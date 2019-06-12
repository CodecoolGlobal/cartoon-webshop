package com.codecool.shop.dao.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class DB_connection {

    /*----------------You need to set your db account details here for proper application running---------------------*/

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/cartoon_shop";
    private static final String DB_USER = System.getenv("POSTGRES_DB_USER");
    private static final String DB_PASSWORD = System.getenv("POSTGRES_DB_PASSWORD");

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public void executeStatement(String SQL_Statement) {
        try(Connection connection = getConnection();
            PreparedStatement pStatement =connection.prepareStatement(SQL_Statement)) {
                        pStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
