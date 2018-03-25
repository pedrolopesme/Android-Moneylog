package com.moneylog.android.moneylog.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.business.TransactionBusiness;
import com.moneylog.android.moneylog.clickListener.TransactionListChangedClickListener;
import com.moneylog.android.moneylog.dao.BaseDaoFactory;
import com.moneylog.android.moneylog.fragments.ListTransactionsFragment;
import com.moneylog.android.moneylog.utils.DateUtil;
import com.moneylog.android.moneylog.utils.NumberUtil;
import com.moneylog.android.moneylog.utils.PermissionsUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import widgets.MoneyLogWidgetService;

public class MainActivity extends AppCompatActivity implements TransactionListChangedClickListener {

    private TransactionBusiness transactionBusiness;

    @BindView(R.id.tv_toolbar_date)
    TextView tvToolbarDate;

    @BindView(R.id.tv_toolbar_account_summary)
    TextView tvToolbarAccountSummary;

    @BindView(R.id.fab_add_transaction)
    FloatingActionButton addTransaction;


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
                startActivity(intent);
            }
        });
    }

    /**
     * Render toolbar
     */
    private void renderToolbar() {
        final double transactionsAmount = transactionBusiness.getTransactionAmount();
        tvToolbarDate.setText(DateUtil.format(new Date(), "MMM - YYYY"));
        tvToolbarAccountSummary.setText(String.format("$ %s", NumberUtil.stringify(transactionsAmount)));
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
