package com.example.irakli.soplidange;

import android.content.Context;

/**
 * Created by GeoLab on 7/11/16.
 */
public class SingletonDemo {
    private static SingletonDemo instance = null;
    private static Context context;




    /**
     * To initialize the class. It must be called before call the method getInstance()
     * @param ctx The Context used

     */
    public static void initialize(Context ctx) {
        context = ctx;

    }


    /**
     * Check if the class has been initialized
     * @return true  if the class has been initialized
     *         false Otherwise
     */
    public static boolean hasBeenInitialized() {
        return context != null;

    }

    /**
     * The private constructor. Here you can use the context to initialize your variables.
     * @param
     */
    public SingletonDemo() {
        // Use context to initialize the variables.

    }

    /**
     * The main method used to get the instance
     */
    public static synchronized SingletonDemo getInstance() {
        if (context == null) {
            throw new IllegalArgumentException("Impossible to get the instance. This class must be initialized before");
        }

        if (instance == null) {
            instance = new SingletonDemo();
        }

        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not allowed.");
    }
}
