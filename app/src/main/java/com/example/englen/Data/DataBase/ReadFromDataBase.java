
//Класс для взаимодействия с базой данных

package com.example.englen.Data.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;

public class ReadFromDataBase {

    // Количество записей в которых определённый столбец равен определенному значению
    public static String getTheFirstIdSpecificColumn(DataBaseHelper helper, String BDName, String Row, String RowName) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery(
                "SELECT * FROM " + BDName + " WHERE " + Row + " is " + RowName, null
        ); // Читаем из базы данных определенные записи
        if (cursor.moveToFirst()) {
            String qa = cursor.getString(0);
            return qa;
        }
        return "0";
    }

    // Чтение количества записей
    public static int readCountRecord(DataBaseHelper helper, String BDName, String Row, String RowName) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        long result = DatabaseUtils.longForQuery(mDb, "SELECT COUNT(*) FROM " + BDName + " WHERE " + Row + " = " + "'" + RowName + "'", null);
        return (int) result;
    }

    //Читает определённую запись
    public static String[] readSpecificAllRowFromBD(DataBaseHelper helper, int ID, String BDName, String Row, String RowName) throws Exception {
        updataDataBase(helper);// Обновляем базу данных
        String[] result;
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery(
                "SELECT * FROM " + BDName + " WHERE " + Row + " = " + "'" + RowName + "'", null
        ); // Читаем из базы данных определенные записи
        if (cursor != null && cursor.moveToFirst()) {
            int size = cursor.getColumnCount();
            result = readSpecificRowFromBD(cursor, size, ID);
            return result;
        }
        throw new Exception();
    }

    // Читает все записи определённый столбец которых равен определённому значению
    public static String[][] readSpecificAllFromBD(DataBaseHelper helper, String BDName, String Row, String RowName) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных
        Cursor cursor = mDb.rawQuery(
                "SELECT * FROM " + BDName + " WHERE " + Row + " = " + "'" + RowName + "'", null
        ); // Читаем из базы данных определенные записи
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

    // Читает определённую строку
    public static String[] readSpecificRowFromBD(Cursor cursor, int size, int ID) {
        String ArraysResult[] = new String[size];
        // Читаем запись
        cursor.moveToPosition(ID);
        for (int i = 0; i < size; i++) {
            ArraysResult[i] = cursor.getString(i);
        }

        return ArraysResult;
    }

    // Читает определлёную запись по id
    public static String readSpecificColumnFromBD(DataBaseHelper helper, int ID, String BDName, String Column) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase mDb = helper.getWritableDatabase();// Читаем базу данных

        Cursor cursor = mDb.rawQuery(
                "SELECT " + Column + " FROM " + BDName + " WHERE _id = " + ID, null
        ); // Читаем из базы данных определенные записи

        cursor.moveToNext();
        return cursor.getString(0);

    }

    // Читает из базы данных запись с определённым номером
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

    // Читает всю базу данных
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

    //Читает  запись из базы данных
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
        helper.updateDataBase();
    }

    //Делает запись в определёный столбец определённой записи
    public static void writeToDataBase(DataBaseHelper helper, int id, String Column, String record, String DBName) {
        updataDataBase(helper);// Обновляем базу данных
        SQLiteDatabase db = helper.getWritableDatabase();// Читаем базу данных
        ContentValues cv = new ContentValues();
        cv.put(Column, record);

        db.update(DBName, cv, "_id=?", new String[]{Integer.toString(id)});
    }

}
