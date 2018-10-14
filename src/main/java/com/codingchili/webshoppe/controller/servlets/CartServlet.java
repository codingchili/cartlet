package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.Properties;
import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Created by Robin on 2015-10-01.
 * <p>
 * Handles the updating and display of the cart.
 */

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final String ORDER = "order";
    private static final String CART = "cart";
    private static final String SWISH_JSP = "swish.jsp";
    private static final String LOGIN_JSP = "login.jsp";
    private static final String PRODUCT = "product";
    private static final String CART_JSP = "cart.jsp";
    private static final String CLEAR = "clear";
    private static final String REMOVE = "remove";
    private static final String ACTION = "action";
    private static final String ACCOUNT = "account";
    public static final String SWISH = "swish";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter(ACTION);

        if (action == null) {
            doGet(req, resp);
        } else {
            switch (action) {
                case CLEAR:
                    clearCart(req, resp);
                    break;
                case REMOVE:
                    removeProduct(req, resp);
                    break;
                case ORDER:
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
            req.getSession().setAttribute(CART, new Cart(Session.getAccount(req)));
        } else {
            req.getSession().setAttribute(CART, new Cart());
        }
        Forwarding.to(CART_JSP, req, resp);
    }

    private void removeProduct(HttpServletRequest req, HttpServletResponse resp) {
        Product product = new Product();
        product.setId(Integer.parseInt(req.getParameter(PRODUCT)));

        if (Session.isAuthenticated(req)) {
            CartManager.removeFromCart(product, Session.getAccount(req));
            updateCart(req);
        } else {
            Cart cart = (Cart) req.getSession().getAttribute(CART);
            cart.getProducts().removeIf(inCart -> inCart.getId() == product.getId());
        }
        Forwarding.to(CART_JSP, req, resp);
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAuthenticated(req)) {
            try {
                Account account = Session.getAccount(req);
                int orderId = OrderManager.createOrder(account,
                        (Cart) req.getSession().getAttribute(CART));

                CartManager.clearCart(Session.getAccount(req));

                HttpSession session = req.getSession();
                session.setAttribute(ORDER, OrderManager.getOrderById(account, orderId));

                session.setAttribute(CART, new Cart(account));

                req.setAttribute(SWISH, Properties.get().getSwishReceiver());
                Forwarding.to(SWISH_JSP, req, resp);
            } catch (Exception e) {
                Forwarding.throwable(e, req, resp);
            }
        } else {
            Forwarding.to(LOGIN_JSP, req, resp);
        }
    }

    private void updateCart(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(CART,
                CartManager.getCart((Account)
                        session.getAttribute(ACCOUNT)));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Forwarding.to(CART_JSP, req, resp);
    }
}
