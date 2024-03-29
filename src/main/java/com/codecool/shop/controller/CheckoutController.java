package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.order.Order;
import com.codecool.shop.order.User;
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
import java.lang.reflect.Field;
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setUserInOrder(req);

        resp.sendRedirect("/payment");

        logger.info("Checkout confirmed.");
    }

    private void setUserInOrder(HttpServletRequest req) {
        User newUser = new User();

        setUserAttributesFromForm(req, newUser);

        Order.getInstance().setUser(newUser);
    }

    private void setUserAttributesFromForm(HttpServletRequest req, User user) {

        Enumeration formParameterNames = req.getParameterNames();

        while (formParameterNames.hasMoreElements()) {
            Object nextElement = formParameterNames.nextElement();

            String fieldName = (String) nextElement;
            String inputByUser = req.getParameter(fieldName);
            Field field = user.getDeclaredField(fieldName);
            user.setField(field, inputByUser);
        }
    }
}
