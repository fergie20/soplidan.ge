package com.example.irakli.soplidange.models;

/**
 * Created by GeoLab on 8/16/16.
 */
public class CategoryModel {
    private int category_id;
    private String category;
    private String status;
    private int position;
    private int product_count;
    private int category_image;

    public CategoryModel(int category_id, String category, String status, int position, int product_count, int category_image) {
        this.category_id = category_id;
        this.category = category;
        this.status = status;
        this.position = position;
        this.product_count = product_count;
        this.category_image = category_image;
    }

    public int getCategory_image() {
        return category_image;
    }

    public void setCategory_image(int category_image) {
        this.category_image = category_image;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }
}
