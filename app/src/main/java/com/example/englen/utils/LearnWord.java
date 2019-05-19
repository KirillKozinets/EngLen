
// Количество выученных слов

package com.example.englen.utils;


import android.content.Context;

import com.example.englen.Data.Save;

public class LearnWord {
    static private int currentID = 1;

    private static final String tagID = "tagCurrentID";

    static public int getCurrentID()
    {
        return currentID;
    }

    static public void addNewWord()
    {
        currentID++;
    }

    public static void Save() {
        Save.Save(tagID, Integer.toString(currentID));
    }

    public static void Load(Context context) {
        currentID = Integer.parseInt(Save.load(tagID, "0",context));
    }
}
