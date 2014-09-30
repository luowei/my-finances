package com.rootls.crud.account;

import com.rootls.common.BaseRespository;
import com.rootls.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.rootls.utils.BASE64.base64Decode;
import static com.rootls.utils.BASE64.base64Encode;
import static com.rootls.utils.JiamiJiemi.jiemi;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-31
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AccountRepository extends BaseRespository {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    void init() {
        setDataSource(dataSource);
    }


    public boolean exsitSameAccount(Account account) {
        String sql = "select count(*) from account where siteName='" + account.getSiteName() + "' and `keyName`='" + account.getKeyName() + "'";
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    public int add(Account account) {
        String sql = " insert into account(siteName,`keyName`,keySecret) " +
                " values('" + account.getSiteName() + "','" + account.getKeyName() + "','" + base64Encode(account.getKeySecret()) + "')";
        return getJdbcTemplate().update(sql);
    }

    public boolean exsitById(Integer id) {
        String sql = " select count(*) from account where id=" + id;
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    public int update(Account account) {
        String sql = " update account set siteName='" + account.getSiteName() + "',`keyName`='" + account.getKeyName() + "', " +
                " keySecret='" + base64Encode(account.getKeySecret()) + "' where id=" + account.getId();
        return getJdbcTemplate().update(sql);
    }

    public int delAllAccount() {
        String sql = " delete from account ";
        return getJdbcTemplate().update(sql);
    }

    public Page<Account> list(Account account, Page page) {
        Integer pageNumber = page.getPageNumber();
        Integer pageSize = page.getPageSize();
        String defaultOrder = isNotBlank(page.getOrder()) ? page.getOrder() : " id ";

        int startRow = (pageNumber - 1) * pageSize;
        int endRow = pageNumber * pageSize;


        String sql = " select * from account where 1=1 ";
        String countSql = " select count(*) from account where 1=1 ";
        if (isNotBlank(account.getSiteName())) {
            sql = sql + " and siteName like '%" + account.getSiteName() + "%' ";
            countSql = countSql + " and siteName like '%" + account.getSiteName() + "%' ";
        }
        if (isNotBlank(account.getKeyName())) {
            sql = sql + " and `keyName` like '%" + account.getKeyName() + "%' ";
            countSql = countSql + " and siteName like '%" + account.getSiteName() + "%' ";
        }

        sql = sql + " order by " + defaultOrder + " limit " + startRow + "," + (endRow - startRow);

        int count = getJdbcTemplate().queryForInt(countSql);
        List<Account> list = getJdbcTemplate().query(sql, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int i) throws SQLException {
                Account account = new Account(
                        rs.getInt("id"),
                        rs.getString("siteName"),
                        rs.getString("keyName"),
                        null
                );
                String keySecret = rs.getString("keySecret");
                if (isNotBlank(keySecret)) {
                    account.setKeySecret(base64Decode(keySecret));
                }
                return account;
            }
        });
        return new Page<Account>(pageNumber, pageSize, count, defaultOrder, list);
    }


    public String getKeyReal(Integer id, String secretKey) {
        final String key = secretKey;
        return getJdbcTemplate().query("select keySecret from account where id=" + id,
                new ResultSetExtractor<String>() {
                    @Override
                    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if(rs.next()){
                            String keySecret = rs.getString("keySecret");
                            return jiemi(base64Decode(keySecret),key);
                        }
                        return "";
                    }
                });
    }

    public int delById(Integer id) {
        String sql = " delete from account where id="+id;
        return getJdbcTemplate().update(sql);
    }

    public Account getById(Integer id) {
        String sql = " select * from account where id="+id;
        return getJdbcTemplate().query(sql,new ResultSetExtractor<Account>() {
            @Override
            public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    Account account = new Account(
                            rs.getInt("id"),
                            rs.getString("siteName"),
                            rs.getString("keyName"),
                            null
                    );
                    String keySecret = rs.getString("keySecret");
                    account.setKeySecret(base64Decode(keySecret));
                    return account;
                }
                return null;
            }
        });
    }
}
