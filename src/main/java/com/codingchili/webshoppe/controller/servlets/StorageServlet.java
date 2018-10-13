package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.controller.*;
import com.codingchili.webshoppe.model.ProductManager;
import com.codingchili.webshoppe.model.exception.ProductStoreException;
import com.codingchili.webshoppe.model.exception.StoreException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import static com.codingchili.webshoppe.controller.Language.*;

/**
 * Created by Robin on 2015-10-03.
 *
 * Handles the creation of products.
 */
@WebServlet("/storage")
public class StorageServlet extends HttpServlet {
    private static final String STORAGE_JSP = "storage.jsp";
    private static final String ACTION = "action";
    private static final String ADD_CATEGORY = "addCategory";
    private static final String ADD_PRODUCT = "addProduct";
    private static final String DESCRIPTION = "description";
    private static final String QUANTITY = "quantity";
    private static final String CATEGORY = "category";
    private static final String COST = "cost";
    public static final String NAME = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (Session.isManager(req)) {
            Forwarding.to(STORAGE_JSP, req, resp);
        } else {
            Forwarding.error(Language.NOT_AUTHORIZED, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter(ACTION);

        if (Session.isManager(req) && action != null) {
            if (action.equals(ADD_CATEGORY)) {
                addCategory(req, resp);
            } else if (action.equals(ADD_PRODUCT)) {
                addProduct(req, resp);
            }
        } else {
            Forwarding.error(Language.NOT_AUTHORIZED, req, resp);
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp) {
        String description = req.getParameter(DESCRIPTION);
        String name = req.getParameter(NAME);

        try {
            int quantity = Integer.parseInt(req.getParameter(QUANTITY));
            int categoryId = Integer.parseInt(req.getParameter(CATEGORY));
            int cost = Integer.parseInt(req.getParameter(COST));
            ProductManager.addProduct(name, description, categoryId, quantity, cost);


            Forwarding.success(PRODUCT_CREATE_SUCCESS, req, resp);
        } catch (NumberFormatException | StoreException e) {
            Forwarding.error(PRODUCT_CREATE_FAIL, req, resp);
        }
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) {
        String category = req.getParameter(NAME);
        try {
            ProductManager.addCategory(category);
            Forwarding.success(CATEGORY_CREATE_SUCCESS, req, resp);
        } catch (ProductStoreException e) {
            Forwarding.error(CATEGORY_CREATE_FAIL, req, resp);
        }
    }
}
