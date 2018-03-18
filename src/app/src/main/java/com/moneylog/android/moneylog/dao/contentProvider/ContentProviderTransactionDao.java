package com.moneylog.android.moneylog.dao.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.moneylog.android.moneylog.dao.TransactionDao;
import com.moneylog.android.moneylog.domain.Transaction;
import com.moneylog.android.moneylog.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.moneylog.android.moneylog.data.TransactionsContract.*;

/**
 * Content Provider Transaction Dao
 */
public class ContentProviderTransactionDao extends ContentProviderBaseDao implements TransactionDao {

    public ContentProviderTransactionDao(ContentResolver contentResolver) {
        super(contentResolver);
    }

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_CREATED_AT = "created_at";

    @Override
    public void insert(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TransactionEntry.COLUMN_NAME, transaction.getName());
        values.put(TransactionEntry.COLUMN_AMOUNT, transaction.getAmount());
        values.put(TransactionEntry.COLUMN_LATITUDE, transaction.getLatitude());
        values.put(TransactionEntry.COLUMN_LONGITUDE, transaction.getLongitude());
        values.put(TransactionEntry.COLUMN_PLACE, transaction.getPlaceName());
        values.put(TransactionEntry.COLUMN_CREATED_AT, DateUtil.encode(transaction.getCreatedAt()));
        getContentResolver().insert(TransactionEntry.CONTENT_URI, values);
    }

    @Override
    public void delete(long transactionId) {
        Uri uri = TransactionEntry.CONTENT_URI.buildUpon().appendPath(Long.toString(transactionId)).build();
        getContentResolver().delete(uri, null, null);
    }

    @Override
    public List<Transaction> getTransactions() {
        final Cursor cursor = getContentResolver().query(
                TransactionEntry.CONTENT_URI,
                null,
                null,
                null,
                TransactionEntry.COLUMN_CREATED_AT + " DESC");

        List<Transaction> transactions = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {

                long id = cursor.getLong(cursor.getColumnIndex(TransactionEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(TransactionEntry.COLUMN_NAME));
                Double amount = cursor.getDouble(cursor.getColumnIndex(TransactionEntry.COLUMN_AMOUNT));
                Double latitude = cursor.getDouble(cursor.getColumnIndex(TransactionEntry.COLUMN_LATITUDE));
                Double longitude = cursor.getDouble(cursor.getColumnIndex(TransactionEntry.COLUMN_LONGITUDE));
                String place = cursor.getString(cursor.getColumnIndex(TransactionEntry.COLUMN_PLACE));
                Date createdAt = DateUtil.decode(cursor.getString(cursor.getColumnIndex(TransactionEntry.COLUMN_CREATED_AT)));

                Transaction transaction = new Transaction();
                transaction.setId(id);
                transaction.setName(name);
                transaction.setAmount(amount);
                transaction.setLatitude(latitude);
                transaction.setLongitude(longitude);
                transaction.setPlaceName(place);
                transaction.setCreatedAt(createdAt);

                transactions.add(transaction);
            }
            cursor.close();
        }
        return transactions;
    }

}
