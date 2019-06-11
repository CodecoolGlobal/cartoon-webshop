package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends DB_connection implements ProductDao {

    private static ProductDaoDB instance = null;

    public static ProductDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductDaoDB();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String statement = "INSERT INTO categories (id, name, description, defaultprice, defaultcurrency, category_id, supplier_id) " +
                "VALUES (DEFAULT, '" + product.getName() +
                "', '" + product.getDescription() +
                "', '" + product.getDefaultPrice() +
                "', '" + product.getDefaultCurrency() +
                "', '" + product.getProductCategory().getId() +
                "', '" + product.getSupplier().getId() +
                "');";
        super.executeStatement(statement);
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

                System.out.println("The searching based on ID was successful. \n" +
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
        String query = String.format("SELECT * FROM products WHERE id = %d", supplier.getId());
        return getFilteredProducts(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = String.format("SELECT * FROM products WHERE id = %d", productCategory.getId());
        return getFilteredProducts(query);
    }

    private Product getProductFromDB(ResultSet resultSet) throws SQLException {
        String productName = resultSet.getString("name");
        String productDescription = resultSet.getString("description");
        float productDefaultPrice = resultSet.getFloat("defaultprice");
        String productCurrencyString = resultSet.getString("defaultcurrency");
        int productCategoryId = resultSet.getInt("category_id");
        int supplierId = resultSet.getInt("supplier_id");


        ProductCategory productCategory = ProductCategoryDaoMem.getInstance().find(productCategoryId);
        Supplier supplier = SupplierDaoMem.getInstance().find(supplierId);

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
