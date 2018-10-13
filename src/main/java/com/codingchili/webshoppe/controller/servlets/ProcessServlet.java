package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.*;
import com.codingchili.webshoppe.model.*;
import com.codingchili.webshoppe.model.exception.NoSuchOrderException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.codingchili.webshoppe.controller.Language.ORDERS_NONE_TO_PACK;

/**
 * Created by Robin on 2015-10-02.
 *
 * Fetches an order from the model that is ready for packing.
 */

@WebServlet("/process")
public class ProcessServlet extends HttpServlet {
    private static final String PROCESS_JSP = "process.jsp";
    private static final String ORDER = "order";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isManager(req)) {
            Forwarding.to(PROCESS_JSP, req, resp);
        } else {
            Forwarding.to(Language.NOT_AUTHORIZED, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isManager(req)) {
            try {
                req.setAttribute(ORDER, OrderManager.getOrderForShipping());
                Forwarding.to(PROCESS_JSP, req, resp);
            } catch (NoSuchOrderException noSuchOrder) {
                Forwarding.success(ORDERS_NONE_TO_PACK, req, resp);
            }
        } else {
            Forwarding.to(Language.NOT_AUTHORIZED, req, resp);
        }
    }
}
