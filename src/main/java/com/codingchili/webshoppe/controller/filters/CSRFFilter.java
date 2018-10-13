package com.codingchili.webshoppe.controller.filters;

import com.codingchili.webshoppe.controller.Forwarding;
import com.codingchili.webshoppe.model.HashHelper;
import io.vertx.core.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.codingchili.webshoppe.controller.Language.SERVER_CSRF_INVALID;
import static com.codingchili.webshoppe.controller.Language.SERVER_CSRF_MISSING;

/**
 * @author Robin Duda
 *
 * Filters POST requests without valid csrf token.
 */
@WebFilter("/*")
public class CSRFFilter implements Filter {
    private static final String CSRF = "csrf";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) req;

        if (http.getMethod().equals(HttpMethod.POST.name())) {
            String sessionToken = (String) http.getSession().getAttribute(CSRF);
            String formToken = http.getParameter(CSRF);

            if (sessionToken != null && formToken != null) {

                if (HashHelper.equals(sessionToken.getBytes(), formToken.getBytes())) {
                    chain.doFilter(req, resp);
                } else {
                    Forwarding.error(SERVER_CSRF_INVALID, http, (HttpServletResponse) resp);
                }
            } else {
                Forwarding.error(SERVER_CSRF_MISSING, http, (HttpServletResponse) resp);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }
}
