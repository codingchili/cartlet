package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-10-01.
 *
 * A single point to define the stores used, a store
 * may be dependent on another store.
 */
 class Store {
     private static final AccountDB accountDB = new AccountDB();
     private static final OrderDB orderDB = new OrderDB();
     private static final ProductDB productDB = new ProductDB();
     private static final CartDB cartDB = new CartDB();

    public static AccountStore getAccountStore() {
        return accountDB;
    }

    public static OrderStore getOrderStore() {
        return orderDB;
    }

    public static ProductStore getProductStore() {
        return productDB;
    }

    public static CartStore getCartStore() {
        return cartDB;
    }
}
