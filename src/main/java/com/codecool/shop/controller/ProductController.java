package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.order.LineItem;
import com.codecool.shop.order.Order;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products", productDataStore.getAll());


        // if a product is added to the cart, this adds it to the itemList in Order class
        if (req.getParameter("added-item") != null) {
            int itemToAdd = Integer.parseInt(req.getParameter("added-item"));
            Order.getInstance().add(itemToAdd);
        }

        // displaying the number of items in the cart on the index page
        int numberOfItemsInCart = Order.getInstance().calculateNumberOfItemsInCart();
        context.setVariable("numOfCartItems", numberOfItemsInCart);

        engine.process("product/index.html", context, resp.getWriter());
    }

}
