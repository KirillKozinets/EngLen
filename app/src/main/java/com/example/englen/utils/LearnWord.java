package com.example.englen.utils;


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

    public static void Load() {
        Save.Save(tagID, Integer.toString(currentID));
    }

    public static void Save() {
        currentID = Integer.parseInt(Save.load(tagID, "1"));
    }
}
