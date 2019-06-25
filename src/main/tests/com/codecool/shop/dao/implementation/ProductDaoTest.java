package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.DB.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.DB.ProductDaoDB;
import com.codecool.shop.dao.implementation.DB.SupplierDaoDB;
import com.codecool.shop.dao.implementation.Mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.Mem.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private ProductCategory testCategory;
    private ProductCategoryDaoDB productCategoryDaoDB;
    private Supplier testSupplier;
    private SupplierDaoDB supplierDaoDB;

    private static Stream getProductDao() {
        return Stream.of(ProductDaoMem.getInstance(), ProductDaoDB.getInstance());
    }


    @Disabled
    @ParameterizedTest
    @MethodSource("getProductDao")
    public static void testAreAllProductsReturned(ProductDao productDao) {
        assertEquals(4, productDao.getAll().size());
    }

    @BeforeEach
    @ParameterizedTest
    @MethodSource("getProductDao")
    private void setup() throws IOException {
        productCategoryDaoDB = ProductCategoryDaoDB.getInstance();
        supplierDaoDB = SupplierDaoDB.getInstance();

        testCategory = new ProductCategory("Category", "", "");
        testSupplier = new Supplier("Supplier", "");

        productCategoryDaoDB.add(testCategory);
        supplierDaoDB.add(testSupplier);

        String content = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir"), "src/main/scripts/resetDB.sql")));
        ProductDaoDB.getInstance().executeStatement(content);
    }

    @ParameterizedTest
    @MethodSource("getProductDao")
    public void testIsGetInstanceNotNull(ProductDao productDao) {
        ProductDao instance = (productDao instanceof SupplierDaoMem) ? ProductDaoMem.getInstance() : ProductDaoDB.getInstance();
        assertNotNull(instance);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getProductDao")
    public void testIsProductAdded(ProductDao productDao) {
        Product testProduct = new Product("Test add product", 0,"USD", "This is a test product to check if the add method works.", testCategory, testSupplier);
        productDao.add(testProduct);
        assertTrue(productDao.getAll().contains(testProduct));
    }


    @Disabled
    @ParameterizedTest
    @MethodSource("getProductDao")
    public void testIsCorrectProductFound(ProductDao productDao) {
        Product testProduct = new Product("Test2", 0, "USD", "", testCategory, testSupplier);
        productDao.add(testProduct);
        testProduct.setId(2);
        assertEquals(testProduct, productDao.find(2));
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getProductDao")
    public void testIsProductRemoved(ProductDao productDao) {
        Product testProduct = new Product("Test3", 0, "USD", "", testCategory, testSupplier);
        productDao.add(testProduct);
        testProduct.setId(3);
        productDao.remove(3);
        assertFalse(productDao.getAll().contains(testProduct));
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getProductDao")
    public void testAreProductsReturnedByCategory(ProductDao productDao) {
        ProductCategory newCategory = new ProductCategory("New category", "", "");
        productCategoryDaoDB.add(newCategory);
        Product testProduct = new Product("Test4", 0, "USD", "", newCategory, testSupplier);
        productDao.add(testProduct);
        assertEquals(1, productDao.getBy(newCategory).size());
        assertTrue(productDao.getBy(newCategory).contains(testProduct));
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getProductDao")
    public void testAreProductsReturnedBySupplier(ProductDao productDao) {
        Supplier newSupplier = new Supplier("New supplier", "");
        supplierDaoDB.add(newSupplier);
        Product testProduct = new Product("Test4", 0, "USD", "", testCategory, newSupplier);
        productDao.add(testProduct);
        assertEquals(1, productDao.getBy(newSupplier).size());
        assertTrue(productDao.getBy(newSupplier).contains(testProduct));
    }

}