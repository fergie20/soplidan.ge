package com.example.irakli.soplidange.utils;

import com.example.irakli.soplidange.models.ProductModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeoLab on 7/11/16.
 */
public class SingletonTest {
    private static SingletonTest ourInstance;
    private static HashMap<Integer, ProductModel> cartMap;

    public static SingletonTest getInstance() {
        if (ourInstance == null) {
            ourInstance = new SingletonTest();
            cartMap = new HashMap<>();
        }
        return ourInstance;
    }

    private SingletonTest() {
    }

    public void addProduct(Integer productId, ProductModel model) {
        cartMap.put(productId, model);
    }

    public void removeProduct(String product) {
        cartMap.remove(product);
    }

    public int getNumberOfItems() {
        return cartMap.size();
    }

    public ProductModel getProduct(int id) {
        return cartMap.get(id);
    }

    public HashMap<Integer, ProductModel> getCartMap() {
        return cartMap;
    }

    public void setCart(HashMap<Integer, ProductModel> cart){
        cartMap = deepCopy(cart);
    }

    private HashMap<Integer, ProductModel> deepCopy(HashMap<Integer, ProductModel> original){
        HashMap<Integer, ProductModel> copy = new HashMap<>();

        for(Map.Entry<Integer, ProductModel> entry : original.entrySet()){
            copy.put(entry.getKey(), entry.getValue());
        }

        return copy;
    }

}
