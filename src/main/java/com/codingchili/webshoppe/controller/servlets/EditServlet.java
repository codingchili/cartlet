package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.exception.NoSuchProductException;
import com.codingchili.webshoppe.model.exception.ProductStoreException;
import com.codingchili.webshoppe.model.ProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-03.
 *
 * Handles the editing of a single products image and props.
 */

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isManager(req)) {
            try {
                int product = Integer.parseInt(req.getParameter("product"));
                req.setAttribute("product", ProductManager.findProductById(product));
                Forwarding.to("edit.jsp", req, resp);
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Product not found.");
                Forwarding.to("error.jsp", req, resp);
            }
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isManager(req)) {
            try {
                int productId = Integer.parseInt(req.getParameter("product"));
                updateProduct(req, productId);
                updateProductImage(req, productId);
                Forwarding.redirect("view?product=" + productId, resp);
            } catch (NumberFormatException | ProductStoreException e) {
                e.printStackTrace();
                req.setAttribute("message", "Could not update from specified indata.");
                Forwarding.to("error.jsp", req, resp);
            }
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    private void updateProduct(HttpServletRequest req, int productId) throws NoSuchProductException {
        String description = req.getParameter("description");
        String name = req.getParameter("name");
        int cost = Integer.parseInt(req.getParameter("cost"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        ProductManager.updateProduct(productId, cost, quantity, name, description);
    }

    private void updateProductImage(HttpServletRequest req, int productId) throws ProductStoreException {
        String image = req.getParameter("product-image");

        if (image != null && image.length() != 0) {
            ProductManager.setProductImage(productId, image);
        }
    }
}
