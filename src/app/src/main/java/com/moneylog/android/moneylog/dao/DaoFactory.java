package com.moneylog.android.moneylog.dao;

/**
 * Dao Factory
 */
public interface DaoFactory {

    TransactionDao getTransactionDao();

    PlaceSuggestionDao getPlaceSuggestionDao();

}
