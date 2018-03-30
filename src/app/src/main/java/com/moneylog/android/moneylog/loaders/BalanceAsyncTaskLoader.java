package com.moneylog.android.moneylog.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.moneylog.android.moneylog.business.TransactionBusiness;

/**
 * Balance Async Task Loader
 */
public class BalanceAsyncTaskLoader extends AsyncTaskLoader<Double> {

    private Double balance;

    private TransactionBusiness transactionBusiness;

    public BalanceAsyncTaskLoader(Context context, TransactionBusiness transactionBusiness) {
        super(context);
        this.transactionBusiness = transactionBusiness;
    }

    @Override
    protected void onStartLoading() {
        if (balance != null) {
            deliverResult(balance);
        } else {
            forceLoad();
        }
    }

    @Override
    public Double loadInBackground() {
        return transactionBusiness.getBalance();
    }

    @Override
    public void deliverResult(Double balance) {
        this.balance = balance;
        super.deliverResult(balance);
    }
}
