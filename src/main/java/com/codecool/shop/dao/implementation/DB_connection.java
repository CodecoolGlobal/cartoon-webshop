package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB_connection {

    /*----------------You need to set your db account details here for proper application running---------------------*/

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/cartoon_shop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "6Magos6600";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void all() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(" ");
        String query = "SELECT * FROM products;";

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("description"));
                System.out.println(resultSet.getString("defaultPrice"));
                System.out.println(resultSet.getString("defaultCurrency"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
