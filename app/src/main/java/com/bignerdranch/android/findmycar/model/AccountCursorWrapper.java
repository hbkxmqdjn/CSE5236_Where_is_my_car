package com.bignerdranch.android.findmycar.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.findmycar.model.AccountDbSchema.AccountsTable;

class AccountCursorWrapper extends CursorWrapper {
    AccountCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    Account getAccount() {
        String name = getString(getColumnIndex(AccountsTable.Cols.NAME));
        String password = getString(getColumnIndex(AccountsTable.Cols.PASSWORD));

        return new Account(name, password);
    }
}