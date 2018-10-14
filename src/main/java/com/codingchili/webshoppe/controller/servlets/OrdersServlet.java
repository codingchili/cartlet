package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.OrderManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-01.
 *
 * Returns all orders in the context of the current user.
 */

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private static final String ORDERS = "orders";
    private static final String ORDERS_JSP = "orders.jsp";
    private static final String LOGIN_JSP = "login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAuthenticated(req)) {
            req.setAttribute(ORDERS, OrderManager.getOrders(Session.getAccount(req)));
            Forwarding.to(ORDERS_JSP, req, resp);
        } else
            Forwarding.to(LOGIN_JSP, req, resp);
    }
}
