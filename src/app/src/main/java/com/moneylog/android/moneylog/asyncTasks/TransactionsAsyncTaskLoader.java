package com.moneylog.android.moneylog.asyncTasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.moneylog.android.moneylog.dao.DaoFactory;
import com.moneylog.android.moneylog.domain.Transaction;

import java.util.List;

/**
 * Transactions Async Task Loader
 */
public class TransactionsAsyncTaskLoader extends AsyncTaskLoader<List<Transaction>> {

    private List<Transaction> transactions;

    private DaoFactory daoFactory;

    public TransactionsAsyncTaskLoader(Context context, DaoFactory daoFactory) {
        super(context);
        this.daoFactory = daoFactory;
    }

    @Override
    protected void onStartLoading() {
        if (transactions != null) {
            deliverResult(transactions);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Transaction> loadInBackground() {
        return daoFactory.getTransactionDao().getTransactions();
    }

    @Override
    public void deliverResult(List<Transaction> transactions) {
        super.deliverResult(transactions);
    }
}
