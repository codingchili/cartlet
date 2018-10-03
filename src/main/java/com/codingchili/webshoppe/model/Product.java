package com.codingchili.webshoppe.model;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-30.
 *
 * The product model.
 */

public class Product implements Bean {
    private String name;
    private String description;
    private int id;
    private int count;
    private int cost;
    private ArrayList<Integer> imageId = new ArrayList<>();

    public int getFrontImage() {
        if (imageId.size() > 0)
            return imageId.get(0);
        else
            return -1;
    }


    public int getCost() {
        return cost;
    }

    protected Product setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public int getCount() {
        return count;
    }

    protected Product setCount(int count) {
        this.count = count;
        return this;
    }

    public String getName() {
        return name;
    }

    protected Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    protected Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getImageIds() {
        return imageId;
    }

    protected void setImageIds(ArrayList<Integer> imageId) {
        this.imageId = imageId;
    }
}
