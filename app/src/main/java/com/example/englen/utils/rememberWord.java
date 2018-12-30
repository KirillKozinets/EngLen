package com.example.englen.utils;

import android.content.Context;

import com.example.englen.Data.Save;

public class rememberWord {
    static private int rememberWord = 1;

    private static final String tagID = "tagRememberWord";

    static public int getRememberWord()
    {
        return rememberWord;
    }

    static public void addNewRememberWord()
    {
        rememberWord++;
    }

    static public void resetRepeatedWords()
    {
        rememberWord = 1;
    }


    public static void Save() {
        Save.Save(tagID, Integer.toString(rememberWord));
    }

    public static void Load(Context context) {
        rememberWord = Integer.parseInt(Save.load(tagID, "1",context));
    }
}
