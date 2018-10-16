package com.codingchili.webshoppe.controller;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Robin Duda
 * <p>
 * Retrieve language from message bundle.
 */
public class Language {
    private static final String LANGUAGE = "language";
    private static Map<String, ResourceBundle> bundles = new HashMap<>();

    public static final String PRODUCT_UPDATE_FAILED = "product.update_failed";
    public static final String PRODUCT_NOT_FOUND = "product.not_found";
    public static final String NOT_AUTHORIZED = "login.not_authorized";

    public static final String PRODUCT_CREATE_SUCCESS = "product.create_success";
    public static final String PRODUCT_CREATE_FAIL = "product.create_failed";

    public static final String CATEGORY_CREATE_FAIL = "category.create_fail";
    public static final String CATEGORY_CREATE_SUCCESS = "category.create_success";

    public static final String ORDER_LOAD_FAILED = "orders.load_failed";
    public static final String PAYMENT_GATEWAY_OK = "payment.gateway_ok";

    public static final String SERVER_CSRF_INVALID = "server.csrf_invalid";
    public static final String SERVER_CSRF_MISSING = "server.csrf_missing";

    public static final String ORDERS_NONE_TO_PACK = "orders.none_to_pack";
    public static final String ORDER_NOT_FOUND = "order.not_found";

    /**
     * Retrieves the localized string for the given key.
     *
     * @param key     a key that is specified in a message bundle.
     * @param session a session that contains the language attribute.
     * @return the value of the resolved localized key.
     */
    public static String getString(String key, HttpSession session) {
        String language = (String) session.getAttribute(LANGUAGE);

        if (get(language).containsKey(key)) {
            return get(language).getString(key);
        } else {
            return key;
        }
    }

    private static ResourceBundle get(String locale) {
        return bundles.computeIfAbsent(locale, (key) ->
                ResourceBundle.getBundle(String.format("localization.%s_bundle", key)));
    }

}
