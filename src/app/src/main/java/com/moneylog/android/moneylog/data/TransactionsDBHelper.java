package com.moneylog.android.moneylog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.moneylog.android.moneylog.data.TransactionsContract.*;

/**
 * Transactions DB Helper
 */
public class TransactionsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "transactions.db";

    private static final int DATABASE_VERSION = 1;


    public TransactionsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TX_TABLE =
                "CREATE TABLE " + TransactionEntry.TABLE_NAME + " (" +
                        TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TransactionEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        TransactionEntry.COLUMN_AMOUNT + " REAL NOT NULL, " +
                        TransactionEntry.COLUMN_LATITUDE + " REAL, " +
                        TransactionEntry.COLUMN_LONGITUDE + " REAL, " +
                        TransactionEntry.COLUMN_PLACE + " TEXT, " +
                        TransactionEntry.COLUMN_CREATED_AT + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_TX_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
