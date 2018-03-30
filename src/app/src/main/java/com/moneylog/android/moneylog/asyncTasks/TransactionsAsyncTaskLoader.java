package com.moneylog.android.moneylog.asyncTasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.moneylog.android.moneylog.business.TransactionBusiness;
import com.moneylog.android.moneylog.domain.Transaction;

import java.util.List;

/**
 * Transactions Async Task Loader
 */
public class TransactionsAsyncTaskLoader extends AsyncTaskLoader<List<Transaction>> {

    private List<Transaction> transactions;

    private TransactionBusiness transactionBusiness;

    public TransactionsAsyncTaskLoader(Context context, TransactionBusiness transactionBusiness) {
        super(context);
        this.transactionBusiness = transactionBusiness;
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
        return transactionBusiness.getTransactions();
    }

    @Override
    public void deliverResult(List<Transaction> transactions) {
        super.deliverResult(transactions);
    }
}
