package com.codingchili.webshoppe.controller.servlets;

import com.codingchili.webshoppe.Properties;
import com.codingchili.webshoppe.controller.*;
import com.codingchili.webshoppe.model.*;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.codingchili.webshoppe.controller.Language.ORDER_NOT_FOUND;
import static com.codingchili.webshoppe.controller.Language.PAYMENT_GATEWAY_OK;

/**
 * @author Robin Duda
 * <p>
 * Swish payment integration.
 */
@WebServlet("/swish")
public class SwishServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SwishServlet.class);
    private static final String QRG_SWISH_API_V_1_PREFILLED = "/qrg-swish/api/v1/prefilled";
    private static final String SWISH_ERROR = "swish responded with error code ";
    private static final String MPC_GETSWISH_NET = "mpc.getswish.net";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String IMAGE_SVG = "image/svg+xml";
    private static final String ORDER_ID = "orderId";
    private static final int PORT = 443;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int orderId = Integer.parseInt(req.getParameter(ORDER_ID));

        Optional<Order> order = OrderManager.getOrderById(Session.getAccount(req), orderId);

        if (order.isPresent()) {
            CompletableFuture<Void> qrImage = new CompletableFuture<>();

            Async.vertx().createHttpClient(new HttpClientOptions().setSsl(true))
                    .post(PORT, MPC_GETSWISH_NET, QRG_SWISH_API_V_1_PREFILLED)
                    .handler(response -> {

                        if (response.statusCode() >= 300) {
                            qrImage.complete(null);
                            Forwarding.throwable(
                                    new RuntimeException(SWISH_ERROR + response.statusCode()),
                                    req,
                                    resp);
                        }
                        response.bodyHandler(buffer -> {
                            try (OutputStream out = resp.getOutputStream()) {
                                resp.setContentType(IMAGE_SVG);
                                out.write(buffer.getBytes());
                                qrImage.complete(null);
                            } catch (IOException e) {
                                Forwarding.throwable(e, req, resp);
                            }
                        });
                    })
                    .exceptionHandler(e -> {
                        qrImage.complete(null);
                        Forwarding.throwable(e, req, resp);
                    })
                    .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .end(createQRRequest(order.get()));

            // can't exit the handler before the response is written - the stream will be closed.
            qrImage.join();
        } else {
            Forwarding.error(ORDER_NOT_FOUND, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Forwarding.success(PAYMENT_GATEWAY_OK, req, resp);
    }

    private String createQRRequest(Order order) {
        String json = new JsonObject()
                .put("format", "svg")
                .put("message", new JsonObject()
                        .put("value", "order " + order.getOrderId())
                        .put("editable", false))
                .put("payee",
                        new JsonObject()
                                .put("value", Properties.get().getSwishReceiver())
                                .put("editable", false))
                .put("amount", new JsonObject()
                        .put("value", order.getOrderTotal())
                        .put("editable", false))
                .put("size", 600)          // n/a for svg
                .put("border", 1)
                .put("transparent", false) // n/a for svg
                .encode();
        logger.info("requesting svg from swish\n\t" + json);
        return json;
    }
}
