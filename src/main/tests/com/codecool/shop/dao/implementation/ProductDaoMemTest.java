package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {

    private static ProductCategoryDao productCategoryDao;

    @BeforeAll
    private static void setup() {
        productCategoryDao = ProductCategoryDaoMem.getInstance();
    }

    @Test
    public void testIsProductCategoryAdded() {
        ProductCategory testCategory = new ProductCategory("Test category", "Test department", "Test description");
        productCategoryDao.add(testCategory);
        assertTrue(productCategoryDao.getAll().contains(testCategory));
    }

    @Test
    public void testIsProductCategoryRemoved() {
        ProductCategory differentTestCategory = new ProductCategory("Test category2", "Test department2", "Test description2");
        differentTestCategory.setId(1);
        productCategoryDao.add(differentTestCategory);
        productCategoryDao.remove(1);
        assertFalse(productCategoryDao.getAll().contains(differentTestCategory));
    }

    @Test
    public void testFindsCorrectProductCategory() {
//        ProductCategory differentTestCategory = new ProductCategory()
    }

}