package com.rootls.crud;

import com.rootls.common.Page;
import com.rootls.utils.ClassUtil;
import org.springframework.jdbc.core.RowMapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rootls.common.Config.date_pattern;
import static com.rootls.common.Config.time_pattern;
import static com.rootls.utils.ClassUtil.getWriteMethod;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by luowei on 2014/4/12.
 */
public class SqlQuery implements Serializable {

    private String type;
    private Object bean;
    private String sql;
    private String countSql;

    private Page page;
    private Integer pageNumber;
    private Integer pageSize;
    private String defaultOrder;


    public SqlQuery() {
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getDefaultOrder() {
        return defaultOrder;
    }

    public String getSql() {
        return sql;
    }

    public String getCountSql() {
        return countSql;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setDefaultOrder(String defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }

    /**
     * 被 @DB(false) 修饰的类为false
     * */
    public static Boolean isDB(Class<?> clazz) {
        if (clazz.isAnnotationPresent(DB.class) && !clazz.getAnnotation(DB.class).value()) {
            return false;
        }
        return true;
    }
    /**
     * 被 @DB(false) 修饰的属性为false
     * */
    public static Boolean isDB(Field field) {
        if (field.isAnnotationPresent(DB.class) && !field.getAnnotation(DB.class).value()) {
            return false;
        }
        return true;
    }

    public static List<Field> getDBFields(Object bean) {
        List<Field> fields = new ArrayList<Field>();

        Class<?> clazz = bean.getClass();
        if (!isDB(clazz)) {
            return fields;
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (!isDB(field)) {
                continue;
            } else {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * 被 @AddQuery(false) 修饰的类为false
     * */
    public static Boolean isAddQuery(Class<?> clazz) {
        if (clazz.isAnnotationPresent(AddQuery.class) && !clazz.getAnnotation(AddQuery.class).value()) {
            return false;
        }
        return true;
    }
    /**
     * 被 @AddQuery(false) 修饰的属性为false
     * */
    public static Boolean isAddQuery(Field field) {
        if (field.isAnnotationPresent(AddQuery.class) && !field.getAnnotation(AddQuery.class).value()
                || !isDB(field)) {
            return false;
        }
        return true;
    }

    public static List<Field> getAddQueryFields(Object bean) {
        List<Field> fields = new ArrayList<Field>();

        Class<?> clazz = bean.getClass();
        if (!isAddQuery(clazz)) {
            return fields;
        }
        Field[] filds = clazz.getDeclaredFields();
        for (Field field : filds) {
            if (!isAddQuery(field)) {
                continue;
            } else {
                fields.add(field);
            }
        }
        return fields;
    }

    public Method getReadMethod(Field field) {
        try {
            Class<?> clazz = field.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if (field.getName().equals(pd.getName())) {
                    return pd.getReadMethod();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    //-----------------------------------------------------

    /**
     * 构建添加Sql
     */
    public String buildAddSql(String type, Object entityObj) {
        String fields = "", fieldValues = "";
        List<Field> dbFields = getDBFields(entityObj);
        for (Field field : dbFields) {
            try {
                Method readMethod = getReadMethod(field);
                if (field.getName().equals("id") || readMethod == null) {
                    continue;
                }
                fields += (field.getName() + ",");

                Class<?> returnType = readMethod.getReturnType();
                Object value = readMethod.invoke(entityObj);

                if (String.class.getName().equals(returnType.getName())) {
                    fieldValues += "'" + (value == null ? "" : returnType.cast(value)) + "',";
                } else if (Date.class.getName().equals(returnType.getName())) {
                    DateFormat df = new SimpleDateFormat(date_pattern);
                    fieldValues += "'" + (value == null ? "" : df.format(value)) + "',";
                } else if (Timestamp.class.getName().equals(returnType.getName())) {
                    DateFormat df = new SimpleDateFormat(time_pattern);
                    fieldValues += "'" + (value == null ? "" : df.format(value)) + "',";
                } else {
                    fieldValues += " " + returnType.cast(value) + ",";
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return format(" insert into {0}({1}) values({2}) ", type, delLastComma(fields), delLastComma(fieldValues));
    }

    private String delLastComma(String fields) {
        if (fields.endsWith(",")) {
            fields = fields.substring(0, fields.lastIndexOf(","));
        }
        return fields;
    }

    /**
     * 构建得到一页数据的查询器
     */
    public SqlQuery buildPageQuery(String type, Object bean, Page page) {

        pageNumber = page.getPageNumber();
        pageSize = page.getPageSize();
        defaultOrder = isNotBlank(page.getOrder()) ? page.getOrder() : " id ";
        int startRow = (pageNumber - 1) * pageSize;
        int endRow = pageNumber * pageSize;
        String suffix = " order by " + defaultOrder + " limit " + startRow + "," + (endRow - startRow);

        String wheres = " ";     //添加查询条件
        for (Field field : getAddQueryFields(bean)) {

            String fieldName = field.getName();
            Method method = getReadMethod(field);
            Class<?> returnType = method.getReturnType();
            Object value = null;
            try {
                value = method.invoke(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (value == null || (value instanceof String && isBlank(String.valueOf(value)))) {
                continue;
            }

            if (String.class.getName().equals(returnType.getName())) {
                wheres = wheres + " and " + fieldName + " like '%" + returnType.cast(value) + "%' ";
            } else if (Date.class.getName().equals(returnType.getName())) {
                DateFormat df = new SimpleDateFormat(date_pattern);
                wheres = wheres + " and " + fieldName + " = '" + df.format(value) + "' ";
            } else if (Timestamp.class.getName().equals(returnType.getName())) {
                DateFormat df = new SimpleDateFormat(time_pattern);
                wheres = wheres + " and " + fieldName + " = '" + df.format(value) + "' ";
            } else {
                wheres = wheres + " and " + fieldName + " = " + returnType.cast(value) + " ";
            }
        }

        sql = format(" select * from {0} where 1=1 {1} {2} ", type, wheres, suffix);
        countSql = format(" select count(*) from {0} where 1=1 {1} ", type, wheres);
        return this;
    }

    /**
     * 构建是否存在Sql
     */
    public String buildExsitSql(String type, Object entityObj) {
        String wheres = " ";
        List<Field> dbFields = getDBFields(entityObj);
        for (Field field : dbFields) {
            String fieldName = field.getName();

            Method method = getReadMethod(field);
            Class<?> returnType = method.getReturnType();
            Object value = null;
            try {
                value = method.invoke(entityObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (value == null || (value instanceof String && isBlank(String.valueOf(value)))) {
                continue;
            }

            if (String.class.getName().equals(returnType.getName())) {
                wheres += (" and " + fieldName + " like '%" + returnType.cast(value) + "%' ");
            } else if (Date.class.getName().equals(returnType.getName())) {
                DateFormat df = new SimpleDateFormat(date_pattern);
                wheres += (" and " + fieldName + " = '" + df.format(value) + "' ");
            } else if (Timestamp.class.getName().equals(returnType.getName())) {
                DateFormat df = new SimpleDateFormat(time_pattern);
                wheres += (" and " + fieldName + " = '" + df.format(value) + "' ");
            } else {
                wheres += (" and " + fieldName + " = " + returnType.cast(value) + " ");
            }
        }
        return format(" select count(*) from {0} where 1=1 {1}", type, wheres);
    }

    /**
     * 构建更新Sql
     */
    public String buildUpdateSql(String type, Object entityObj) {
        String setItems = " ", wheres = "";

        boolean setedFlag = false;
        List<Field> dbFields = getDBFields(entityObj);
        for (Field field : dbFields) {
            try {
                Method readMethod = getReadMethod(field);
                Class<?> returnType = readMethod.getReturnType();
                Object value = readMethod.invoke(entityObj);

                if (field.getName().equals("id") && readMethod != null) {
                    wheres += " and id=" + returnType.cast(value);
                } else {
                    if (String.class.getName().equals(returnType.getName())) {
                        if (!setedFlag) {
                            setItems += " set ";
                            setedFlag = true;
                        }
                        setItems += (field.getName() + "='" + returnType.cast(value) + "',");
                    } else if (Date.class.getName().equals(returnType.getName())) {
                        DateFormat df = new SimpleDateFormat(date_pattern);
                        if (!setedFlag) {
                            setItems += " set ";
                            setedFlag = true;
                        }
                        setItems += (field.getName() + "='" + df.format(value) + "',");
                    } else if (Timestamp.class.getName().equals(returnType.getName())) {
                        DateFormat df = new SimpleDateFormat(time_pattern);
                        if (!setedFlag) {
                            setItems += " set ";
                            setedFlag = true;
                        }
                        setItems += (field.getName() + "='" + df.format(value) + "',");
                    } else {
                        if (!setedFlag) {
                            setItems += " set ";
                            setedFlag = true;
                        }
                        setItems += (field.getName() + "=" + returnType.cast(value) + ",");
                    }

                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return format(" update {0} {1}  where 1=1 {2}", type, delLastComma(setItems), wheres);
    }

    /**
     * 构建根据Id查找记录Sql
     */
    public String buildFindByIdSql(String type, Integer id) {
        return format(" select * from {0} where 1=1 and id={1} ", type, id);
    }

    /**
     * 构建根据Id删除记录Sql
     */
    public String buildDelByIdSql(String type, Integer id) {
        return format(" delete from {0} where id={1} ", type, id);
    }

    /**
     * 构建删除所有记录Sql
     */
    public String buildDelAllSql(String type) {
        return format(" delete from {0} ", type);
    }

    public String buildExistByIdSql(String type, Object entityObj) {
        Integer id = null;
        try {
            Method method = getReadMethod(entityObj.getClass().getDeclaredField("id"));
            Class<?> returnType = method.getReturnType();
            Object value = method.invoke(entityObj);
            id = value != null ? (Integer) value : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format(" select count(*) from {0} where id={1} ", type, id);
    }

    public String buildListSql(String type) {
        return format(" select * from {0} ",type);
    }

    public static class Mapper implements RowMapper {

        private Object bean;

        public Mapper(Object bean) {
            this.bean = bean;
        }

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            if (bean == null) {
                return null;
            }
            Object obj = null;
            try {
                List<Field> fields = getDBFields(bean);
                obj = bean.getClass().getConstructor().newInstance();

                //遍历obj的属性
                for (Field field : fields) {
                    Method setMethod = getWriteMethod(field);

                    //遍历ResultSet中定义的方法
                    for (Method rsMethod : ResultSet.class.getDeclaredMethods()) {
                        Class<?>[] paraCls = rsMethod.getParameterTypes();
                        if(field.getType().getSimpleName().equals("Integer")
                                && paraCls.length==1 && paraCls[0].getSimpleName().equals("String")){
                            setMethod.invoke(obj,rs.getInt(field.getName()));
                        }else if (field.getType().getSimpleName().equals(rsMethod.getName().substring(3))
                                && paraCls.length==1 &&  paraCls[0].getSimpleName().equals("String")) {
                            setMethod.invoke(obj, rsMethod.invoke(rs, field.getName()));
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return obj;
        }
    }
}
