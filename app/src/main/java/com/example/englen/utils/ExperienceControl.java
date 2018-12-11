package com.example.englen.utils;

import android.util.Log;

import com.example.englen.Data.Save;

import java.util.logging.Level;

public class ExperienceControl {
    private static int experience = 0;
    private static int level = 1;

    private static final String tagLevel = "tagLevel";
    private static final String tagExperience = "tagExperience";

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
        return level;
    }


    public static int getexperienceForNewLevel() {
        int result = (int) Math.pow(level, 2) * 10 + 20;
        return result;
    }

    public static void Load() {
        Save.Save(tagLevel, Integer.toString(level));
        Save.Save(tagExperience, Integer.toString(experience));
    }

    public static void Save() {
        level = Integer.parseInt(Save.load(tagLevel, "1"));
        experience = Integer.parseInt(Save.load(tagExperience, "0"));
    }

}
