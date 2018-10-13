package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-01.
 * <p>
 * Handles the request to add a product to a cart.
 */

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {
    private static final String CART = "cart";
    private static final String ACCOUNT = "account";
    private static final String PRODUCT = "product";
    private static final String COUNT = "count";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        try {
            int count = Integer.parseInt(req.getParameter(COUNT));
            Product product = new Product();
            product.setId(Integer.parseInt(req.getParameter(PRODUCT)));

            if (Session.isAuthenticated(req)) {
                Account account = (Account) session.getAttribute(ACCOUNT);
                product.setCount(count);
                CartManager.addToCart(product, account);
                session.setAttribute(CART, CartManager.getCart(account));
            } else {
                product = ProductManager.findProductById(product.getId());
                product.setCount(count);
                Cart cart = (Cart) session.getAttribute(CART);

                boolean isInCart = false;
                for (Product inCart: cart.getItems()) {
                    if (inCart.getId() == product.getId()) {
                        inCart.setCount(inCart.getCount() + count);

                        isInCart = true;
                    }
                }
                if (!isInCart) {
                    cart.getItems().add(product);
                }
            }

            Forwarding.redirect(CART, resp);
        } catch (NumberFormatException e) {
            Forwarding.throwable(e, req, resp);
        }
    }
}
