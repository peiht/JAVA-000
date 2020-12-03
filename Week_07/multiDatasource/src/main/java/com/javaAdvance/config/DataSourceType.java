package com.javaAdvance.config;

/**
 * @author hitopei
 *
 * 数据源的枚举类型
 */
public enum DataSourceType {

    /**
     * 主库
     */
    Master("master"),
    Slave("slave");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private DataSourceType(String name){
        this.name = name;
    }

}
