package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Robin on 2015-09-28.
 * <p>
 * Handles the login event.
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet implements ServletContextListener {

    public static final String PRODUCTS = "/products";
    public static final String LOGIN_JSP = "login.jsp";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOGIN_RESULT = "loginResult";
    public static final String CART = "cart";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (Session.isAuthenticated(req)) {
            Forwarding.redirect(PRODUCTS, resp);
        } else {
            Forwarding.to(LOGIN_JSP, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LoginResult loginResult = AccountManager.authenticate(
                req.getParameter(USERNAME),
                req.getParameter(PASSWORD));

        req.setAttribute(LOGIN_RESULT, loginResult);

        if (loginResult.isErroneous()) {
            Forwarding.to(LOGIN_JSP, req, resp);
        } else {
            Session.authenticate(req, loginResult.getAccount());

            Cart cart = (Cart) req.getSession().getAttribute(CART);

            if (cart.getUniqueProducts() > 0) {
                Forwarding.to("/cart", req, resp);
            } else {
                Forwarding.to("/products", req, resp);
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
