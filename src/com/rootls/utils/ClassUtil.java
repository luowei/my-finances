package com.rootls.utils;

import com.rootls.crud.model.Menu;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luowei on 2014/4/12.
 */
public class ClassUtil {

    public static void main(String[] args) {
        Menu menu = new Menu();

//        for(Method method:menu.getClass().getMethods() ) {
//            getFieldName(method);
//        }

        Object a = Boolean.valueOf(false);
        Boolean b = null;

        System.out.println(a.getClass().cast(b));


//        for (Field field : getFieldsAndSuperFields(menu.getClass())) {
//            System.out.println("field=====:" + field.getName());
//        }
    }

    public static Field getField(Method method) {
        try {
            Class<?> clazz = method.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if (method.equals(pd.getWriteMethod()) || method.equals(pd.getReadMethod())) {
                    return clazz.getDeclaredField(pd.getName());
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Method> getReadMethod(Class<?> clazz) {
        List<Method> methods = new ArrayList<Method>();
        try {
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                methods.add(pd.getReadMethod());
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return methods;
    }

    public static List<Method> getWriteMethod(Class<?> clazz){
        List<Method> methods = new ArrayList<Method>();
        try {
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                methods.add(pd.getWriteMethod());
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return methods;
    }

    public static Method getReadMethod(Field field) {
        try {
            Class<?> clazz = field.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if(field.getName().equals(pd.getName())){
                    return pd.getReadMethod();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getWriteMethod(Field field) {
        try {
            Class<?> clazz = field.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if(field.getName().equals(pd.getName())){
                    return pd.getWriteMethod();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Field> getFieldsAndSuperFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>();
        Class superClass = clazz.getSuperclass();
        if (superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                fields.add(field);
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }

        return fields;
    }
}
