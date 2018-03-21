package com.moneylog.android.moneylog.dao;

import com.moneylog.android.moneylog.domain.Transaction;

import java.util.List;

/**
 * Transaction Dao Interface
 */
public interface TransactionDao {

    void insert(final Transaction transaction);

    void delete(final long transactionId);

    List<Transaction> getTransactions();
}
