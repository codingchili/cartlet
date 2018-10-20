package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.*;
import com.codingchili.webshoppe.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Optional;

/**
 * Created by Robin on 2015-10-02.
 * <p>
 * Fetches an order from the model that is ready for packing.
 */

@WebServlet("/process")
public class ProcessServlet extends HttpServlet {
    private static final String PROCESS_JSP = "process.jsp";
    private static final String ORDER_ID = "order-id";
    private static final String ORDER = "order";
    private static final String STATISTICS = "stats";
    private static final String ACTION = "action";
    private static final String UPDATE = "update";
    private static final String STATUS = "status";
    private static final String SEARCH = "search";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isManager(req)) {
            req.setAttribute(STATISTICS, OrderManager.getStatistics());
            Forwarding.to(PROCESS_JSP, req, resp);
        } else {
            Forwarding.error(Language.NOT_AUTHORIZED, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isManager(req)) {
            String orderId = req.getParameter(ORDER_ID);
            String action = req.getParameter(ACTION);

            // no order id: return next to ship.
            if (action.equals(SEARCH) && orderId == null || orderId.isEmpty()) {
                // todo: sets status to packing !
                Optional<Order> shipping = OrderManager.getOrderForShipping();

                if (shipping.isPresent()) {
                    req.setAttribute(ORDER, shipping.get());
                    Forwarding.to(PROCESS_JSP, req, resp);
                } else {
                    Forwarding.success(Language.ORDERS_NONE_TO_PACK, req, resp);
                }
            } else {
                Optional<Order> order = OrderManager.getOrderById(Integer.parseInt(orderId));

                if (order.isPresent()) {
                    if (action.equals(UPDATE)) {
                        OrderStatus status = OrderStatus.valueOf(req.getParameter(STATUS));
                        OrderManager.updateOrder(order.get().getOrderId(), status);
                        order.get().setStatus(status);
                    }
                    req.setAttribute(ORDER, order.get());
                    Forwarding.to(PROCESS_JSP, req, resp);
                } else {
                    Forwarding.error(Language.ORDER_NOT_FOUND, req, resp);
                }
            }
        } else {
            Forwarding.to(Language.NOT_AUTHORIZED, req, resp);
        }
    }
}
