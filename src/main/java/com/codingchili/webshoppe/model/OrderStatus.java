package com.codingchili.webshoppe.model;

/**
 * @author Robin Duda
 *
 * Statuses and transitions for orders.
 */
public enum OrderStatus {
    CANCELLED(Constants.DANGER),
    RETURNED(Constants.WARNING),
    SHIPPED(Constants.SUCCESS, RETURNED),
    PACKING(Constants.INFO, CANCELLED, SHIPPED),
    PAYED(Constants.INFO, CANCELLED, PACKING),
    PAYMENT(Constants.WARNING, CANCELLED, PAYED);

    private OrderStatus[] next;
    private String color;

    OrderStatus(String color, OrderStatus... next) {
        this.next = next;
        this.color = color;
    }

    public String color() {
        return color;
    }

    public OrderStatus[] next() {
        return next;
    }

    private static class Constants {
        static final String DANGER = "danger";
        static final String WARNING = "warning";
        static final String SUCCESS = "success";
        static final String INFO = "info";
    }
}
