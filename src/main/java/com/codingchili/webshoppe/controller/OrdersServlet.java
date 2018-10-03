package com.codingchili.webshoppe.controller;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAuthenticated(req)) {
            req.setAttribute("orders", OrderManager.getOrders(Session.getAccount(req)));
            Forwarding.to("orders.jsp", req, resp);
        } else
            Forwarding.to("login.jsp", req, resp);
    }
}
