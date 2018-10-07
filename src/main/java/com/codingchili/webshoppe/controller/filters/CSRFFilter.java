package com.codingchili.webshoppe.controller.filters;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.model.HashHelper;
import io.vertx.core.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Robin Duda
 *
 * Filters POST requests without valid csrf token.
 */
@WebFilter("/*")
public class CSRFFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CSRFFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) req;

        if (http.getMethod().equals(HttpMethod.POST.name())) {
            String sessionToken = (String) http.getSession().getAttribute("csrf");
            String formToken = http.getParameter("csrf");

            if (sessionToken != null && formToken != null) {

                if (HashHelper.equals(sessionToken.getBytes(), formToken.getBytes())) {
                    chain.doFilter(req, resp);
                } else {
                    onError(req, resp, "Failed to verify CSRF token.");
                }
            } else {
                onError(req, resp, "Request missing 'csrf' token.");
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    private static void onError(ServletRequest req, ServletResponse resp, String message) {
        logger.info(message);
        req.setAttribute("message", message);
        Forwarding.to("error.jsp", req, resp);
    }
}
