package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DB.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.DB.ProductDaoDB;
import com.codecool.shop.dao.implementation.DB.SupplierDaoDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.order.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private ProductDao productDataStore = ProductDaoDB.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoDB.getInstance();

    private Order order = Order.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<Product> filteredProducts = filterProducts(req);
        context.setVariable("products", filteredProducts);

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        int numberOfItemsInCart = order.calculateNumberOfItemsInCart();
        context.setVariable("numOfCartItems", numberOfItemsInCart);

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        addOneItemToOrder(req);

        this.doGet(req, resp);
    }


    private List<Product> filterProducts(HttpServletRequest req) {

        List<Product> filteredProducts;

        if(req.getParameter("category_id") != null){
            int categoryID = Integer.parseInt(req.getParameter("category_id"));
            ProductCategory filteredCategory = productCategoryDataStore.find(categoryID);
            filteredProducts = productDataStore.getBy(filteredCategory);
        }

        else if(req.getParameter("supplier_id") != null){
            int supplierID = Integer.parseInt(req.getParameter("supplier_id"));
            Supplier filteredSupplier = supplierDataStore.find(supplierID);
            filteredProducts = productDataStore.getBy(filteredSupplier);
        }

        else{
            filteredProducts = productDataStore.getAll();
        }

        return filteredProducts;
    }

    private void addOneItemToOrder(HttpServletRequest req) {
        if (req.getParameter("added-item") != null) {
            int itemToAdd = Integer.parseInt(req.getParameter("added-item"));
            order.add(itemToAdd);
        }
    }

}
