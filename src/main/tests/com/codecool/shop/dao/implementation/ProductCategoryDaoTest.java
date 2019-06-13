package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private static ProductCategoryDao productCategoryDao;

    @AfterAll
    @Test
    public static void testAreAllCategoriesReturned() {
        assertEquals(2, productCategoryDao.getAll().size());
    }

    @BeforeEach
    private void setup() {
        productCategoryDao = ProductCategoryDaoMem.getInstance();
    }

    @Test
    public void testIsGetInstanceNotNull() {
        assertNotNull(ProductCategoryDaoMem.getInstance());
    }

    @Test
    public void testIsCategoryAdded() {
        ProductCategory testCategory = new ProductCategory("Test1", "", "");
        productCategoryDao.add(testCategory);
        testCategory.setId(1);
        assertTrue(productCategoryDao.getAll().contains(testCategory));
    }

    @Test
    public void testIsCorrectCategoryFound() {
        ProductCategory testCategory = new ProductCategory("Test3", "", "");
        productCategoryDao.add(testCategory);
        testCategory.setId(2);
        assertEquals(testCategory, productCategoryDao.find(2));
    }

    @Test
    public void testIsCategoryRemoved() {
        ProductCategory testCategory = new ProductCategory("Test2", "", "");
        productCategoryDao.add(testCategory);
        testCategory.setId(3);
        productCategoryDao.remove(3);
        assertFalse(productCategoryDao.getAll().contains(testCategory));
    }

}