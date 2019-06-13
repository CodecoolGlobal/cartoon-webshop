package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    @Disabled
    @ParameterizedTest
    @MethodSource("getSupplierDao")
    public static void testAreAllSuppliersReturned(SupplierDao supplierDao) {
        assertEquals(4, supplierDao.getAll().size());
    }

    private static Stream getSupplierDao() {
        return Stream.of(SupplierDaoMem.getInstance(), SupplierDaoDB.getInstance());
    }


    @ParameterizedTest
    @MethodSource("getSupplierDao")
    public void testIsGetInstanceNotNull(SupplierDao supplierDao) {
        SupplierDao instance = (supplierDao instanceof SupplierDaoMem) ? SupplierDaoMem.getInstance() : SupplierDaoDB.getInstance();
        assertNotNull(instance);
    }


    @ParameterizedTest
    @MethodSource("getSupplierDao")
    public void testIsSupplierAdded(SupplierDao supplierDao) {
        Supplier testSupplier = new Supplier("Test add supplier", "This is a test supplier to check if the add method works.");
        int numberOfSuppliersBefore = supplierDao.getAll().size();
        supplierDao.add(testSupplier);
        int numberOfSuppliersAfter = supplierDao.getAll().size();
        assertEquals(numberOfSuppliersAfter, numberOfSuppliersBefore + 1);
    }

//    @Disabled
//    @ParameterizedTest
//    @MethodSource("getSupplierDao")
//    public void testIsCorrectSupplierFound(SupplierDao supplierDao) {
//        Supplier testSupplier = new Supplier("Test find supplier", "This is a test supplier to check if the find method works.");
//        supplierDao.add(testSupplier);
//        int id = 0;
//        assertEquals(testSupplier, supplierDao.find(id));
//    }

    @ParameterizedTest
    @MethodSource("getSupplierDao")
    public void testIsSupplierRemoved(SupplierDao supplierDao) {
        Supplier testSupplier = new Supplier("Test supplier", "This is a test supplier.");
        supplierDao.add(testSupplier);
        testSupplier.setId(999);
        supplierDao.remove(999);
        assertFalse(supplierDao.getAll().contains(testSupplier));
    }

}