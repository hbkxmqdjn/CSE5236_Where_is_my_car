package com.bignerdranch.android.findmycar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.findmycar.database.ParkingDbSchema;

public class ParkingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "parkingBase.db";

    public ParkingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ParkingDbSchema.ParkingTable.NAME + '(' +
                " _id integer primary key autoincrement, " +
                ParkingDbSchema.Cols.UUID + ", " +
                ParkingDbSchema.Cols.NOTE + ", " +
                ParkingDbSchema.Cols.DATE +  ", " +
                ParkingDbSchema.Cols.LOCATION + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
