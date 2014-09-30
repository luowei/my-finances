package com.rootls.crud.regex;

import com.rootls.common.BaseRespository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-5
 * Time: 上午3:03
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RegexTipRepository extends BaseRespository {


    public List<RegexTip> list(RegexTip tip) {
        String sql = "select * from regextip where 1=1 ";
        if (isNotBlank(tip.getName())) {
            sql = sql + " and `name` like '%" + tip.getName() + "%' ";
        }
        if (isNotBlank(tip.getDescribe())) {
            sql = sql + " and `describe` like '%" + tip.getDescribe() + "%' ";
        }
        return getJdbcTemplate().query(sql, new RowMapper<RegexTip>() {
            @Override
            public RegexTip mapRow(ResultSet rs, int i) throws SQLException {
                RegexTip tip = new RegexTip(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("describe"),
                        rs.getString("regex")
                );
                return tip;
            }
        });
    }

    public boolean exsitSameRegex(RegexTip regexTip) {
        String sql = " select count(*) from regextip " +
                " where `name`='" + regexTip.getName() + "' and `describe`='" + regexTip.getDescribe() + "'";
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    public int add(RegexTip regexTip) {
        String sql = " insert into regextip(`name`,`describe`,regex) " +
                " values('" + regexTip.getName() + "','" + regexTip.getDescribe() + "','" + regexTip.getRegex() + "') ";
        return getJdbcTemplate().update(sql);
    }

    public boolean exsitById(Integer id) {
        String sql = " select count(*) from regextip where id = " + id;
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    public int update(RegexTip regexTip) {
        String sql = " update regextip set `name`='" + regexTip.getName() + "',`describe`='" + regexTip.getDescribe() + "'," +
                "regex='" + regexTip.getRegex() + "' where id=" + regexTip.getId();
        return getJdbcTemplate().update(sql);
    }

    public int delById(Integer id) {
        String sql = " delete from regextip where id=" + id;
        return getJdbcTemplate().update(sql);
    }

    public RegexTip getRegex(Integer id) {
        String sql = " select * from regextip where id=" + id;
        return getJdbcTemplate().query(sql, new ResultSetExtractor<RegexTip>() {
            @Override
            public RegexTip extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    RegexTip regex = new RegexTip(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("describe"),
                            rs.getString("regex")
                    );
                    return regex;
                }
                return null;
            }
        });
    }

    public int delAll() {
        String sql = " delete from regextip ";
        return getJdbcTemplate().update(sql);
    }
}
