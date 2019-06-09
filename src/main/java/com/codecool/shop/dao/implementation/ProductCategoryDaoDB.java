package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;

import com.codecool.shop.model.ProductCategory;



import java.sql.*;
import java.util.List;

public class ProductCategoryDaoDB extends DB_connection implements ProductCategoryDao {

    private static ProductCategoryDaoDB instance = null;

    public static ProductCategoryDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoDB();
        }
        return instance;
    }

    /*TODO We can use formatted String here because it's much more cleaner code*/

    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO categories (id, name, department, description) " +
                "VALUES (DEFAULT, '" + category.getName() +
                "', '" + category.getDepartment() +
                "', '" + category.getDescription() +
                "');";
        super.executeStatement(query);
    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }


    /*public List<Object> executeQuery(String SQL_Statement   ){
        try (Connection connection = getConnection();
             PreparedStatement pStatement =connection.prepareStatement(SQL_Statement)) {
            ResultSet resultSet = pStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void all() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(" ");
        String query = "SELECT * FROM products;";

        try (Connection connection = getConnection();
             Statement statement =connection.prepareStatement(query);
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
    }*/
}
