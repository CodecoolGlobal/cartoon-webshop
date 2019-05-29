package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.order.LineItem;
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

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Order order = Order.getInstance();
        List<LineItem> items = order.getItems();

        // if a product is added to the cart, this adds it to the itemList in Order class
        if (req.getParameter("added-item") != null) {
            int itemToAdd = Integer.parseInt(req.getParameter("added-item"));
            order.add(itemToAdd);
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("order", order);
        context.setVariable("items", items);
        engine.process("product/shopping_cart.html", context, resp.getWriter());



    }

}
