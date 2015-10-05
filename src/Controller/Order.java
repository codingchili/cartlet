package Controller;

import Model.OrderManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-02.
 *
 * Retrieves an order from the model and associated products.
 */

@WebServlet("/order")
public class Order extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isAuthenticated(req)) {
            try {
                int orderId = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("order", OrderManager.getOrderById(Session.getAccount(req), orderId));
                Forwarding.to("order.jsp", req, resp);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("message", "Failed to load order details, try later.");
                Forwarding.to("error.jsp", req, resp);
            }
        } else {
            Forwarding.to("login", req, resp);
        }
    }
}
