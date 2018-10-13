package com.codingchili.webshoppe.controller;


import com.codingchili.webshoppe.model.ProductManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * Forwarding proxy class, loads the data required for all pages.
 */

public abstract class Forwarding {
    private static final Logger logger = LoggerFactory.getLogger(Forwarding.class);
    private static final SecureRandom random = new SecureRandom();
    private static final String MESSAGE = "message";
    private static final String ERROR_JSP = "error.jsp";
    private static final String SUCCESS_JSP = "success.jsp";
    private static final String SERVER_EXCEPTION = "server.exception";
    private static final String CATEGORIES = "categories";

    /**
     * Redirects the user to another resource.
     *
     * @param resource the requestor should be redirected to.
     * @throws IOException on failure to send reqdirect.
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
            req.setAttribute(CATEGORIES, ProductManager.listCategories());
            req.getRequestDispatcher(resource).forward(req, resp);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Logs the throwable exception with an error reference that is displayed to the end user.
     *
     * @param e    the throwable to log.
     * @param req  contains language for localization.
     * @param resp the response to write the reference to.
     */
    public static void throwable(Throwable e, HttpServletRequest req, HttpServletResponse resp) {
        byte[] bytes = new byte[3];
        random.nextBytes(bytes);

        String errorId = new BigInteger(bytes).abs().toString(16);
        errorId = errorId.substring(0, 3) + "-" + errorId.substring(3);

        logger.error(String.format("error reference %s", errorId), e);

        String message = Language.getString(SERVER_EXCEPTION, req.getSession());
        req.setAttribute(MESSAGE, message + " " + errorId);
        to(ERROR_JSP, req, resp);
    }

    /**
     * Redirects the user to an error page with a visible error message.
     *
     * @param key  the key to the message that should be displayed.
     *             if the key is not found, the key is displayed as-is.
     * @param req  a request containing a session with a locale.
     * @param resp the response used to write the error page to.
     */
    public static void error(String key, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(MESSAGE, Language.getString(key, req.getSession()));
        to(ERROR_JSP, req, resp);
    }

    /**
     * Redirects the user to a success page with a visible feedback message.
     *
     * @param key  the key to the message that should be displayed.
     * @param req  a request containing a session with a locale.
     * @param resp the response used to write the page to.
     */
    public static void success(String key, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(MESSAGE, Language.getString(key, req.getSession()));
        to(SUCCESS_JSP, req, resp);
    }
}
