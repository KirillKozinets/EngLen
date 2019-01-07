package com.example.englen.Data.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.englen.utils.LearnWord;

import java.io.IOException;

public class ReadTask {

    static int MaxWord;

    // Читай из базы данных запись с определённым номером 
    public static String[] readTask(DataBaseHelper helper, int ID , String BDName) throws Exception {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + BDName, null); // Читаем из базы данных определенные записи
        int size =cursor.getColumnCount();

        return readFromBD(ID,cursor,size);
    }

    private static String[] readFromBD(int ID , Cursor cursor , int size) throws Exception {
        String ArraysResult[] = new String[size];
        if (cursor.getCount() > ID) {// Если мы не дошли до последней записи
            // Читаем запись
            cursor.move(ID);
            for (int i = 0; i < size; i++) {
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
