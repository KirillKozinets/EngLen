package com.example.englen.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class Save {


        static SharedPreferences  myPreferences;

    static private final String FileName = "File";

    // Сохраняет информацию
    public static void  Save(String Key , String value) {
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString(Key,value);
        editor.apply();
    }

    public static String load(String Key,String defaultValue,Context context) {
        myPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return myPreferences.getString(Key, defaultValue);
    }
}
