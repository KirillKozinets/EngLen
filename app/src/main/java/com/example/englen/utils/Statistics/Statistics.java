package com.example.englen.utils.Statistics;

import android.content.Context;

import com.example.englen.Data.Save;

import java.util.ArrayList;

public class Statistics {
    private static final String tagHours = "tagHours";
    private static int hours;//Потраченных часов
    private static final String taglearnWord= "taglearnWord";
    private static int learnWord;//Выученных слов
    private static final String tagRemember = "tagRemember";
    private static int rememberWord;//Повторено слов
    private static final String tagTheme = "tagTheme";
    private static int learnTheme;//Изучено тем

    public static void Save()
    {
        Save.Save(tagHours, Integer.toString(hours));
        Save.Save(taglearnWord, Integer.toString(learnWord));
        Save.Save(tagRemember, Integer.toString(rememberWord));
        Save.Save(tagTheme, Integer.toString(learnTheme));
    }

    public static void Load(Context context)
    {
        hours = Integer.parseInt(Save.load(tagHours, "0",context));
        learnWord =Integer.parseInt(Save.load(taglearnWord, "0",context));
        rememberWord = Integer.parseInt(Save.load(tagRemember, "0",context));
        learnTheme = Integer.parseInt(Save.load(tagTheme, "0",context));
    }

    public static int[] toArray() {
        int[] array = new int[]{
                hours,
                learnWord,
                rememberWord,
                learnTheme
        };
        return array;
    }

    public static void addHours(int value) {
        hours += value;
    }

    public static void addLearnWord(int value) {
        learnWord += value;
    }

    public static void addRepeatWord(int value) {
        rememberWord += value;
    }

    public static void addLearnTheme(int value) {
        learnTheme += value;
    }


}
