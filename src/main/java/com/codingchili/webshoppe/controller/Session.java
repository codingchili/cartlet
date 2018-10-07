package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Robin on 2015-09-29.
 *
 * Session logic.
 */
public class Session implements HttpSessionListener {
    private static final int SESSION_TIMEOUT = 30;
    private static final int SESSION_AUTHENTICATED_TIMEOUT = 2 * 60 * 3600;

    public static void authenticate(HttpServletRequest req, Account account) {
        HttpSession session = req.getSession(true);
        req.changeSessionId(); // prevent session fixation.
        session.setMaxInactiveInterval(SESSION_AUTHENTICATED_TIMEOUT);
        session.setAttribute("account", account);
        session.setAttribute("cart", new Cart(account));
    }

    public static void deAuthenticate(HttpServletRequest req) {
        req.getSession().invalidate();
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
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
        return (Account) req.getSession().getAttribute("account");
    }
}
