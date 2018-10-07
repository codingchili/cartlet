package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAdmin(req)) {
            req.setAttribute("managers", new AccountList(AccountManager.getManagers()));
            Forwarding.to("managers.jsp", req, resp);
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAdmin(req)) {
            String action = req.getParameter("action");

            if (action != null) {
                if (action.equals("remove")) {
                    try {
                        int managerId = Integer.parseInt(req.getParameter("manager"));
                        AccountManager.deRegister(managerId);
                    } catch (NumberFormatException ignored) {
                    } finally {
                        doGet(req, resp);
                    }
                } else if (action.equals("register")) {
                    req.setAttribute("registerResult", register(req));
                    doGet(req, resp);
                }
            } else
                doGet(req, resp);
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    private RegisterResult register(HttpServletRequest req) {
        RegisterResult registerResult = AccountManager.registerManager(
                req.getParameter("username"),
                req.getParameter("password"),
                req.getParameter("zip"),
                req.getParameter("street"));

        if (!req.getParameter("password").equals(req.getParameter("password-repeat")))
            registerResult.setPasswordMismatch(true);
        return registerResult;
    }
}
