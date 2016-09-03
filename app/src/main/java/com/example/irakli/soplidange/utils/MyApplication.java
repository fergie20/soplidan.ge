package com.example.irakli.soplidange.utils;

import android.app.Application;

/**
 * Created by GeoLab on 9/2/16.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

//        new SharedPreferences().retryShared();


        initSingletons();
    }

    protected void initSingletons()
    {
        SingletonTest.getInstance();
    }

    public void customAppMethod()
    {
        // Custom application method
    }

}