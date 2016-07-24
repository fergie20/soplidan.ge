package com.example.irakli.soplidange;

import com.example.irakli.soplidange.models.ProductModel;

import java.util.HashMap;

/**
 * Created by GeoLab on 7/11/16.
 */
public class SingletonTest {
    private static SingletonTest ourInstance;
    private static HashMap<Integer,ProductModel> cartMap;

    public static SingletonTest getInstance() {
        if(ourInstance == null){
            ourInstance = new SingletonTest();
            cartMap = new HashMap<>();
        }
        return ourInstance;
    }

    private SingletonTest() {
    }

    public void addProduct(Integer productId, ProductModel model){
        cartMap.put(productId, model);
    }

    public void removeProduct(String product){
        cartMap.remove(product);
    }

    public int getNumberOfItems(){
        return cartMap.size();
    }
    public ProductModel getProduct(int id){
        return cartMap.get(id);
    }

    public HashMap<Integer, ProductModel> getCartMap(){
        return cartMap;
    }

}
