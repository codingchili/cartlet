package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-10-03.
 *
 * A product image.
 */

public class Image implements Bean {
    private String data;
    private int imageId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
