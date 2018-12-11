package com.example.englen.Data.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.englen.Data.DataBase.DataBaseHelper;
import com.example.englen.utils.LearnWord;

import java.io.IOException;
import java.util.Random;

public class ReadTask {

    // Читай из базы данных запись с определённым номером 
    public static String[] readTask(DataBaseHelper helper, int item, String type) throws Exception {
        String[] ArraysResult = new String[item];

        try {
            helper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        SQLiteDatabase mDb = helper.getWritableDatabase();

        Cursor cursor = mDb.rawQuery("SELECT * FROM TaskAnswersList WHERE Type = type ", null);
        if(cursor.getCount() > LearnWord.getCurrentID()) {
            cursor.move(LearnWord.getCurrentID());

            for (int i = 0; i < item; i++)
                ArraysResult[i] = cursor.getString(i + 1);
        }
        else
            throw new Exception();

        return ArraysResult;
    }

}
