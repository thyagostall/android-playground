package com.thyago.singleton;

import android.util.Log;

/**
 * Created by thyago on 1/18/16.
 */
public class MySingletonClass {
    private static final String LOG_TAG = MySingletonClass.class.getSimpleName();
    private static MySingletonClass instance = null;

    private MySingletonClass() {
        Log.d(LOG_TAG, "Object created!");
    }

    public static MySingletonClass getInstance() {
        if (instance == null) {
            instance = new MySingletonClass();
        }
        return instance;
    }

    public void myMethod() {
        Log.d(LOG_TAG, "myMethod called!");
    }
}
