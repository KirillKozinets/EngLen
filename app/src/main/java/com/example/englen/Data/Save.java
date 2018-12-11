package com.example.englen.Data;

import android.content.SharedPreferences;

public class Save {


    static SharedPreferences  myPreferences;

    // Сохраняет информацию
    public static void  Save(String Key , String value) {
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString(Key,value);
        editor.apply();
    }

    // Читает информацию
    public static String load(String Key) {
        return myPreferences.getString(Key, "0");
    }
    public static String load(String Key,String defaultValue) {
        return myPreferences.getString(Key, defaultValue);
    }
}
