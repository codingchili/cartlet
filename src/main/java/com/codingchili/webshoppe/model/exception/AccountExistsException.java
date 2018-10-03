package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-09-28.
 *
 * Throw when an operation requires that A specified
 * account did not already exist.
 */
public class AccountExistsException extends AccountStoreException {
    public AccountExistsException(String s) {
        super(s);
    }

    public AccountExistsException(Throwable cause) {
        super(cause);
    }
}
