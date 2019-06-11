package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

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
        return null;
    }

    @Override
    public void remove(int id) {
        String statement = String.format("DELETE FROM products WHERE id = %d", id);
        super.executeStatement(statement);
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

}
