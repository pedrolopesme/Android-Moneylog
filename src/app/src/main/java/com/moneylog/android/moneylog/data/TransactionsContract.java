package com.moneylog.android.moneylog.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Transactions Contract
 */
public class TransactionsContract {

    public static final String CONTENT_AUTHORITY = "com.moneylog.android.moneylog";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TRANSACTIONS = "transactions";

    public static final class TransactionEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TRANSACTIONS)
                .build();

        public static final String TABLE_NAME = "transactions";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_CREATED_AT = "created_at";

        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(String.valueOf(id))
                    .build();
        }

    }

}
