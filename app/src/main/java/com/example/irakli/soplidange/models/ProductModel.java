package com.example.irakli.soplidange.models;

import java.io.Serializable;

/**
 * Created by GeoLab on 7/10/16.
 */
public class ProductModel implements Serializable {
    private String categories,name,description,img, product_code,status;
    private int id, recource, quontity,list_discount_prc;
    private double list_price,base_price,list_discount;


    public ProductModel(String categories, String name, String description, String img, int id, int recource, double price, String status, String product_code, Double base_price, Double list_discount, int list_discount_prc) {
        this.categories = categories;
        this.name = name;
        this.description = description;
        this.img = img;
        this.id = id;
        this.recource = recource;
        this.list_price = price;
        this.status=status;
        this.product_code=product_code;
        this.base_price=base_price;
        this.list_discount=list_discount;
        this.list_discount_prc=list_discount_prc;
    }

    public String getProduct_code() {
        return product_code;
    }

    public double getBase_price() {
        return base_price;
    }

    public void setBase_price(double base_price) {
        this.base_price = base_price;
    }

    public double getList_discount() {
        return list_discount;
    }

    public void setList_discount(double list_discount) {
        this.list_discount = list_discount;
    }

    public int getList_discount_prc() {
        return list_discount_prc;
    }

    public void setList_discount_prc(int list_discount_prc) {
        this.list_discount_prc = list_discount_prc;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public double getSumPrice(){
        return  list_price *2;
    }

    public int getQuontity() {
        return quontity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getList_price() {
        return list_price;
    }

    public void setList_price(double list_price) {
        this.list_price = list_price;
    }


}
