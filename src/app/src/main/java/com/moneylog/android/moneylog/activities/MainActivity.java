package com.moneylog.android.moneylog.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.loaders.BalanceAsyncTaskLoader;
import com.moneylog.android.moneylog.business.TransactionBusiness;
import com.moneylog.android.moneylog.clickListener.TransactionListChangedClickListener;
import com.moneylog.android.moneylog.dao.BaseDaoFactory;
import com.moneylog.android.moneylog.fragments.ListTransactionsFragment;
import com.moneylog.android.moneylog.utils.DateUtil;
import com.moneylog.android.moneylog.utils.NumberUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import widgets.MoneyLogWidgetService;

public class MainActivity extends BaseActivity implements TransactionListChangedClickListener {

    private TransactionBusiness transactionBusiness;

    @BindView(R.id.tv_toolbar_date)
    TextView tvToolbarDate;

    @BindView(R.id.tv_toolbar_account_balance)
    TextView tvToolbarAccountBalance;

    @BindView(R.id.fab_add_transaction)
    FloatingActionButton addTransaction;

    @BindView(R.id.main_coordinator_layout)
    CoordinatorLayout rootLayout;

    public static final int FORM_SAVED = 500;

    // Transaction Loader ID
    private static final int BALANCE_LOADER_INDEX = 200;

    // Transaction Loader Callbacks
    private LoaderManager.LoaderCallbacks<Double>
            balanceLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Double>() {

                @Override
                public Loader<Double> onCreateLoader(int id, Bundle args) {
                    return new BalanceAsyncTaskLoader(getApplicationContext(), transactionBusiness);
                }

                @Override
                public void onLoadFinished(Loader<Double> loader, Double data) {
                    tvToolbarAccountBalance.setText(String.format("$ %s", NumberUtil.stringify(data)));
                }

                @Override
                public void onLoaderReset(Loader<Double> loader) {
                    tvToolbarAccountBalance.setText(String.format("$ %s", NumberUtil.stringify(0.0)));
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentResolver contentResolver = getContentResolver();
        String apiKey = getString(R.string.config_google_maps_key);
        transactionBusiness = new TransactionBusiness(new BaseDaoFactory(contentResolver, apiKey));

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(BALANCE_LOADER_INDEX, null, balanceLoaderCallbacks);
        createFragment();
        setAddTransactionOnClick();
        renderToolbar();
    }

    /**
     * Creates Transaction List Fragment
     */
    private void createFragment() {
        Timber.i("Creating Transaction List Fragment");
        ListTransactionsFragment fragment = new ListTransactionsFragment();
        fragment.setListChangedClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.list_transactions_fragment, fragment);
        transaction.commit();
    }

    /**
     * Sets add transaction on click listener
     */
    private void setAddTransactionOnClick() {
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivityForResult(intent, FORM_SAVED);
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case FORM_SAVED:
                if (resultCode == FORM_SAVED) {
                    Snackbar snackbar = Snackbar
                            .make(rootLayout, R.string.transaction_saved, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
        }
    }

    /**
     * Render toolbar
     */
    private void renderToolbar() {
        tvToolbarDate.setText(DateUtil.format(new Date(), "MMM - yyyy"));
        getSupportLoaderManager().restartLoader(BALANCE_LOADER_INDEX, null, balanceLoaderCallbacks);
    }


    @Override
    public void listChanged() {
        renderToolbar();
        addToWidget();
    }

    public void addToWidget() {
        Timber.i("Updating account balance on widget ");
        MoneyLogWidgetService.startActionUpdateBalance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        createFragment();
        renderToolbar();
        addToWidget();
    }
}
