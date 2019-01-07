package com.example.englen.Data.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.englen.utils.LearnWord;

import java.io.IOException;

public class ReadTask {

    // Читай из базы данных запись с определённым номером
    public static String[] readDataFromBD(DataBaseHelper helper, int ID , String BDName) throws Exception {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + BDName, null); // Читаем из базы данных определенные записи
        int size =cursor.getColumnCount();

        return readFromBD(ID,cursor,size);
    }

    // Читай из базы данных запись с определённым номером
    public static String[][] readAllDataFromBD(DataBaseHelper helper , String BDName) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + BDName, null); // Читаем из базы данных определенные записи
        int size =cursor.getColumnCount();
        String Result[][] = new String[cursor.getCount()][cursor.getColumnCount()];

        for(int i = 0 ; i < cursor.getCount() ; i++)
        {
            String[] temp = new String[0];

            try {
                temp = readFromBD(i,cursor,size);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int q = 0 ; q < cursor.getColumnCount() ; q++)
            {
                Result[i][q] = temp[q];
            }
        }

        return Result;
    }

    private static String[] readFromBD(int ID , Cursor cursor , int size) throws Exception {
        String ArraysResult[] = new String[size];
        if (cursor.getCount() > ID) {// Если мы не дошли до последней записи
            // Читаем запись
            cursor.move(ID);
            if(cursor.moveToFirst()) {
                for (int i = 1; i < size; i++) {
                    ArraysResult[i - 1] = cursor.getString(i);
                }
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
