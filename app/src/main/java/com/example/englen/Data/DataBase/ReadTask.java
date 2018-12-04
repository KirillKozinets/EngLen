package com.example.englen.Data.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.englen.Data.DataBase.DataBaseHelper;

import java.io.IOException;
import java.util.Random;

public class ReadTask {

    public static String[] readTask(DataBaseHelper helper, int curs, String type) {
        String[] ArraysResult = new String[curs];

        try {
            helper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        SQLiteDatabase mDb = helper.getWritableDatabase();

        Cursor cursor = mDb.rawQuery("SELECT * FROM TaskAnswersList WHERE Type = type ", null);
        cursor.move( 1 + new Random().nextInt(cursor.getCount()));

        for(int i = 0 ; i < curs ; i++)
            ArraysResult[i] = cursor.getString(i+1);

        return ArraysResult;
    }

}
