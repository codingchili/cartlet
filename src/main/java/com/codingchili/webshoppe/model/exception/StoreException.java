package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-10-01.
 *
 * Thrown when an exception in any of the stores occurs.
 */

public class StoreException extends RuntimeException {

    public StoreException(Throwable cause) {
        super(cause);
    }

    public StoreException(String s) {
        super(s);
    }
}
