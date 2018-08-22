package com.example.husseiny.car.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.husseiny.car.database.CarContract.CarEntity;
/**
 * Created by husseiny on 7/30/2018.
 */

public class CarDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "car.db";
    public static final int DATABASE_VERSION = 6;

    public CarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + CarEntity.TABLE_NAME + "(" +
                CarEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CarEntity.COLUMN_CAR_NAME + " TEXT NOT NULL, " +
                //CarEntity.COLUMN_CAR_BRAND + " INTEGER, " +
                //CarEntity.COLUMN_CAR_DESC + " TEXT, " +
                CarEntity.COLUMN_CAR_PRICE + " INTEGER DEFAULT 0)";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            String SQL_ALTER_TABLE = "ALTER TABLE " + CarEntity.TABLE_NAME +
                    " DROP COLUMN " + CarEntity.COLUMN_CAR_DATE + " DATE";

            sqLiteDatabase.execSQL(SQL_ALTER_TABLE);
    }
}
