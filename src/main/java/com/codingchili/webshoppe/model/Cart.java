package com.codingchili.webshoppe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * A shopping cart holding products.
 */

public class Cart implements Bean {
    private List<Product> products = new ArrayList<>();
    private Account owner;

    public Cart() {
    }

    public Cart(Account account) {
        owner = account;
    }

    public List<Product> getProducts() {
        return products;
    }

    protected void setProducts(List<Product> products) {
        this.products = products;
    }

    public Account getOwner() {
        return owner;
    }

    protected void setOwner(Account owner) {
        this.owner = owner;
    }

    protected void addProduct(Product product) {
        products.add(product);
    }

    public int getTotalCost() {
        int cost = 0;

        for (Product product : products) {
            cost += product.getCost() * product.getCount();
        }

        return cost;
    }

    public int getProductCount() {
        int count = 0;
        for (Product product : products) {
            count += product.getCount();
        }
        return count;
    }

    public int getUniqueProducts() {
        return products.size();
    }
}
