package Controller;

import Model.Account;
import Model.CartManager;
import Model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-01.
 *
 * Handles the request to add a product to a cart.
 */

@WebServlet("/buy")
public class Buy extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (Session.isAuthenticated(req)) {
            try {
                int count = Integer.parseInt(req.getParameter("count"));
                Product product = new Product();
                product.setId(Integer.parseInt(req.getParameter("product")));

                CartManager.addToCart(product, count, (Account) session.getAttribute("account"));
                Forwarding.redirect("cart", resp);
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Failed to add product to cart.");
                Forwarding.to("error.jsp", req, resp);
            }
        } else {
            Forwarding.redirect("login", resp);
        }
    }
}
