package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.controller.Session;
import com.codingchili.webshoppe.model.ProductManager;
import com.codingchili.webshoppe.model.exception.NoSuchProductException;
import com.codingchili.webshoppe.model.exception.ProductStoreException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import static com.codingchili.webshoppe.controller.Language.*;

/**
 * Created by Robin on 2015-10-03.
 *
 * Handles the editing of a single products image and props.
 */

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final String VIEW_PRODUCT = "view?product=";
    private static final String PRODUCT_IMAGE = "product-image";
    private static final String DESCRIPTION = "description";
    private static final String QUANTITY = "quantity";
    private static final String EDIT_JSP = "edit.jsp";
    private static final String PRODUCT = "product";
    private static final String COST = "cost";
    public static final String NAME = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isManager(req)) {
            try {
                int product = Integer.parseInt(req.getParameter(PRODUCT));
                req.setAttribute(PRODUCT, ProductManager.findProductById(product));
                Forwarding.to(EDIT_JSP, req, resp);
            } catch (NumberFormatException e) {
                Forwarding.error(PRODUCT_NOT_FOUND, req, resp);
            }
        } else {
            Forwarding.error(NOT_AUTHORIZED, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (Session.isManager(req)) {
            try {
                int productId = Integer.parseInt(req.getParameter(PRODUCT));
                updateProduct(req, productId);
                updateProductImage(req, productId);
                Forwarding.redirect(VIEW_PRODUCT + productId, resp);
            } catch (NumberFormatException | ProductStoreException e) {
                Forwarding.error(PRODUCT_UPDATE_FAILED, req, resp);
            }
        } else {
            Forwarding.error(NOT_AUTHORIZED, req, resp);
        }
    }

    private void updateProduct(HttpServletRequest req, int productId) throws NoSuchProductException {
        String description = req.getParameter(DESCRIPTION);
        String name = req.getParameter(NAME);
        int cost = Integer.parseInt(req.getParameter(COST));
        int quantity = Integer.parseInt(req.getParameter(QUANTITY));

        ProductManager.updateProduct(productId, cost, quantity, name, description);
    }

    private void updateProductImage(HttpServletRequest req, int productId) throws ProductStoreException {
        String image = req.getParameter(PRODUCT_IMAGE);

        if (image != null && image.length() != 0) {
            ProductManager.setProductImage(productId, image);
        }
    }
}