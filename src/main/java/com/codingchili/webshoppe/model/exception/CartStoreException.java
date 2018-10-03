package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-10-01.
 *
 * Throw when the cart store has failed.
 */

public class CartStoreException extends StoreException {
    public CartStoreException(Throwable cause) {
        super(cause);
    }

    public CartStoreException(String s) {
        super(s);
    }
}
