package Controller;

import Model.Product;
import Model.ProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-09-30.
 *
 * Route for viewing product details.
 */

@WebServlet("/view")
public class View extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product;

        try {
            int productId = Integer.parseInt(req.getParameter("product"));
            product = ProductManager.findProductById(productId);
            req.setAttribute("product", product);
        } catch (NumberFormatException ignored) {
        }
        Forwarding.to("view.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
