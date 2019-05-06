package com.example.englen.utils;

import android.content.Context;

import com.example.englen.Data.Save;

public class Tens {
   static private int tens = 1;

    private static final String tagID = "tensD";

    static public int getCurrentID()
    {
        return tens;
    }

    static public void addNewWord()
    {
        tens++;
    }

    public static void Save() {
        Save.Save(tagID, Integer.toString(tens));
    }

    public static void Load(Context context) {
        tens = Integer.parseInt(Save.load(tagID, "1",context));
    }
}
