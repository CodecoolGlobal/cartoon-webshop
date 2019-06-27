package com.codecool.shop.dao.implementation.DB;

import com.codecool.shop.dao.ProductCategoryDao;

import com.codecool.shop.dao.implementation.DB_connection;
import com.codecool.shop.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB extends DB_connection implements ProductCategoryDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoDB.class);
    private static ProductCategoryDaoDB instance = null;

    public static ProductCategoryDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoDB();
            logger.debug("\n   Singleton instance of {} created", instance);
        }
        return instance;
    }

    /*TODO We can use formatted String here because it's much more cleaner code*/

    @Override
    public void add(ProductCategory category) {
        String statement = "INSERT INTO categories (id, name, department, description) " +
                "VALUES (DEFAULT, '" + category.getName() +
                "', '" + category.getDepartment() +
                "', '" + category.getDescription() +
                "');";

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){

            pStatement.executeUpdate();
            ResultSet generatedKey = pStatement.getGeneratedKeys();
            if(generatedKey.next()){
                category.setId(generatedKey.getInt(1));
            }
            logger.debug("\n   ProductCategory [{}]\n   was added to database successfully.", category);
        } catch (Exception e) {
            logger.error("\n   Error during adding ProductCategories with query {} \n Stack: {}", statement, e.getStackTrace());
        }
    }

    @Override
    public ProductCategory find(int id) {
        String query = String.format("SELECT * FROM categories WHERE id = %d;", id);

        /*We need to declare this object here (out of try/catch) so that we can return it at the end of the method*/
        ProductCategory returnedProductCategory = null;

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()) {

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

                logger.debug(
                    "\n   The searching based on {} was successful." +
                    "\n   The following data retrieved from Database: ProductCategory: [{}]",
                    returned_id,
                    returnedProductCategory
                );
            }
        } catch (SQLException e) {
            logger.error("Finding ProductCategory with id {} in database was unsuccessful. Stack: {}", id, e.getStackTrace());
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
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()){

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
                logger.debug("\n   Getting ProductCategory [{}] \n   from database was successful.", returnedProductCategory);
            }
            logger.info("All ProductCategories were successfully received from database.");

        } catch (Exception e) {
            logger.error("Error during getting all ProductCategories from the database. \n    Stack: {}", e.getStackTrace());
        }
        return result;
    }



}
