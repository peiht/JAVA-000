package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariJNDIFactory;
import com.zaxxer.hikari.hibernate.HikariConfigurationUtil;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import com.zaxxer.hikari.pool.HikariPool;
import com.zaxxer.hikari.pool.HikariProxyConnection;
import com.zaxxer.hikari.pool.HikariProxyStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestDataSource.class)
public class TestDataSource {


    @Test
    public void test() throws SQLException {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://10.0.20.202:3306/dct?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull");
        config.setUsername("duocaitou");
        config.setPassword("J8UfRvLEiUPz");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        HikariDataSource hikariDataSource = new HikariDataSource(config);
        Connection connection = hikariDataSource.getConnection();
        System.out.println("------" + connection.getClass());
        System.out.println("------" + hikariDataSource.getClass());

        connection.close();
    }
}
