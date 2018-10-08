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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (Session.isAuthenticated(req)) {
            Forwarding.redirect("/products", resp);
        } else {
            Forwarding.to("login.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LoginResult loginResult = AccountManager.authenticate(
                req.getParameter("username"),
                req.getParameter("password"));
        req.setAttribute("loginResult", loginResult);

        if (loginResult.isErroneous()) {
            Forwarding.to("login.jsp", req, resp);
        } else {
            Session.authenticate(req, loginResult.getAccount());

            Cart cart = (Cart) req.getSession().getAttribute("cart");

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
