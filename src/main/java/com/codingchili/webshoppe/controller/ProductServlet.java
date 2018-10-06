package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.model.Product;
import com.codingchili.webshoppe.model.ProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Robin on 2015-09-30.
 *
 * This is the entry point for the webshop, products are listed here.
 */

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String query = (req.getParameter("search") != null ? req.getParameter("search") : "");

        List<Product> products = ProductManager.findProductsByName(query);

        if (products.size() == 1) {
            req.setAttribute("product", products.get(0));
            Forwarding.to("view.jsp", req, resp);
        } else {
            req.setAttribute("products", products);
            Forwarding.to("products.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
