package Controller;

import Model.Exception.ProductStoreException;
import Model.Exception.StoreException;
import Model.ProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-10-03.
 *
 * Handles the creation of products.
 */

@WebServlet("/storage")
public class Storage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Session.isManager(req)) {
            Forwarding.to("storage.jsp", req, resp);
        } else {
            req.setAttribute("message", "Not Authorized.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (Session.isManager(req) && action != null) {
            if (action.equals("addCategory")) {
                addCategory(req, resp);
            } else if (action.equals("addProduct")) {
                addProduct(req, resp);
            }
        } else {
            req.setAttribute("message", "Not Authorized");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
        String name = req.getParameter("name");

        try {
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            int categoryId = Integer.parseInt(req.getParameter("category"));
            int cost = Integer.parseInt(req.getParameter("cost"));
            ProductManager.addProduct(name, description, categoryId, quantity, cost);
            req.setAttribute("message", "Created product \"" + name + "\".");
            Forwarding.to("success.jsp", req, resp);
        } catch (NumberFormatException | StoreException e) {
            req.setAttribute("message", "Failed to add product.");
            Forwarding.to("error.jsp", req, resp);
        }
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("name");
        try {
            ProductManager.addCategory(category);
            req.setAttribute("message", "Added category \"" + category + "\".");
            Forwarding.to("success.jsp", req, resp);
        } catch (ProductStoreException e) {
            req.setAttribute("message", "Failed to add category.");
            Forwarding.to("error.jsp", req, resp);
        }
    }
}
