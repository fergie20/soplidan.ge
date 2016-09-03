package com.example.irakli.soplidange.utils;

import android.app.Activity;

import com.example.irakli.soplidange.models.ProductModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by GeoLab on 9/3/16.
 */
public class SharedPreferences extends Activity {

    public void saveShared (){

        android.content.SharedPreferences mPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);



        HashMap<Integer,ProductModel> cartMap;

        cartMap = SingletonTest.getInstance().getCartMap();

        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartMap);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }
    public void retryShared(){
        android.content.SharedPreferences mPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");

        Type typeOfHashMap = new TypeToken<HashMap<Integer, ProductModel>>() { }.getType();
        HashMap<Integer, ProductModel> newMap = gson.fromJson(json, typeOfHashMap);
        SingletonTest.getInstance().setCart(newMap);
    }

}
