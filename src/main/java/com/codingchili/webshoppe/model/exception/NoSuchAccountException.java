package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-09-28.
 *
 * Throw when the presence of a given Account is required
 * but is not met.
 */
public class NoSuchAccountException extends AccountStoreException {
    public NoSuchAccountException(String s) {
        super(s);
    }

    public NoSuchAccountException(Throwable cause) {
        super(cause);
    }
}
