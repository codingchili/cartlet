package com.codingchili.webshoppe.controller.filters;

import com.codingchili.webshoppe.controller.Forwarding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.nio.charset.StandardCharsets;

/**
 * @author Robin Duda
 *
 * Request filter that sets the proper encoding for all requests.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException {
        try {
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());
            chain.doFilter(req, resp);
        } catch (Exception e) {
            logger.error("Failed to handle request.", e);
            req.setAttribute("message", e.getMessage());
            Forwarding.to("error.jsp", req, resp);
        }
    }
}
