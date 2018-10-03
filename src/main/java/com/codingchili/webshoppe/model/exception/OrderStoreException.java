package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-10-01.
 *
 * Thrown when the order store has failed to complete an operation.
 */

public class OrderStoreException extends StoreException {

    public OrderStoreException(String s) {
        super(s);
    }

    public OrderStoreException(Throwable cause) {
        super(cause);
    }
}
