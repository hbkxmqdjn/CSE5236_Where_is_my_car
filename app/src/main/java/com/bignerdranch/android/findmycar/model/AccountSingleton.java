package com.bignerdranch.android.findmycar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.bignerdranch.android.findmycar.model.AccountDbSchema.AccountsTable;

import java.util.ArrayList;
import java.util.List;

public class AccountSingleton {
    private static AccountSingleton sAccount;

    private SQLiteDatabase mDatabase;

    private static final String INSERT_STMT = "INSERT INTO " + AccountsTable.NAME + " (name, password) VALUES (?, ?)" ;

    public static AccountSingleton get(Context context) {
        if (sAccount == null) {
            sAccount = new AccountSingleton(context);
        }
        return sAccount;
    }

    private AccountSingleton(Context context) {
        AccountDbHelper dbHelper = new AccountDbHelper(context.getApplicationContext());
        mDatabase = dbHelper.getWritableDatabase();
    }

    private static ContentValues getContentValues(Account account) {
        ContentValues values = new ContentValues();
        values.put(AccountsTable.Cols.NAME, account.getName());
        values.put(AccountsTable.Cols.PASSWORD, account.getPassword());

        return values;
    }

    public void addAccount(Account account) {
        ContentValues contentValues = getContentValues(account);

        mDatabase.beginTransaction();
        try {
            SQLiteStatement statement = mDatabase.compileStatement(INSERT_STMT);
            statement.bindString(1, contentValues.getAsString(AccountsTable.Cols.NAME));
            statement.bindString(2, contentValues.getAsString(AccountsTable.Cols.PASSWORD));
            statement.executeInsert();
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    public void deleteAllAccounts() {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(AccountsTable.NAME, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    private AccountCursorWrapper queryAccounts() {
        Cursor cursor = mDatabase.query(
                AccountsTable.NAME,
                null, // columns; null selects all columns
                null,
                null,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return new AccountCursorWrapper(cursor);
    }

    public List<Account> getAccounts() {
        List<Account> accountList = new ArrayList<>();

        try (AccountCursorWrapper cursor = queryAccounts()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                accountList.add(cursor.getAccount());
                cursor.moveToNext();
            }
        }

        return accountList;
    }
}