package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    @Disabled
    @ParameterizedTest
    @MethodSource("getCategoryDao")
    public static void testAreAllCategoriesReturned(ProductCategoryDao productCategoryDao) {
        assertEquals(2, productCategoryDao.getAll().size());
    }

    private static Stream getCategoryDao() {
        return Stream.of(ProductCategoryDaoMem.getInstance(), ProductCategoryDaoDB.getInstance());
    }

    @ParameterizedTest
    @MethodSource("getCategoryDao")
    public void testIsGetInstanceNotNull(ProductCategoryDao productCategoryDao) {
        ProductCategoryDao instance = (productCategoryDao instanceof ProductCategoryDaoMem) ? ProductCategoryDaoMem.getInstance() : ProductCategoryDaoDB.getInstance();
        assertNotNull(instance);
    }

    @ParameterizedTest
    @MethodSource("getCategoryDao")
    public void testIsCategoryAdded(ProductCategoryDao productCategoryDao) {
        ProductCategory testCategory = new ProductCategory("Test add category", "","This is a test category to check if the add method works.");
        int numberOfCategoriesBefore = productCategoryDao.getAll().size();
        productCategoryDao.add(testCategory);
        int numberOfCategoriesAfter = productCategoryDao.getAll().size();
        assertEquals(numberOfCategoriesAfter, numberOfCategoriesBefore + 1);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getCategoryDao")
    public void testIsCorrectCategoryFound(ProductCategoryDao productCategoryDao) {
        ProductCategory testCategory = new ProductCategory("Test3", "", "");
        productCategoryDao.add(testCategory);
        testCategory.setId(2);
        assertEquals(testCategory, productCategoryDao.find(2));
    }

    @ParameterizedTest
    @MethodSource("getCategoryDao")
    public void testIsCategoryRemoved(ProductCategoryDao productCategoryDao) {
        ProductCategory testCategory = new ProductCategory("Test supplier","", "This is a test supplier.");
        productCategoryDao.add(testCategory);
        testCategory.setId(999);
        productCategoryDao.remove(999);
        assertFalse(productCategoryDao.getAll().contains(testCategory));
    }

}