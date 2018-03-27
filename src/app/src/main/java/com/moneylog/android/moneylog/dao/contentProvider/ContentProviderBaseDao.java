package com.moneylog.android.moneylog.dao.contentProvider;

import android.content.ContentResolver;

/**
 * Content Provider Base Dao
 */
public class ContentProviderBaseDao {

    private final ContentResolver contentResolver;

    ContentProviderBaseDao(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    ContentResolver getContentResolver() {
        return contentResolver;
    }

}
