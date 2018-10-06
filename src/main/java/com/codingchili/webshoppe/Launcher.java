package com.codingchili.webshoppe;

import com.codingchili.webshoppe.controller.*;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.*;
import io.undertow.servlet.util.DefaultClassIntrospector;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Robin Duda
 *
 * Bootstrap class for the web app.
 */
public class Launcher {


    public static void main(String[] args) throws ServletException {
        final PathHandler servletPath = new PathHandler();
        final ServletContainer container = ServletContainer.Factory.newInstance();

        servletPath.addPrefixPath("/web/",
                Handlers.resource(new ClassPathResourceManager(Launcher.class.getClassLoader())));

        DeploymentInfo builder = new DeploymentInfo()
                .addFilter(Servlets.filter(EncodingFilter.class))
                .addFilterUrlMapping(EncodingFilter.class.getSimpleName(), "/*", DispatcherType.REQUEST)
                .setClassLoader(Launcher.class.getClassLoader())
                .setContextPath("/")
                .setClassIntrospecter(DefaultClassIntrospector.INSTANCE)
                .setDeploymentName("webshop.war")
                .setResourceManager(new ClassPathResourceManager(Launcher.class.getClassLoader()))
                .addServlets(allServlets())
                .addServlet(JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp"));

        JspServletBuilder.setupDeployment(
                builder,
                new HashMap<>(),
                TldLocator.createTldInfos(),
                new HackInstanceManager()
        );

        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();
        servletPath.addPrefixPath(builder.getContextPath(), manager.start());

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(servletPath)
                .build();
        server.start();
    }

    private static Collection<ServletInfo> allServlets() {
        // i think this api is stupid because there is no way to find @WebServlets and just start them...
        return Stream.of(ProductServlet.class,
                ViewServlet.class,
                AccountServlet.class,
                BuyServlet.class,
                CartServlet.class,
                EditServlet.class,
                ImageServlet.class,
                LoginServlet.class,
                LogoutServlet.class,
                ManagersServlet.class,
                OrderServlet.class,
                OrdersServlet.class,
                ProcessServlet.class,
                ProductCategoryServlet.class,
                RegisterServlet.class,
                StorageServlet.class,
                SwishServlet.class
        )
        .map(servlet -> {
            ServletInfo info = Servlets.servlet(servlet);
            Arrays.stream(servlet.getAnnotation(WebServlet.class).value()).forEach(info::addMapping);
            return info;
        }).collect(Collectors.toList());
    }

}
