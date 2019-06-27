package com.codecool.shop.dao.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public abstract class DB_connection {
    private static final Logger logger = LoggerFactory.getLogger(DB_connection.class);

    private static Properties properties = getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String DB_USER = properties.getProperty("user");
    private static final String DB_PASSWORD = properties.getProperty("password");

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
                        logger.debug("SQL statement: {} executed.", SQL_Statement);
        } catch (SQLException e) {
            logger.error("Error during execution of SQL statement: {} \n Stack: {}", SQL_Statement, e.getStackTrace());
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(System.getProperty("user.dir"), "src/main/resources/connection.properties")));
            logger.info("Loading properties file for database was successful.");
        } catch (IOException e) {
            logger.error("Loading properties file for database was unsuccessful {}", e.getStackTrace());
        }
        return properties;
    }
}
