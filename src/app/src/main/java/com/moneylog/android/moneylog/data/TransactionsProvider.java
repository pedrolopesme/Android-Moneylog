package com.moneylog.android.moneylog.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

import static com.moneylog.android.moneylog.data.TransactionsContract.*;

/**
 * Transactions Provider
 */
public class TransactionsProvider extends ContentProvider{

    public static final int CODE_TRANSACTIONS = 100;
    public static final int CODE_TRANSACTIONS_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TransactionsDBHelper dbHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TransactionsContract.CONTENT_AUTHORITY;

        /* This URI is content://com.moneylog.android.moneylog/transactions/ */
        matcher.addURI(authority, TransactionsContract.PATH_TRANSACTIONS, CODE_TRANSACTIONS);

        /* This URI is content://com.moneylog.android.moneylog/transactions/1 */
        matcher.addURI(authority, TransactionsContract.PATH_TRANSACTIONS + "/#", CODE_TRANSACTIONS_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new TransactionsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case CODE_TRANSACTIONS:
                cursor = dbHelper.getReadableDatabase().query(
                        TransactionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_TRANSACTIONS_WITH_ID:
                String id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{id};

                cursor = dbHelper.getReadableDatabase().query(
                        TransactionEntry.TABLE_NAME,
                        projection,
                        TransactionEntry._ID + " = ?",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        final Context context = getContext();
        if(context != null)
            cursor.setNotificationUri(context.getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Type isn't implemented.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_TRANSACTIONS:
                db.beginTransaction();
                try {
                    final long id = db.insert(TransactionEntry.TABLE_NAME, null, values);
                    if (id != -1) {
                        db.setTransactionSuccessful();
                        return TransactionEntry.buildUriWithId(id);
                    }
                } catch (Exception ex) {
                    Timber.e("It was impossible to insert values: " + ex.getMessage());
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new RuntimeException("Something went wrong.");
        }
        throw new RuntimeException("Value not inserted.");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case CODE_TRANSACTIONS_WITH_ID:
                String id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{id};

                numRowsDeleted = dbHelper.getWritableDatabase().delete(
                        TransactionEntry.TABLE_NAME,
                        TransactionEntry._ID + " = ?",
                        selectionArguments);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {

            final Context context = getContext();
            if(context != null)
                context.getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case CODE_TRANSACTIONS_WITH_ID:
                String id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{id};

                numRowsUpdated = dbHelper.getWritableDatabase().update(
                        TransactionEntry.TABLE_NAME,
                        values,
                        TransactionEntry._ID + " = ?",
                        selectionArguments);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsUpdated != 0) {
            final Context context = getContext();
            if(context != null)
                context.getContentResolver().notifyChange(uri, null);
        }

        return numRowsUpdated;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
