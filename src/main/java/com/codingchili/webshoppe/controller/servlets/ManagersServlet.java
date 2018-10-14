package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.*;
import com.codingchili.webshoppe.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-02.
 *
 * Handles the registration of Manager accounts.
 */

@WebServlet("/managers")
public class ManagersServlet extends HttpServlet {
    private static final String MANAGERS = "managers";
    private static final String MANAGERS_JSP = "managers.jsp";
    private static final String ACTION = "action";
    private static final String REMOVE = "remove";
    private static final String MANAGER = "manager";
    private static final String REGISTER = "register";
    private static final String REGISTER_RESULT = "registerResult";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ZIP = "zip";
    private static final String STREET = "street";
    private static final String PASSWORD_REPEAT = "password-repeat";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAdmin(req)) {
            req.setAttribute(MANAGERS, new AccountList(AccountManager.getManagers()));
            Forwarding.to(MANAGERS_JSP, req, resp);
        } else {
            Forwarding.to(Language.NOT_AUTHORIZED, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAdmin(req)) {
            String action = req.getParameter(ACTION);

            if (action != null) {
                if (action.equals(REMOVE)) {
                    try {
                        int managerId = Integer.parseInt(req.getParameter(MANAGER));
                        AccountManager.deRegister(managerId);
                    } catch (NumberFormatException ignored) {
                    } finally {
                        doGet(req, resp);
                    }
                } else if (action.equals(REGISTER)) {
                    req.setAttribute(REGISTER_RESULT, register(req));
                    doGet(req, resp);
                }
            } else
                doGet(req, resp);
        } else {
            Forwarding.error(Language.NOT_AUTHORIZED, req, resp);
        }
    }

    private RegisterResult register(HttpServletRequest req) {
        return AccountManager.registerManager(
                new Account()
                .setUsername(req.getParameter(USERNAME))
                .setPassword(req.getParameter(PASSWORD))
                .setPasswordRepeat(req.getParameter(PASSWORD_REPEAT))
                .setZip(req.getParameter(ZIP))
                .setStreet(req.getParameter(STREET)));
    }
}
