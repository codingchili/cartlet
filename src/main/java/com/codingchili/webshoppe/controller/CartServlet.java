package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.model.CartManager;
import com.codingchili.webshoppe.model.OrderManager;
import com.codingchili.webshoppe.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-01.
 *
 * Handles the updating and display of the cart.
 */

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            doGet(req, resp);
        } else if (action.equals("clear")) {
            clearCart(req, resp);
        } else if (action.equals("remove")) {
            removeProduct(req, resp);
        } else if (action.equals("order")) {
            createOrder(req, resp);
        }
    }

    private void clearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartManager.clearCart(Session.getAccount(req));
        Forwarding.to("cart.jsp", req, resp);
    }

    private void removeProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        product.setId(Integer.parseInt(req.getParameter("product")));
        CartManager.removeFromCart(product, Session.getAccount(req));
        Forwarding.to("cart.jsp", req, resp);
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean created = OrderManager.createOrder(Session.getAccount(req));

        if (created) {
            CartManager.clearCart(Session.getAccount(req));
            req.setAttribute("message", "The order was placed successfully.");
            Forwarding.to("success.jsp", req, resp);
        } else {
            req.setAttribute("message", "Failed to place the order right now, try later.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAuthenticated(req)) {
            Forwarding.to("cart.jsp", req, resp);
        } else {
            req.setAttribute("message", "Needs to be logged on to view cart.");
            Forwarding.to("error.jsp", req, resp);
        }
    }
}
