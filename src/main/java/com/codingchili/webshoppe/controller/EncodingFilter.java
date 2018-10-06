package com.codingchili.webshoppe.controller;

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

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException {
        try {
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());
            chain.doFilter(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            Forwarding.to("error.jsp", req, resp);
        }
    }
}
