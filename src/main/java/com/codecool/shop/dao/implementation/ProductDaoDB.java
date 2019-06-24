package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends DB_connection implements ProductDao {

    private static final Logger logger = Logger.getLogger(ProductDaoDB.class);
    private static ProductDaoDB instance = null;

    public static ProductDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductDaoDB();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String statement = "INSERT INTO products (id, name, description, defaultprice, defaultcurrency, category_id, supplier_id) " +
                "VALUES (DEFAULT, '" + product.getName() +
                "', '" + product.getDescription() +
                "', '" + product.getDefaultPrice() +
                "', '" + product.getDefaultCurrency() +
                "', '" + product.getProductCategory().getId() +
                "', '" + product.getSupplier().getId() +
                "');";

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){

            pStatement.executeUpdate();
            ResultSet generatedKey = pStatement.getGeneratedKeys();
            if(generatedKey.next()){
                product.setId(generatedKey.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        String query = String.format("SELECT * FROM products WHERE id = %d;", id);
        Product returnedProduct = null;

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()) {

            while (resultSet.next()) {

                int productId = resultSet.getInt("id");
                returnedProduct = getProductFromDB(resultSet);
                returnedProduct.setId(productId);

                logger.debug("The searching based on ID was successful. \n" +
                        "The following data retrieved from Database: " +
                        "Product: [" +
                        returnedProduct.toString() +
                        "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnedProduct;
    }

    @Override
    public void remove(int id) {
        String statement = String.format("DELETE FROM products WHERE id = %d", id);
        super.executeStatement(statement);
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        return getFilteredProducts(query);
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = String.format("SELECT * FROM products WHERE supplier_id = %d", supplier.getId());
        return getFilteredProducts(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = String.format("SELECT * FROM products WHERE category_id = %d", productCategory.getId());
        return getFilteredProducts(query);
    }

    private Product getProductFromDB(ResultSet resultSet) throws SQLException {
        String productName = resultSet.getString("name");
        String productDescription = resultSet.getString("description");
        float productDefaultPrice = resultSet.getFloat("defaultprice");
        String productCurrencyString = resultSet.getString("defaultcurrency");
        int productCategoryId = resultSet.getInt("category_id");
        int supplierId = resultSet.getInt("supplier_id");


        ProductCategory productCategory = ProductCategoryDaoDB.getInstance().find(productCategoryId);
        Supplier supplier = SupplierDaoDB.getInstance().find(supplierId);

        return new Product(
                productName,
                productDefaultPrice,
                productCurrencyString,
                productDescription,
                productCategory,
                supplier
        );
    }

    private List<Product> getFilteredProducts(String query) {
        List<Product> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()){

            while(resultSet.next()){

                int productId = resultSet.getInt("id");
                Product returnedProduct = getProductFromDB(resultSet);
                returnedProduct.setId(productId);
                result.add(returnedProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
