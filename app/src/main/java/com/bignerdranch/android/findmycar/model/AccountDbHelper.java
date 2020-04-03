package com.bignerdranch.android.findmycar.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.findmycar.model.AccountDbSchema.AccountsTable;



public class AccountDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TicTacToe.db";
    private static final int DATABASE_VERSION = 1;

    public AccountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + AccountsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccountsTable.Cols.NAME + " TEXT, " +
                AccountsTable.Cols.PASSWORD + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccountsTable.NAME);
        onCreate(sqLiteDatabase);
    }
}