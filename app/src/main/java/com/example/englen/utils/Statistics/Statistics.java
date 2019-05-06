package com.example.englen.utils.Statistics;

import android.content.Context;

import com.example.englen.Data.Save;
import com.example.englen.utils.ExperienceControl;

public class Statistics {

    private static final String tagHours = "tagHours";
    private static int minutes;//Потраченных часов
    private static final String taglearnWord= "taglearnWord";
    private static int learnWord;//Выученных слов
    private static final String tagRemember = "tagRemember";
    private static int rememberWord;//Повторено слов
    private static final String tagTheme = "tagTheme";
    private static int learnTheme;//Изучено тем
    private static final String tagFalseRemember = "tagFalseRemember";
    private static int falseRememberWord;//Не Правильных ответов при повторение слов
    private static final String tagallLearnTheme = "tagallLearnTheme";
    private static int allLearnTheme;//Все попытки прохождения тестов


    private static int trueRememberWordPercent = 100;
    private static int trueLearnThemePercent = 100;


    public static void Save()
    {
        Save.Save(tagHours, Integer.toString(minutes));
        Save.Save(taglearnWord, Integer.toString(learnWord));
        Save.Save(tagRemember, Integer.toString(rememberWord));
        Save.Save(tagTheme, Integer.toString(learnTheme));
        Save.Save(tagFalseRemember, Integer.toString(falseRememberWord));
        Save.Save(tagallLearnTheme , Integer.toString(allLearnTheme));
    }

    public static void Load(Context context)
    {
        minutes = Integer.parseInt(Save.load(tagHours, "0",context));
        learnWord =Integer.parseInt(Save.load(taglearnWord, "0",context));
        rememberWord = Integer.parseInt(Save.load(tagRemember, "0",context));
        learnTheme = Integer.parseInt(Save.load(tagTheme, "0",context));
        falseRememberWord = Integer.parseInt(Save.load(tagFalseRemember, "0",context));
        allLearnTheme = Integer.parseInt(Save.load(tagallLearnTheme, "0",context));
    }

    public static int[] toArray() {
        if(rememberWord!=0 && falseRememberWord !=0)
        {
            int a = rememberWord - falseRememberWord;
            float b = (float) a / (float)rememberWord;
            trueRememberWordPercent = (int)( b * 100);
        }
        if(learnTheme!=0 && allLearnTheme !=0)
        {
            int a = learnTheme;
            float b = (float) a / (float)allLearnTheme;
            trueLearnThemePercent = (int)( b * 100);
        }
        int[] array = new int[]{
                minutes,
                learnWord,
                rememberWord,
                learnTheme,
                trueRememberWordPercent,
                trueLearnThemePercent,
                ExperienceControl.getLevel()
        };
        return array;
    }

    public static void addAllLearnTheme(int value){allLearnTheme++;}

    public static void addfalseRememberWord(int value) {
        falseRememberWord += value;
    }

    public static void addMinutes(int value) {
        minutes += value;
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
