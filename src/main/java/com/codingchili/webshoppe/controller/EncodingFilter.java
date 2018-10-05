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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
        try {
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
