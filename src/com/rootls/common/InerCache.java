package com.rootls.common;

import com.rootls.crud.SqlQuery;
import com.rootls.crud.model.Menu;
import com.rootls.user.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.rootls.common.Config.inerCache_expire;
import static org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-1-26
 * Time: 上午11:32
 * To change this template use File | Settings | File Templates.
 */
public class InerCache {

    Map<Integer, User> userIdMap;
    Map<Integer, Menu> menuIdMap;

    Long initTime;
    static InerCache inerCache;

    public synchronized void init() {
        if (inerCache == null) {
            inerCache = new InerCache();
        }

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(BasicDataSource.class.cast(getCurrentWebApplicationContext().getBean("dataSource")));

            String userSql = " select * from `user` ";
            List<User> userList = jdbcTemplate.query(userSql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
//                    list.add(new SimpleGrantedAuthority(rs.getString("role")));
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

            inerCache.userIdMap = new HashMap<Integer, User>(100);
            for (User user : userList) {
                inerCache.userIdMap.put(user.getId(), user);
            }
            System.out.println("################# user's (id,user) map size :" + userIdMap.size() + " #################");

            //设置菜单Cache
            String menuSql = " select * from `menu`";
            List menuList = jdbcTemplate.query(menuSql, new SqlQuery.Mapper(new Menu()));

            inerCache.menuIdMap = new HashMap<Integer, Menu>(100);
            for (Object m : menuList) {
                if(m instanceof Menu){
                    Menu menu = (Menu)m;
                    inerCache.menuIdMap.put(menu.getId(), menu);
                }
            }

            inerCache.initTime = System.currentTimeMillis();
            System.out.println("################# menu's (id,menu) map size :" + menuIdMap.size() + " #################");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Map<Integer, User> getUserMap() {
        if (inerCache == null) {
            inerCache = new InerCache();
        }
        Long mapTime = System.currentTimeMillis() - inerCache.initTime;
        if (inerCache.userIdMap == null || inerCache.userIdMap.isEmpty() ||
                mapTime > inerCache_expire) {
            inerCache.init();
        }
        return inerCache.userIdMap;
    }

    public static Map<Integer, Menu> getMenuMap() {
        if (inerCache == null) {
            inerCache = new InerCache();
        }
        Long mapTime = System.currentTimeMillis() - inerCache.initTime;
        if (inerCache.menuIdMap == null || inerCache.menuIdMap.isEmpty() ||
                mapTime > inerCache_expire) {
            inerCache.init();
        }
        return inerCache.menuIdMap;
    }

    private InerCache() {
        initTime = System.currentTimeMillis();
    }


    public static void clearCache() {
        inerCache = null;
        System.gc();
    }
}