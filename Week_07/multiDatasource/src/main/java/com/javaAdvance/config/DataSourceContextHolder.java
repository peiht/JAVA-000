package com.javaAdvance.config;

/**
 * @author hitopei
 *
 *
 */
public class DataSourceContextHolder {

    private final static ThreadLocal<String> local = new ThreadLocal<>();

    public static void putDataSource(String name){
        local.set(name);
    }

    public static String getDataSource() {
        return local.get();
    }
}
