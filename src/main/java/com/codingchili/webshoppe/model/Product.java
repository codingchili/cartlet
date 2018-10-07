package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-09-30.
 *
 * The product model.
 */

public class Product implements Bean {
    //private List<Integer> imageId = new ArrayList<>();
    private String name;
    private String description;
    private int imageId = -1;
    private int id;
    private int count;
    private int cost;

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

    public Product setCount(int count) {
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    /*public List<Integer> getImageIds() {
        return imageId;
    }

    protected void setImageIds(List<Integer> imageId) {
        this.imageId = imageId;
    }*/
}
