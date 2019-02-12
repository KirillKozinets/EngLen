package com.example.englen.utils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LevelEndEvent;

public class Fabric {

    public static void enterLevel(String levelName , int ex , boolean result)
    {
        if(levelName == null)levelName = "null";

        Answers.getInstance().logLevelEnd(new LevelEndEvent()
                .putLevelName(levelName)
                .putScore(ex)
                .putSuccess(result));
    }
}
