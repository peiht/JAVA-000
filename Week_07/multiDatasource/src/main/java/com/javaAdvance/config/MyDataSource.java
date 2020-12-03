package com.javaAdvance.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hitopei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyDataSource {

    /**
     * 默认是master
     * @return value
     */
    DataSourceType value() default DataSourceType.Master;
}
