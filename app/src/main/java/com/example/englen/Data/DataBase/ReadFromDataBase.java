package com.example.englen.Data.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;

public class ReadFromDataBase {

    public static String readSpecificColumnFromBD(DataBaseHelper helper, int ID, String BDName, String Column) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных

        Cursor cursor = mDb.rawQuery(
                "SELECT " + Column + " FROM " + BDName + " WHERE id = " + ID, null
        ); // Читаем из базы данных определенные записи

        cursor.moveToNext();
        return cursor.getString(0);
    }


    // Читай из базы данных запись с определённым номером
    public static String[] readDataFromBD(DataBaseHelper helper, int ID, String BDName) throws ArrayIndexOutOfBoundsException {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + BDName, null); // Читаем из базы данных определенные записи

        if (cursor.getCount() < ID) {// Если записи в таблице закончились
            throw new ArrayIndexOutOfBoundsException();
        }

        int size = cursor.getColumnCount();
        return readRecordFromBD(ID, cursor, size);
    }

    // Читай всю базу данных
    public static String[][] readAllDataFromBD(DataBaseHelper helper, String BDName) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + BDName, null); // Читаем из базы данных определенные записи
        int size = cursor.getColumnCount();
        String Result[][] = new String[cursor.getCount()][cursor.getColumnCount()];

        for (int i = 1; i <= cursor.getCount(); i++) {
            String[] temp = readRecordFromBD(i - 1, cursor, size);
            for (int q = 0; q < cursor.getColumnCount(); q++) {
                Result[i - 1][q] = temp[q];
            }
        }

        return Result;
    }

    //Читает 1 запись из базы данных
    private static String[] readRecordFromBD(int ID, Cursor cursor, int size) {
        String ArraysResult[] = new String[size];
        // Читаем запись
        cursor.moveToPosition(ID);

        for (int i = 0; i < size; i++) {
            ArraysResult[i] = cursor.getString(i);
        }

        return ArraysResult;
    }

    // Обновление базы данных
    private static void updataDataBase(DataBaseHelper helper) {
        try {
            helper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }

    public static void writeToDataBase(DataBaseHelper helper, int id, String Column, String record, String DBName)   {
        SQLiteDatabase db = helper.getWritableDatabase();// Читаем базу данных
        ContentValues cv = new ContentValues();
        cv.put(Column,record);

        db.update(DBName, cv, "id=?",new String[]{Integer.toString(id)});
    }

}
