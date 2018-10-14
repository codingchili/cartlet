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
 * Handles the registrations of USER accounts.
 */

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAuthenticated(req)) {
            Forwarding.to("/products", req, resp);
        } else {
            Forwarding.to("register.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        RegisterResult registerResult = AccountManager.register(
                new Account()
                    .setUsername(req.getParameter("username"))
                    .setStreet(req.getParameter("street"))
                    .setZip(req.getParameter("zip"))
                    .setPassword(req.getParameter("password"))
                    .setPasswordRepeat(req.getParameter("password-repeat")));

        req.setAttribute("registerResult", registerResult);

        if (registerResult.isErroneous()) {
            Forwarding.to("register.jsp", req, resp);
        } else {
            Session.authenticate(req, registerResult.getAccount());
            Cart cart = (Cart) req.getSession().getAttribute("cart");

            if (cart.getUniqueProducts() > 0) {
                Forwarding.to("/cart", req, resp);
            } else {
                Forwarding.to("/products", req, resp);
            }
        }
    }
}
