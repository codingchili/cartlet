package com.codingchili.webshoppe.controller.filters;

import com.codingchili.webshoppe.controller.Forwarding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Robin Duda
 *
 * Request filter that sets the proper encoding for all requests.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());
            chain.doFilter(req, resp);
        } catch (Throwable e) {
            Forwarding.throwable(e, (HttpServletRequest) req, (HttpServletResponse) resp);
        }
    }
}
