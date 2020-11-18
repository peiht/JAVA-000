package com.javaAdvance.jdbc;

import java.sql.*;

/**
 * jdbc 操作
 *
 * 电脑本机的mysql起不来了，用的公司开发环境
 * @author hitopei
 */
public class JdbcOperator {

    private static final String username = "dct";
    private static final String password = "J8UfRvLEiUPz";
    private static final String jdbcUrl = "jdbc:mysql://10.0.20.202:3306/dct?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull";
    public static void main(String[] args) {
        //operate();
        //operatePreparedStatement();
        operateBatch();
    }

    /**
     * jdbc 基本用法
     */
    public static void operate(){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();

            //增
            String insertSql = "insert into user_acct_hht values (1111, 1231, 1234)";
            boolean isExecuted = statement.execute(insertSql);
            System.out.println("执行成功");

            //查
            String sql = "select * from user_acct_hht";
            statement.executeQuery(sql);
            ResultSet set = statement.getResultSet();
            System.out.println("查询结果：");
            while (set.next()) {
                int id = set.getInt(1);
                System.out.println(id);
            }
            System.out.println();

            //改
            String updateSql = "update user_acct_hht t set t.platformCustomerNo = 4321 where t.userId = 1111";
            statement.executeUpdate(updateSql);

            //删
            String deleteSql = "delete from user_acct_hht where userId = 1111";
            statement.execute(deleteSql);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * preparedStatement实现增删改查
     */
    public static void operatePreparedStatement(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            // query
            String querySql = "select * from user_acct_hht limit 10";
            statement = connection.prepareStatement(querySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

            //add
            String addSql = "insert into user_acct_hht values (1111, 1231, 1234)";
            statement = connection.prepareStatement(addSql);
            statement.execute();

            // update
            String updateSql = "update user_acct_hht t set t.platformCustomerNo = ? where t.userId = ?";
            statement = connection.prepareStatement(updateSql);
            statement.setInt(1, 4321);
            statement.setInt(2, 1111);
            statement.executeUpdate();

            // delete
            String deleteSql = "delete from user_acct_hht where userId = ?";
            statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, 1111);
            statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启jdbc事务，不使用自动提交，代码块执行完一起提交
     */
    public static void operateTransaction(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            //设置自动提交为false
            connection.setAutoCommit(false);
            // query
            String querySql = "select * from user_acct_hht limit 10";
            statement = connection.prepareStatement(querySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

            //add
            String addSql = "insert into user_acct_hht values (?, ?, ?)";
            statement = connection.prepareStatement(addSql);
            statement.setInt(1, 1111);
            statement.setInt(2, 1234);
            statement.setInt(3, 4321);
            statement.execute();

            // update
            String updateSql = "update user_acct_hht t set t.platformCustomerNo = ? where t.userId = ?";
            statement = connection.prepareStatement(updateSql);
            statement.setInt(1, 4321);
            statement.setInt(2, 1111);
            statement.executeUpdate();

            // delete
            String deleteSql = "delete from user_acct_hht where userId = ?";
            statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, 1111);
            statement.execute();

            //提交事务
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        } finally {
            try {
                statement.close();
                connection.close();
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用jdbc的批处理添加10条数据
     */
    public static void operateBatch(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);


            //add
            //批处理添加10条数据
            String addSql = "insert into user_acct_hht values (?, ?, ?)";
            statement = connection.prepareStatement(addSql);
            for (int i = 0; i < 10; i++) {
                statement.setInt(1, 1110 + i);
                statement.setInt(2, 1234 + i);
                statement.setInt(3, 4321 + i);
                statement.addBatch();
            }
            statement.executeBatch();
            statement.clearBatch();

            // query
            String querySql = "select * from user_acct_hht limit 10";
            statement = connection.prepareStatement(querySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

            // update
            String updateSql = "update user_acct_hht t set t.platformCustomerNo = ? where t.userId = ?";
            statement = connection.prepareStatement(updateSql);
            statement.setInt(1, 4321);
            statement.setInt(2, 1111);
            statement.executeUpdate();

            // delete
            String deleteSql = "delete from user_acct_hht where userId = ?";
            statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, 1111);
            statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
