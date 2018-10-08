package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-01.
 * <p>
 * Handles the updating and display of the cart.
 */

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");

        if (action == null) {
            doGet(req, resp);
        } else {
            switch (action) {
                case "clear":
                    clearCart(req, resp);
                    break;
                case "remove":
                    removeProduct(req, resp);
                    break;
                case "order":
                    createOrder(req, resp);
                    break;
                default:
                    doGet(req, resp);
            }
        }
    }

    private void clearCart(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAuthenticated(req)) {
            CartManager.clearCart(Session.getAccount(req));
            req.getSession().setAttribute("cart", new Cart(Session.getAccount(req)));
        } else {
            req.getSession().setAttribute("cart", new Cart());
        }
        Forwarding.to("cart.jsp", req, resp);
    }

    private void removeProduct(HttpServletRequest req, HttpServletResponse resp) {
        Product product = new Product();
        product.setId(Integer.parseInt(req.getParameter("product")));

        if (Session.isAuthenticated(req)) {
            CartManager.removeFromCart(product, Session.getAccount(req));
            updateCart(req);
        } else {
            Cart cart = (Cart) req.getSession().getAttribute("cart");
            cart.getItems().removeIf(inCart -> inCart.getId() == product.getId());
        }
        Forwarding.to("cart.jsp", req, resp);
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAuthenticated(req)) {
            try {
                Account account = Session.getAccount(req);
                int orderId = OrderManager.createOrder(account);
                CartManager.clearCart(Session.getAccount(req));

                HttpSession session = req.getSession();
                session.setAttribute("order",
                        OrderManager.getOrderById(account, orderId));

                session.setAttribute("cart", new Cart(account));

                Forwarding.to("swish.jsp", req, resp);
            } catch (Exception e) {
                req.setAttribute("message", "Failed to place the order right now, try later.");
                Forwarding.to("error.jsp", req, resp);
            }
        } else {
            Forwarding.to("login.jsp", req, resp);
        }
    }

    private void updateCart(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("cart",
                CartManager.getCart((Account)
                        session.getAttribute("account")));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Forwarding.to("cart.jsp", req, resp);
    }
}
