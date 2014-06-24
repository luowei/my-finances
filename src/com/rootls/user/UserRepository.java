package com.rootls.user;

import com.rootls.common.BaseRespository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import static com.rootls.utils.BASE64.encrypt;
import static com.rootls.utils.JiamiJiemi.jiami;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserRepository extends BaseRespository {


    public User findByUsername(String username) {
        final String name = username;
        List<User> list = getJdbcTemplate().query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pst = connection.prepareStatement(
                        " select * from user where username=? ");
                pst.setString(1, name);
                return pst;
            }
        },
        new RowMapper(){
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        new HashSet<GrantedAuthority>()
                );
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                user.setToken(rs.getString("token"));
                user.setSecretKey(rs.getString("secretKey"));
                return user;
            }
        });
        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public int reSetAdmin() {
        boolean exsitAdmin = getJdbcTemplate().queryForInt("select count(*) from user where username='admin'") > 0;
        String encryptPW= encrypt(jiami("admin", "luowei"));
        int ret = 0;
        if(exsitAdmin){
            ret = getJdbcTemplate().update("update user set password='"+encryptPW+"'," +
                    " secretKey='luowei' where username='admin' ");
        }else {
            ret = getJdbcTemplate().update("insert into user(username,password,email,secretKey) " +
                    " values('admin','"+encryptPW+"','luowei@rootls.com','luowei')");
        }
        return ret;
    }

    public String getSecretKey(final String username) {
        return getJdbcTemplate().query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pst = connection.prepareStatement("select secretKey from `user` where username= ?");
                //noinspection JpaQueryApiInspection
                pst.setString(1,username);
                return pst;
            }
        }, new ResultSetExtractor<String>(){
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    return rs.getString("secretKey");
                }
                return null;
            }
        });
    }
}
