package com.example.englen.utils;

import android.util.Log;

import com.example.englen.Interface.stateApp;

import java.util.logging.Level;

public class ExperienceControl implements stateApp {
    private static int experience = 0;
    private static int level = 1;

    public static int getExperience() {
        return experience;
    }

    public static int getLevel() {
        return level;
    }

    public static int addExperience(int experience) {
        ExperienceControl.experience += experience;

        while (getexperienceForNewLevel() <= ExperienceControl.experience) {
            ExperienceControl.experience = ExperienceControl.experience - getexperienceForNewLevel();
            level++;
        }
        Log.e("errore", Integer.toString(ExperienceControl.experience));
        Log.e("errore", Integer.toString(getexperienceForNewLevel()));
        return level;
    }


    public static int getexperienceForNewLevel() {
        int result = (int) Math.pow(level, 2) * 10 + 20;
        return result;
    }

    @Override
    public void Create() {

    }

    @Override
    public void Close() {

    }
}
