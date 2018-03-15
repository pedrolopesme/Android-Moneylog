package com.moneylog.android.moneylog.dao;

import android.content.ContentResolver;

import com.moneylog.android.moneylog.dao.contentProvider.ContentProviderTransactionDao;

/**
 * Base Dao Factory
 */
public class BaseDaoFactory implements DaoFactory {

    private final ContentResolver contentResolver;

    public BaseDaoFactory(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public TransactionDao getTransactionDao() {
        return new ContentProviderTransactionDao(contentResolver);
    }

}
