package Controller;

import Model.RegisterResult;
import Model.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-09-28.
 *
 * Handles the registrations of USER accounts.
 */

@WebServlet("/register")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null)
            Forwarding.to("register.jsp", req, resp);
        else
            Forwarding.to("/products", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterResult registerResult = AccountManager.register(
                req.getParameter("username"),
                req.getParameter("password"),
                req.getParameter("zip"),
                req.getParameter("street"));

        if (!req.getParameter("password").equals(req.getParameter("password-repeat")))
            registerResult.setPasswordMismatch(true);

        req.setAttribute("registerResult", registerResult);

        if (registerResult.isErroneous())
            Forwarding.to("register.jsp", req, resp);
        else {
            Session.authenticate(req, registerResult.getAccount());
            Forwarding.to("/products", req, resp);
        }
    }
}
