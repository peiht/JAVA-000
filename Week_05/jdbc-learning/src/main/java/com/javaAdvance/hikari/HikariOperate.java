package com.javaAdvance.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用hikari连接池
 * @author hitopei
 *
 */
public class HikariOperate {

    private static final String username = "duocaitou";
    private static final String password = "J8UfRvLEiUPz";
    private static final String jdbcUrl = "jdbc:mysql://10.0.20.202:3306/dct?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull";

    public static void main(String[] args) {
        hikari();
    }

    public static void hikari() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        HikariDataSource hikariDataSource = new HikariDataSource(config);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = hikariDataSource.getConnection();
            statement = connection.createStatement();
            String sql = "select * from user_acct_hht";
            statement.executeQuery(sql);
            ResultSet set = statement.getResultSet();
            System.out.println("查询结果：");
            while (set.next()) {
                int id = set.getInt(1);
                System.out.println(id);
            }

            //改
            String updateSql = "update user_acct_hht t set t.platformCustomerNo = 4321 where t.userId = 1111";
            statement.executeUpdate(updateSql);

            //删
            String deleteSql = "delete from user_acct_hht where userId = 1111";
            statement.execute(deleteSql);

            //增
            String insertSql = "insert into user_acct_hht values (1111, 1231, 1234)";
            statement.execute(insertSql);
            System.out.println("执行成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
}
