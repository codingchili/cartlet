package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.Order;
import com.codingchili.webshoppe.model.OrderManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Optional;

import static com.codingchili.webshoppe.controller.Language.ORDER_LOAD_FAILED;
import static com.codingchili.webshoppe.controller.Language.ORDER_NOT_FOUND;

/**
 * Created by Robin on 2015-10-02.
 *
 * Retrieves an order from the model and associated products.
 */

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final String ID = "id";
    private static final String ORDER_JSP = "order.jsp";
    private static final String ORDER = "order";
    private static final String LOGIN = "login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isAuthenticated(req)) {
            try {
                int orderId = Integer.parseInt(req.getParameter(ID));

                Optional<Order> order = OrderManager.getOrderById(Session.getAccount(req), orderId);

                if (order.isPresent()) {
                    req.setAttribute(ORDER, order.get());
                    Forwarding.to(ORDER_JSP, req, resp);
                } else {
                    Forwarding.error(ORDER_NOT_FOUND, req, resp);
                }
            } catch (NumberFormatException e) {
                Forwarding.error(ORDER_LOAD_FAILED, req, resp);
            }
        } else {
            Forwarding.to(LOGIN, req, resp);
        }
    }
}
