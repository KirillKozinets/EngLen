package com.example.englen.Data.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.englen.utils.LearnWord;

import java.io.IOException;

public class ReadTask {

    static int MaxWord;

    // Читай из базы данных запись с определённым номером 
    public static String[] readTask(DataBaseHelper helper, int item, int max, Boolean isNew) throws Exception {
        String[] ArraysResult = new String[item];
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных

        Cursor cursor = mDb.rawQuery("SELECT * FROM TaskAnswersList", null); // Читаем из базы данных определенные записи
        if (cursor.getCount() > max && (LearnWord.getCurrentID() > max || isNew == true)) {// Если мы не дошли до последней записи
            // Читаем запись
            cursor.move(max);
            for (int i = 0; i < item; i++) {
                ArraysResult[i] = cursor.getString(i + 1);
            }

        } else
            throw new Exception();

        return ArraysResult;
    }

    public static void updataDataBase(DataBaseHelper helper) {
        try {
            helper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }

}
