package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    private static SupplierDao supplierDao;

    @AfterAll
    @ParameterizedTest
    @MethodSource("getSupplierDao")
    public static void testAreAllSuppliersReturned(SupplierDao supplierDao) {
        assertEquals(2, supplierDao.getAll().size());
    }

    private static Stream getSupplierDao() {
        return Stream.of(SupplierDaoMem.getInstance(), SupplierDaoDB.getInstance());
    }

    @BeforeEach
    private void setup() {
        supplierDao = SupplierDaoMem.getInstance();
    }


    @ParameterizedTest
    @MethodSource("getSupplierDao")
    public void testIsGetInstanceNotNull(SupplierDao supplierDao) {
        assertNotNull(ProductCategoryDaoMem.getInstance());
    }

    @MethodSource("getSupplierDao")
    @ParameterizedTest
    public void testIsSupplierAdded(SupplierDao supplierDao) {
        Supplier testSupplier = new Supplier("Test1", "");
        supplierDao.add(testSupplier);
        testSupplier.setId(1);
        assertTrue(supplierDao.getAll().contains(testSupplier));
    }

    @MethodSource("getSupplierDao")
    @ParameterizedTest
    public void testIsCorrectSupplierFound(SupplierDao supplierDao) {
        Supplier testSupplier = new Supplier("Test3", "");
        supplierDao.add(testSupplier);
        testSupplier.setId(2);
        assertEquals(testSupplier, supplierDao.find(2));
    }

    @Test
    public void testIsSupplierRemoved() {
        Supplier testSupplier = new Supplier("Test2", "");
        supplierDao.add(testSupplier);
        testSupplier.setId(3);
        supplierDao.remove(3);
        assertFalse(supplierDao.getAll().contains(testSupplier));
    }

}