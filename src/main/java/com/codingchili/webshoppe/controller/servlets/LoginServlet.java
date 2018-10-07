package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.LoginResult;
import com.codingchili.webshoppe.model.AccountManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-09-28.
 *
 * Handles the login event.
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet implements ServletContextListener {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAuthenticated(req))
            Forwarding.redirect("/products", resp);
        else
            Forwarding.to("login.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginResult loginResult = AccountManager.authenticate(
                req.getParameter("username"),
                req.getParameter("password"));
        req.setAttribute("loginResult", loginResult);

        if (loginResult.isErroneous())
            Forwarding.to("login.jsp", req, resp);
        else {
            Session.authenticate(req, loginResult.getAccount());
            Forwarding.to("/products", req, resp);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccountManager.registerAdmin("admin", "rawadminsocks", "admin_zip", "admin_street");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
