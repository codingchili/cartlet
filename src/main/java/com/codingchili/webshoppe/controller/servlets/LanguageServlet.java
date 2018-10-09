package com.codingchili.webshoppe.controller.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Allows the user to set the language of their session.
 */
@WebServlet("/language")
public class LanguageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String language = req.getParameter("language");
        String callback = req.getParameter("callback");

        req.getSession().setAttribute("language", language);
        resp.sendRedirect(callback);
    }
}
