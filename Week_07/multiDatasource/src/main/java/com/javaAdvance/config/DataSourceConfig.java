package com.javaAdvance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hitopei
 *
 * 配置信息
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource dataSourceMaster(){
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSource dataSourceSlave(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource dynamicDataSource(){
        DynamicDataSource dataSource = new DynamicDataSource();
        DataSource master = dataSourceMaster();
        DataSource slave = dataSourceSlave();

        dataSource.setDefaultTargetDataSource(master);
        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceType.Master, master);
        map.put(DataSourceType.Slave, slave);
        dataSource.setTargetDataSources(map);
        return dataSource;
    }
}
