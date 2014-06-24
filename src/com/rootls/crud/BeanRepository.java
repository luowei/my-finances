package com.rootls.crud;

import com.rootls.common.BaseRespository;
import com.rootls.common.Page;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.rootls.utils.ClassUtil.getWriteMethod;


/**
 * Created by luowei on 2014/4/12.
 */
@Component
public class BeanRepository extends BaseRespository {

    public Object getEntityObj(String type) {
        try {
            String entityClassName = this.getClass().getPackage().getName() + ".model." +
                    type.substring(0, 1).toUpperCase() + type.substring(1);
            Class entityClass = Class.forName(entityClassName);
            return entityClass.getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询一页数据
     */
    public Page page(String type, Object bean, Page page) throws Exception {
        SqlQuery sqlQuery = new SqlQuery().buildPageQuery(type, bean, page);

        int count = getJdbcTemplate().queryForInt(sqlQuery.getCountSql());
        List list = getJdbcTemplate().query(sqlQuery.getSql(),new SqlQuery.Mapper(getEntityObj(type)) );

        return new Page(sqlQuery.getPageNumber(), sqlQuery.getPageSize(), count, sqlQuery.getDefaultOrder(), list);
    }


    public List list(String type) {
        String sqlQuery = new SqlQuery().buildListSql(type);
        return getJdbcTemplate().query(sqlQuery,new SqlQuery.Mapper(getEntityObj(type)));
    }

    /**
     * 添加一条记录
     */
    public int add(String type, Object entityObj) {
        return getJdbcTemplate().update(new SqlQuery().buildAddSql(type, entityObj));
    }

    /**
     * 判断这条记录是否存在
     */
    public boolean exsit(String type, Object entityObj) {
        String sql = new SqlQuery().buildExsitSql(type, entityObj);
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    public boolean exsitById(String type, Object entityObj) {
        String sql = new SqlQuery().buildExistByIdSql(type,entityObj);
        return getJdbcTemplate().queryForInt(sql) > 0;
    }

    /**
     * 更新一条记录
     */
    public int update(String type, Object entityObj) {
        return getJdbcTemplate().update(new SqlQuery().buildUpdateSql(type,entityObj));
    }

    /**
     * 根据Id找出一条记录
     */
    public Object getById(String type, Integer id) {
        String sql = new SqlQuery().buildFindByIdSql(type, id);
        List list = getJdbcTemplate().query(sql, new SqlQuery.Mapper(getEntityObj(type)) );
        return list.size() > 0 ? list.get(0) : null;
    }

    public int delById(String type, Integer id) {
        String sql = new SqlQuery().buildDelByIdSql(type, id);
        return getJdbcTemplate().update(sql);
    }

    /**
     * 删除所有记录
     */
    public int delAll(String type) {
        String sql = new SqlQuery().buildDelAllSql(type);
        return getJdbcTemplate().update(sql);
    }

}
