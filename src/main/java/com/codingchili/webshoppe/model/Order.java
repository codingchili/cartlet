package com.codingchili.webshoppe.model;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-10-01.
 *
 * An order with items and an account as the owner.
 */
public class Order implements Bean {
    private String status;
    private int orderId;
    private String changed;
    private String created;
    private Account account;
    private ArrayList<Product> products = new ArrayList<>();

    public Order() {
    }

    public String getChanged() {
        return changed;
    }

    protected void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    protected void setChanged(String changed) {
        this.changed = changed;
    }

    public String getCreated() {
        return created;
    }

    protected void setCreated(String created) {
        this.created = created;
    }

    protected void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    protected void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getTotal() {
        int cost = 0;

        for (Product product : products) {
            cost += product.getCost() * product.getCount();
        }
        return cost;
    }

    protected class Status {
        public final static int WAITING = 1;
        public final static int SHIPPED = 2;
        public final static int PROCESSING = 3;
        public final static int APPROVED = 4;
    }

    ;
}
