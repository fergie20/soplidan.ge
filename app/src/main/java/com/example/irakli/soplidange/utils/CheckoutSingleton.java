package com.example.irakli.soplidange.utils;

import com.example.irakli.soplidange.models.ProductModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeoLab on 9/16/16.
 */
public class CheckoutSingleton {
    private static CheckoutSingleton ourInstance;
    private static HashMap<String, String> cartMap;

    public static CheckoutSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new CheckoutSingleton();
            cartMap = new HashMap<>();
        }
        return ourInstance;
    }

    private CheckoutSingleton() {

    }

    public void addNewValue(String key, String value) {
        cartMap.put(key, value);
    }

    public String getValue(String key) {

        return cartMap.get(key);
    }

    public HashMap<String, String> getCartmap() {

        return cartMap;
    }


    public void setCart(HashMap<String, String> cart){
        cartMap = deepCopy(cart);
    }

    private HashMap<String, String> deepCopy(HashMap<String, String> original){
        HashMap<String, String> copy = new HashMap<>();

        if(original !=null){
            for(Map.Entry<String, String> entry : original.entrySet()){
                copy.put(entry.getKey(), entry.getValue());
            }
        }

        return copy;
    }



}
