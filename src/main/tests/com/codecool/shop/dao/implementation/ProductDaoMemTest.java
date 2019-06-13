package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {

    private static ProductDao productDao;
    private ProductCategory testCategory;
    private Supplier testSupplier;

    @AfterAll
    @Test
    public static void testAreAllProductsReturned() {
        assertEquals(4, productDao.getAll().size());
    }

    @BeforeEach
    private void setup() {
        productDao = ProductDaoMem.getInstance();
        testCategory = new ProductCategory("Category", "", "");
        testSupplier = new Supplier("Supplier", "");
    }

    @Test
    public void testIsGetInstanceNotNull() {
        assertNotNull(ProductDaoMem.getInstance());
    }

    @Test
    public void testIsProductAdded() {
        Product testProduct = new Product("Test1", 0,"USD", "", testCategory, testSupplier);
        productDao.add(testProduct);
        testProduct.setId(1);
        assertTrue(productDao.getAll().contains(testProduct));
    }

    @Test
    public void testIsCorrectProductFound() {
        Product testProduct = new Product("Test2", 0, "USD", "", testCategory, testSupplier);
        productDao.add(testProduct);
        testProduct.setId(2);
        assertEquals(testProduct, productDao.find(2));
    }

    @Test
    public void testIsProductRemoved() {
        Product testProduct = new Product("Test3", 0, "USD", "", testCategory, testSupplier);
        productDao.add(testProduct);
        testProduct.setId(3);
        productDao.remove(3);
        assertFalse(productDao.getAll().contains(testProduct));
    }

    @Test
    public void testAreProductsReturnedByCategory() {
        ProductCategory newCategory = new ProductCategory("New category", "", "");
        Product testProduct = new Product("Test4", 0, "USD", "", newCategory, testSupplier);
        productDao.add(testProduct);
        assertEquals(1, productDao.getBy(newCategory).size());
        assertTrue(productDao.getBy(newCategory).contains(testProduct));
    }

    @Test
    public void testAreProductsReturnedBySupplier() {
        Supplier newSupplier = new Supplier("New supplier", "");
        Product testProduct = new Product("Test4", 0, "USD", "", testCategory, newSupplier);
        productDao.add(testProduct);
        assertEquals(1, productDao.getBy(newSupplier).size());
        assertTrue(productDao.getBy(newSupplier).contains(testProduct));
    }

}