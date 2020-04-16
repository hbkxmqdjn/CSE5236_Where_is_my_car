package com.bignerdranch.android.findmycar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.findmycar.database.ParkingDbSchema.*;

public class ParkingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "parkingBase.db";

    public ParkingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + ParkingTable.NAME + '(' +
                " _id integer primary key autoincrement, " +
                ParkingTable.Cols.UUID + ", " +
                ParkingTable.Cols.NOTE + ", " +
                ParkingTable.Cols.DATE +  ", " +
                ParkingTable.Cols.LONGITUDE +  ", " +
                ParkingTable.Cols.LATITUDE +  ", " +
                ParkingTable.Cols.USERNAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
