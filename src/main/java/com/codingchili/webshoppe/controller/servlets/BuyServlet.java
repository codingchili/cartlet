package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-01.
 *
 * Handles the request to add a product to a cart.
 */

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (Session.isAuthenticated(req)) {
            try {
                int count = Integer.parseInt(req.getParameter("count"));
                Product product = new Product();
                product.setId(Integer.parseInt(req.getParameter("product")));

                Account account = (Account) session.getAttribute("account");
                CartManager.addToCart(product, count, account);
                session.setAttribute("cart", CartManager.getCart(account));

                Forwarding.redirect("cart", resp);
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Failed to add product to cart.");
                Forwarding.to("error.jsp", req, resp);
            }
        } else {
            Forwarding.redirect("login", resp);
        }
    }
}
