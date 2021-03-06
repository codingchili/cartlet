package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.Properties;
import com.codingchili.webshoppe.model.*;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by Robin on 2015-09-29.
 * <p>
 * Session logic.
 */
public class Session implements SessionListener {
    private static final Logger logger = LoggerFactory.getLogger(Session.class);
    private static final SecureRandom csrf = new SecureRandom();
    private static final int SESSION_TIMEOUT = 30;
    private static final int SESSION_AUTHENTICATED_TIMEOUT = 2 * 60 * 3600;
    public static final String ACCOUNT = "account";
    public static final String CSRF = "csrf";
    public static final String LANGUAGE = "language";
    public static final String CART = "cart";

    public static void authenticate(HttpServletRequest req, Account account) {
        HttpSession session = req.getSession(true);
        req.changeSessionId(); // prevent session fixation.
        session.setMaxInactiveInterval(SESSION_AUTHENTICATED_TIMEOUT);
        session.setAttribute(ACCOUNT, account);

        Cart cart = (Cart) session.getAttribute(CART);

        if (cart.getUniqueProducts() == 0) {
            session.setAttribute(CART, CartManager.getCart(account));
        } else {
            // if the user had a cart when logging in, replace any existing cart.
            Async.task(() -> CartManager.setCartItems(cart.getProducts(), account), "copy cart on login.");
        }

        logger.info("session authenticated: " + session.getAttribute(ACCOUNT));
    }

    public static void deAuthenticate(HttpServletRequest req) {
        req.getSession().invalidate();
        logger.info("session destroyed: " + req.getSession().getAttribute(ACCOUNT));
    }

    @Override
    public void sessionCreated(io.undertow.server.session.Session session, HttpServerExchange exchange) {
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
        byte[] random = new byte[32];
        csrf.nextBytes(random);
        String token = Base64.getEncoder().encodeToString(random);

        logger.info("session created with c-surf token: " + token);
        session.setAttribute(CSRF, token);
        session.setAttribute(LANGUAGE, Properties.get().getLanguage());

        if (!session.getAttributeNames().contains(CART)) {
            session.setAttribute(CART, new Cart());
        }
    }

    @Override
    public void sessionDestroyed(io.undertow.server.session.Session session, HttpServerExchange exchange, SessionDestroyedReason reason) {
        // no op.
    }

    public static boolean isAuthenticated(HttpServletRequest req) {
        return getAccount(req) != null;
    }

    public static boolean isAdmin(HttpServletRequest req) {
        if (isAuthenticated(req)) {
            return getAccount(req).getRole().is(Role.Actor.Admin);
        } else
            return false;
    }

    public static boolean isManager(HttpServletRequest req) {
        if (isAuthenticated(req)) {
            return getAccount(req).getRole().is(Role.Actor.Admin) || getAccount(req).getRole().is(Role.Actor.Manager);
        } else
            return false;
    }

    public static Account getAccount(HttpServletRequest req) {
        return (Account) req.getSession().getAttribute(ACCOUNT);
    }
}
