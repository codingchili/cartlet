package com.codingchili.webshoppe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2015-10-01.
 *
 * An order with items and an account as the owner.
 */
public class Order implements Bean {
    private int status;
    private int orderId;
    private int orderTotal;
    private int itemCount;
    private int ownerId;
    private String changed;
    private String created;
    private Account account;
    private List<Product> products = new ArrayList<>();

    public Order() {
    }

    public int getItemCount() {
        if (products.isEmpty()) {
            return itemCount;
        } else {
            return products.size();
        }
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getChanged() {
        return changed;
    }

    protected void setAccount(Account account) {
        this.account = account;
        this.ownerId = account.getId();
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

    public int getStatus() {
        return status;
    }

    protected void setStatus(int status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    protected void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getOrderTotal() {
        if (products.isEmpty()) {
            return orderTotal;
        } else {
            int cost = 0;

            for (Product product : products) {
                cost += product.getCost() * product.getCount();
            }
            return cost;
        }
    }

    public void setOwner(Integer integer) {
        ownerId = integer;
    }

    public int getOwnerId() {
        return ownerId;
    }

    protected class Status {
        public final static int WAITING = 1;
        public final static int SHIPPED = 2;
        public final static int PROCESSING = 3;
        public final static int APPROVED = 4;
    }

    ;
}
