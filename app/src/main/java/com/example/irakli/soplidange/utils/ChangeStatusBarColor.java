package com.example.irakli.soplidange.utils;

import com.example.irakli.soplidange.models.ChangeStatusBar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeoLab on 9/29/16.
 */

public class ChangeStatusBarColor {
    private static ChangeStatusBarColor ourInstance;
    private static HashMap<Integer, ChangeStatusBar> cartMap;

    public static ChangeStatusBarColor getInstance() {
        if (ourInstance == null) {
            ourInstance = new ChangeStatusBarColor();
            cartMap = new HashMap<>();
        }
        return ourInstance;
    }

    private ChangeStatusBarColor() {
    }

    public void addProduct(Integer categoriId, ChangeStatusBar model) {
        cartMap.put(categoriId, model);
    }

    public void removeProduct(Integer categoriId) {
        cartMap.remove(categoriId);
    }

    public int getNumberOfItems() {
        return cartMap.size();
    }

    public ChangeStatusBar getProduct(int id) {
        return cartMap.get(id);
    }

    public HashMap<Integer, ChangeStatusBar> getCartMap() {
        return cartMap;
    }

    public void setCart(HashMap<Integer, ChangeStatusBar> cart){
        cartMap = deepCopy(cart);
    }

    private HashMap<Integer, ChangeStatusBar> deepCopy(HashMap<Integer, ChangeStatusBar> original){
        HashMap<Integer, ChangeStatusBar> copy = new HashMap<>();

        if(original !=null){
            for(Map.Entry<Integer, ChangeStatusBar> entry : original.entrySet()){
                copy.put(entry.getKey(), entry.getValue());
            }
        }



        return copy;
    }

}

