package com.codingchili.webshoppe.model;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-30.
 *
 * A shopping cart holding products.
 */

public class Cart implements Bean {
    private ArrayList<Product> items = new ArrayList<>();
    private Account owner;

    protected Cart() {}

    public ArrayList<Product> getItems() {
        return items;
    }

    protected void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public Account getOwner() {
        return owner;
    }

    protected void setOwner(Account owner) {
        this.owner = owner;
    }

    protected void addProduct(Product product) {
        items.add(product);
    }

    public int getTotalCost() {
        int cost = 0;

        for (Product product: items) {
            cost += product.getCost() * product.getCount();
        }

        return cost;
    }

    public int getUniqueProducts() {
        return items.size();
    }
}
