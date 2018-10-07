package com.codingchili.webshoppe.controller;


import com.codingchili.webshoppe.model.ProductManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * Forwarding proxy class, loads the data required for all pages.
 */

public abstract class Forwarding {

    /**
     * Redirects the user to another resource.
     *
     * @param resource the requestor should be redirected to.
     * @throws IOException
     */
    public static void redirect(String resource, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(resource);
    }

    /**
     * Forwards a request to another resource while adding required data for all pages.
     *
     * @param resource specified as a relative url.
     */
    public static void to(String resource, ServletRequest req, ServletResponse resp) {
        try {
            req.setAttribute("categories", ProductManager.listCategories());
            req.getRequestDispatcher(resource).forward(req, resp);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
