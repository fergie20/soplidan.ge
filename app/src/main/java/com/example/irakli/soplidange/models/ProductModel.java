package com.example.irakli.soplidange.models;

import java.io.Serializable;

/**
 * Created by GeoLab on 7/10/16.
 */
public class ProductModel implements Serializable {
    private String categories,name,description,img;
    private int id;
    private int recource;
    private double price;
    private int quontity;

    public ProductModel(String categories, String name, String description, String img, int id, int recource, double price) {
        this.categories = categories;
        this.name = name;
        this.description = description;
        this.img = img;
        this.id = id;
        this.recource = recource;
        this.price = price;
    }

    public int getQuontity() {
        return quontity;
    }

    public void setQuontity(int quontity) {
        this.quontity = quontity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecource() {
        return recource;
    }

    public void setRecource(int recource) {
        this.recource = recource;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
