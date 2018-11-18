package com.example.englen.Data;

import android.content.SharedPreferences;

public class Save {


    static SharedPreferences  myPreferences;

    public static void  Save(String Key , String value) {
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString(Key,value);
        editor.apply();
    }

    public static String load(String Key) {
        return myPreferences.getString(Key, "0");
    }
}
