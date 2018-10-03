package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-10-03.
 *
 * Thrown when a requested product is not available.
 */
public class NoSuchProductException extends ProductStoreException {
    public NoSuchProductException(Throwable e) {
        super(e);
    }

    public NoSuchProductException(String s) {
        super(s);
    }
}
