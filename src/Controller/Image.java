package Controller;

import Model.Exception.ProductStoreException;
import Model.ProductManager;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Robin on 2015-10-03.
 *
 * Handles the retrieval of product images from the database.
 */

@WebServlet("/image")
public class Image extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int imageId = Integer.parseInt(req.getParameter("id"));
            Model.Image image = ProductManager.getProductImage(imageId);
            String data = image.getData();

            resp.setContentType(getContentType(data));
            data = stripContentType(data);

            OutputStream out = resp.getOutputStream();
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(data);
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
