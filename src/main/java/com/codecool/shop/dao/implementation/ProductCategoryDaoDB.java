package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;

import com.codecool.shop.model.ProductCategory;


import java.sql.*;
import java.util.ArrayList;
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
        String statement =
                String.format("INSERT INTO categories (id, name, department, description) VALUES (DEFAULT, %n, %d, %s);",
                category.getName(), category.getDepartment(), category.getDescription());

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = pStatement.executeQuery();
            ResultSet generatedKey = pStatement.getGeneratedKeys();
            if(generatedKey.next()){
                category.setId(generatedKey.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(category.getId());
    }

    @Override
    public ProductCategory find(int id) {
        String query = String.format("SELECT * FROM categories WHERE id = %d;", id);

        /*We need to declare this object here (out of try/catch) so that we can return it at the end of the method*/
        ProductCategory returnedProductCategory = null;

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = pStatement.executeQuery();

            while (resultSet.next()) {

                int returned_id = resultSet.getInt("id");
                String returned_name = resultSet.getString("name");
                String returned_department = resultSet.getString("department");
                String returned_description = resultSet.getString("description");

                returnedProductCategory = new ProductCategory(
                        returned_name,
                        returned_department,
                        returned_description
                );

                returnedProductCategory.setId(returned_id);

                /*Creating logs for server (not mandatory)*/

                System.out.println("The searching based on ID was successful. \n" +
                        "The following data retrieved from Database: " +
                        "Product Category: [" +
                        returnedProductCategory.toString() +
                        "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedProductCategory;
    }


    @Override
    public void remove(int id) {
        String statement = String.format("DELETE FROM categories WHERE id = %d", id);
        super.executeStatement(statement);

    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM categories";

        List<ProductCategory> result = new ArrayList<>();


        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query)){

            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){


                int returned_id = resultSet.getInt("id");
                String returned_name = resultSet.getString("name");
                String returned_department = resultSet.getString("department");
                String returned_description = resultSet.getString("description");

                ProductCategory returnedProductCategory = new ProductCategory(
                        returned_name,
                        returned_department,
                        returned_description
                );

                returnedProductCategory.setId(returned_id);

                result.add(returnedProductCategory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
