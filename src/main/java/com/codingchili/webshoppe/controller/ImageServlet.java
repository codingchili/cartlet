package com.codingchili.webshoppe.controller;

import com.codingchili.webshoppe.model.ProductManager;
import com.codingchili.webshoppe.model.exception.ProductStoreException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Robin on 2015-10-03.
 *
 * Handles the retrieval of product images from the database.
 */

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int imageId = Integer.parseInt(req.getParameter("id"));
            com.codingchili.webshoppe.model.Image image = ProductManager.getProductImage(imageId);
            String data = image.getData();

            resp.setContentType(getContentType(data));
            data = stripContentType(data);

            OutputStream out = resp.getOutputStream();
            byte[] bytes = Base64.getDecoder().decode(data);
            out.write(bytes);
            out.close();
        } catch (NumberFormatException | ProductStoreException e) {
            Forwarding.to("img/placeholder.svg", req, resp);
        }
    }

    private String getContentType(String data) {
        String contentType = "";
        Pattern pattern = Pattern.compile("image[^;]*");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            contentType = matcher.group();
        }

        return contentType;
    }

    private String stripContentType(String data) {
        return data.replaceFirst("(.)*,", "");
    }
}
