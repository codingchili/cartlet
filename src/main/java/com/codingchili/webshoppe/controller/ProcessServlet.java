package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.model.*;
import com.codingchili.webshoppe.model.exception.NoSuchOrderException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-02.
 *
 * Fetches an order from the model that is ready for packing.
 */

@WebServlet("/process")
public class ProcessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isManager(req)) {
            Forwarding.to("process.jsp", req, resp);
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isManager(req)) {
            try {
                req.setAttribute("order", OrderManager.getOrderForShipping());
                Forwarding.to("process.jsp", req, resp);
            } catch (NoSuchOrderException noSuchOrder) {
                req.setAttribute("message", "There are no orders to pack.");
                Forwarding.to("success.jsp", req, resp);
            }
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }
}
