package com.rootls.crud.finance;

import com.rootls.common.BaseRespository;
import com.rootls.common.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-14
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DaytipRepository extends BaseRespository {

    public int addDaytip(Daytip daytip, String tableName) {
//        String tipDate = new SimpleDateFormat("yyyy-MM-dd").format(daytip.getTipDate());
        String sql = " insert into " + tableName + "(tipDateStr,`week`,money,`desc`,`type`) " +
                "value('" + daytip.getTipDateStr() + "','" + daytip.getWeek() + "'," + daytip.getMoney() + ",'" + daytip.getDesc() + "','"+daytip.getType()+"') ";
        return getJdbcTemplate().update(sql);
    }

    public boolean exsitDaytip(String tipDateStr, String tableName) {
        String sql = " select count(*) from " + tableName + " where tipDateStr='" + tipDateStr.trim() + "'";
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    public int appendDaytipMoney(Daytip daytip, String tableName) {
        String sql = " update " + tableName + " set money=money+" + daytip.getMoney() +
                ",`desc`=concat(`desc`,'+','" + daytip.getDesc() + "'),`type`='"+daytip.getType()+"' " +
                " where tipDateStr='" + daytip.getTipDateStr() + "'";
        return getJdbcTemplate().update(sql);
    }

    public int countDaytip(String tableName) {
        String sql = "select count(*) from " + tableName + " ";
        return getJdbcTemplate().queryForInt(sql);
    }

    public int delDaytip(String tableName) {
        String sql = " delete from " + tableName + " ";
        return getJdbcTemplate().update(sql);
    }

    public void updateTipDate(Integer year, String tableName) {
        String selSql = " select * from " + tableName + " ";
        List<Daytip> list = getJdbcTemplate().query(selSql, new RowMapper<Daytip>() {
            @Override
            public Daytip mapRow(ResultSet rs, int i) throws SQLException {
                Daytip tip = new Daytip(
                        rs.getString("tipDateStr"),
                        null,
                        rs.getString("week"),
                        rs.getFloat("money"),
                        rs.getString("desc")
                );
                tip.setId(rs.getInt("id"));
                return tip;
            }
        });

        for (Daytip tip : list) {
            String month = StringUtils.substringBefore(tip.getTipDateStr(), "月");
            String day = StringUtils.substringBetween(tip.getTipDateStr(), "月", "日");
            String sql = " update  " + tableName + " set tipDate='" + year + "-" + month + "-" + day + "' where id=" + tip.getId();
            getJdbcTemplate().update(sql);
        }

    }

    public boolean checkTipDate(String tableName) {
        String sql = " select count(*) from " + tableName + " where tipDate is null or tipDate=''";
        return getJdbcTemplate().queryForInt(sql) == 0;
    }

    public Page<Daytip> pageDaytip(Daytip daytip, String tableName,Page page) {

        Integer pageNumber = page.getPageNumber();
        Integer pageSize = page.getPageSize();
        String defaultOrder = isNotBlank(page.getOrder()) ? page.getOrder() : " id ";

        int startRow = (pageNumber - 1) * pageSize;
        int endRow = pageNumber * pageSize;

        String suffix = " order by " + defaultOrder + " limit " + startRow + "," + (endRow - startRow);

        String wheres = "";
        if(isNotBlank(daytip.getDesc())){
            wheres +=" and `desc` like '%"+ daytip.getDesc()+"%' ";
        }
        if(isNotBlank(daytip.getStartDate())){
            wheres += " and tipDate >='"+daytip.getStartDate()+"' ";
        }
        if(isNotBlank(daytip.getEndDate())){
            wheres += " and tipDate <='"+daytip.getEndDate()+"' ";
        }
        if(isNotBlank(daytip.getType())){
            wheres += " and `type` like '%"+daytip.getType()+"%'";
        }

        String sql = format(" select * from {0} where 1=1 {1} {2} ", tableName, wheres, suffix);
        String countSql = format(" select count(*) from {0} where 1=1 {1} ",tableName,wheres);

        Integer count = getJdbcTemplate().queryForInt(countSql);

        List<Daytip> list = getJdbcTemplate().query(sql, new RowMapper<Daytip>() {
            @Override
            public Daytip mapRow(ResultSet rs, int i) throws SQLException {
                Daytip tip = new Daytip(
                        rs.getString("tipDateStr"),
                        rs.getTimestamp("tipDate"),
                        rs.getString("week"),
                        rs.getFloat("money"),
                        rs.getString("desc")
                );
                tip.setId(rs.getInt("id"));
                tip.setType(rs.getString("type"));
                return tip;
            }
        });

        Page<Daytip> pg = new Page<Daytip>(pageNumber, pageSize, count, defaultOrder, list);

        String moneySumSql = format(" select sum(money) moneySum from {0} where 1=1 {1} ",tableName,wheres);
        String moneySum = getJdbcTemplate().query(moneySumSql,new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    return String.valueOf(rs.getFloat("moneySum"));
                }
                return null;
            }
        });
        pg.getExtra().put("moneySum",moneySum);
        return pg;
    }
}
