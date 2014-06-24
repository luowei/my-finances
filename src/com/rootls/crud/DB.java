package com.rootls.crud;

import java.lang.annotation.*;

/**
 * 被修改的类、属性、方法与数据库中表、字段有映射关系
 * Created by luowei on 2014/4/12.
 */
@Documented
@Target({ ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DB {
    boolean value() default true;
}
