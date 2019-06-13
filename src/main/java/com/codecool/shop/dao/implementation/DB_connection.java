package com.codecool.shop.dao.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public abstract class DB_connection {

    private static final String DATABASE = "jdbc:postgresql://" + getProperties().getProperty("url") + "/" + getProperties().getProperty("database");
    private static final String DB_USER = getProperties().getProperty("user");
    private static final String DB_PASSWORD = getProperties().getProperty("password");

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

    private static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), "src/main/resources/connection.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
