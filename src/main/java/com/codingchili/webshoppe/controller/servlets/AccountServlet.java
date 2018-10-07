package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-02.
 *
 * Handles the route for displaying information about a single account.
 */

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAuthenticated(req))
            Forwarding.to("account.jsp", req, resp);
        else
            Forwarding.to("login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action != null) {
            if (action.equals("deRegister")) {
                AccountManager.deRegister(Session.getAccount(req));
                Session.deAuthenticate(req);
                doGet(req, resp);
            } else {
                doGet(req, resp);
            }
        } else {
            doGet(req, resp);
        }
    }
}
