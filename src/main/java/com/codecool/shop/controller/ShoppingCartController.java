package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.order.LineItem;
import com.codecool.shop.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ShoppingCartController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    private Order order = Order.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("order", order);

        List<LineItem> items = order.getItems();
        context.setVariable("items", items);

        engine.process("product/shopping_cart.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        addOneItemToOrder(req);

        removeOneItemFromOrder(req);

        this.doGet(req, resp);
    }

    private void removeOneItemFromOrder(HttpServletRequest req) {
        if (req.getParameter("removed-item") != null) {
            int itemToRemove = Integer.parseInt(req.getParameter("removed-item"));
            order.remove(itemToRemove);
            logger.info("Item with id {} removed from the order.", itemToRemove);
        }
    }

    private void addOneItemToOrder(HttpServletRequest req) {
        if (req.getParameter("added-item") != null) {
            int itemToAdd = Integer.parseInt(req.getParameter("added-item"));
            order.add(itemToAdd);
            logger.info("Item with id {} added to the order.", itemToAdd);
        }
    }
}
