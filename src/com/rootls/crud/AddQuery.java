package com.rootls.crud;

import java.lang.annotation.*;

/**
 * 被修饰的属性(字段)会添加到查询条件
 * Created by luowei on 2014/4/12.
 */
@Documented
@Target({ElementType.TYPE,ElementType.FIELD ,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@DB(true)
public @interface AddQuery {
    boolean value() default true;

}
