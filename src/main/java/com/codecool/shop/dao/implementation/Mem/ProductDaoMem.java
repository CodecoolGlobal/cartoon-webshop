package com.codecool.shop.dao.implementation.Mem;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

    /*PRODUCT NAME COMPARATOR*/
    private static Comparator<Product> ProductNameComparator = (o1, o2) -> {
        String productName1 = o1.getName().toUpperCase();
        String productName2 = o2.getName().toUpperCase();
        return productName1.compareTo(productName2);
    };

    /*PRODUCT ID COMPARATOR*/
    private static Comparator<Product> ProductIDComparator = (o1, o2) -> {
        Integer productName1 = o1.getId();
        Integer productName2 = o2.getId();
        return productName1.compareTo(productName2);
    };

    public void doSort(){
        data.sort(ProductDaoMem.ProductNameComparator);
        int idIndex = 1;
        for (Product product: data) {
            product.setId(idIndex);
            idIndex++;
        }
    }

    @Override
    public List<Product> searchByExpression(String searchedExpression) {
        return null;
    }
}
