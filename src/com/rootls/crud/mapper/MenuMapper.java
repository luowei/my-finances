package com.rootls.crud.mapper;

import com.rootls.crud.model.Menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by luowei on 2014/4/13.
 */
public  class MenuMapper implements RowMapper {

    private Object bean;
    public MenuMapper(Object bean) {
        this.bean = bean;
    }
    public Object getBean() {
        return bean;
    }
    public void setBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public Menu mapRow(ResultSet rs, int i) throws SQLException {

        Class<?> clazz = bean.getClass();


        Menu m = new Menu(
                rs.getInt("id"),
                rs.getString("menuName"),
                rs.getString("desc"),
                rs.getString("menuUrl"),
                rs.getBoolean("isParent")
        );
        return m;
    }
}
