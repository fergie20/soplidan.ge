package com.example.irakli.soplidange.models;

/**
 * Created by GeoLab on 9/29/16.
 */

public class ChangeStatusBar {
    private int image;
    private int color;
    private int statusbarcolor;
    private String title;


    public ChangeStatusBar(int image, int color, int statusbarcolor, String title) {
        this.image = image;
        this.color = color;
        this.statusbarcolor = statusbarcolor;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStatusbarcolor() {
        return statusbarcolor;
    }

    public void setStatusbarcolor(int statusbarcolor) {
        this.statusbarcolor = statusbarcolor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
