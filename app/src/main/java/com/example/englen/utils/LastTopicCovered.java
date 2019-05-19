
// Хранение информации о последней пройденной теме
// Используется для промотки до последней выученной темы
// во время открытия списка тем

package com.example.englen.utils;

import android.content.Context;

import com.example.englen.Data.Save;

public class LastTopicCovered {
    static private int lastTopicCoveredID = 0;

    private static final String tagID = "taglastTopicCoveredID";

    static public int getlastTopicCoveredID()
    {
        return lastTopicCoveredID;
    }

    static public void setlastTopicCoveredID(int value)
    {
        lastTopicCoveredID = value;
    }

    public static void Save() {
        Save.Save(tagID, Integer.toString(lastTopicCoveredID));
    }

    public static void Load(Context context) {
        lastTopicCoveredID = Integer.parseInt(Save.load(tagID, "0",context));
    }
}
