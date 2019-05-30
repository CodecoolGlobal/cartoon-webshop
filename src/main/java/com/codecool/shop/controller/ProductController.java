package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.order.Order;
import jdk.jfr.Category;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        // if a product is added to the cart, this adds it to the itemList in Order class
        if (req.getParameter("added-item") != null) {
            int itemToAdd = Integer.parseInt(req.getParameter("added-item"));
            Order.getInstance().add(itemToAdd);
        }

        // setting variables for templateEngine
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        /*---------------------------------------------DATA TRANSFER--------------------------------------------------*/

        /*Category filtering*/

        if(req.getParameter("category_id") != null){
            int categoryID = Integer.parseInt(req.getParameter("category_id"));
            ProductCategory filteredCategory = productCategoryDataStore.find(categoryID);
            context.setVariable("products", productDataStore.getBy(filteredCategory));
        }

        /*Supplier filtering*/

        else if(req.getParameter("supplier_id") != null){
            int supplierID = Integer.parseInt(req.getParameter("supplier_id"));
            Supplier filteredSupplier = supplierDataStore.find(supplierID);
            context.setVariable("products", productDataStore.getBy(filteredSupplier));
        }

        else{
            context.setVariable("products", productDataStore.getAll());
        }

        /*Data transfer of Category & Supplier*/

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        /*------------------------------------------------------------------------------------------------------------*/

        // displaying the number of items in the cart on the index page
        int numberOfItemsInCart = Order.getInstance().calculateNumberOfItemsInCart();
        context.setVariable("numOfCartItems", numberOfItemsInCart);

        engine.process("product/index.html", context, resp.getWriter());
    }

}
