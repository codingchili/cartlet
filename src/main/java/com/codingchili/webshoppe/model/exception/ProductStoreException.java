package com.codingchili.webshoppe.model.exception;

/**
 * Created by Robin on 2015-09-30.
 *
 * Thrown when an exception in the product store occurs.
 */

public class ProductStoreException extends StoreException {

    public ProductStoreException(Throwable e) {
        super(e);
    }

    public ProductStoreException(String s) {
        super(s);
    }
}
