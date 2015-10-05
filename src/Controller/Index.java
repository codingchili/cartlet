package Controller;

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
 * This is the entry point for the webshop, products are listed here.
 */

@WebServlet("/products")
public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = (req.getParameter("search") != null ? req.getParameter("search") : "");
        req.setAttribute("products", ProductManager.findProductsByName(query));
        Forwarding.to("products.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
