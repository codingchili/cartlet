package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by Robin on 2015-10-01.
 *
 * Route for viewing products by category.
 */

@WebServlet("/category")
public class ProductCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        try {
            Category category = new Category();
            category.setCategoryId(Integer.parseInt(query));
            List<Product> products = ProductManager.findProductsByCategory(category);
            req.setAttribute("products", products);
            Forwarding.to("products.jsp", req, resp);
        } catch (NumberFormatException e) {
            Forwarding.to("/products", req, resp);
        }
    }
}
