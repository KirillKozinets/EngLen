
// Класс для упрощения работы с fabric

package com.example.englen.utils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LevelEndEvent;

public class Fabric {

    // отправка информации о выученном / повторенном уровне , пройденной темы и тд
    public static void enterLevel(String levelName , int ex , boolean result)
    {
        if(levelName == null)levelName = "null";

        Answers.getInstance().logLevelEnd(new LevelEndEvent()
                .putLevelName(levelName)
                .putScore(ex)
                .putSuccess(result));
    }
}
