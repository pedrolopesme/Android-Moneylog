package com.moneylog.android.moneylog.dao;

import android.content.ContentResolver;

import com.moneylog.android.moneylog.dao.contentProvider.ContentProviderTransactionDao;
import com.moneylog.android.moneylog.dao.http.HttpPlaceSuggestionDao;

/**
 * Base Dao Factory
 */
public class BaseDaoFactory implements DaoFactory {

    private final ContentResolver contentResolver;
    private final String apiKey;

    public BaseDaoFactory(final ContentResolver contentResolver, final String apiKey) {
        this.contentResolver = contentResolver;
        this.apiKey = apiKey;
    }

    @Override
    public TransactionDao getTransactionDao() {
        return new ContentProviderTransactionDao(contentResolver);
    }

    @Override
    public PlaceSuggestionDao getPlaceSuggestionDao() {
        return new HttpPlaceSuggestionDao(apiKey);
    }

}
