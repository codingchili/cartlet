package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-10-01.
 *
 */
public class Category implements Bean {
    private int categoryId;
    private String name;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
