package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-09-29.
 *
 * Throw when the account store has failed in some irrelevant way.
 */

public class AccountStoreException extends StoreException {

    public AccountStoreException(String s) {
        super(s);
    }

    public AccountStoreException(Throwable cause) {
        super(cause);
    }
}
