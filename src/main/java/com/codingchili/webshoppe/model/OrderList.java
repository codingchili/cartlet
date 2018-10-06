package com.codingchili.webshoppe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2015-10-01.
 *
 * Used to list the items in an order as JSTL cannot handle generic lists.
 */
public class OrderList {
    private List<Order> items = new ArrayList<>();

    public List<Order> getItems() {
        return items;
    }

    protected void setItems(List<Order> items) {
        this.items = items;
    }

    public int getSize() {
        return items.size();
    }
}
