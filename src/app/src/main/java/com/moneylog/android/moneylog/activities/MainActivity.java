package com.moneylog.android.moneylog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.fragments.ListTransactionsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab_add_transaction)
    FloatingActionButton addTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        createFragment();
        setAddTransactionOnClick();

    }

    /**
     * Creates Transaction List Fragment
     */
    private void createFragment() {
        Timber.i("Creating Transaction List Fragment");
        Fragment fragment = new ListTransactionsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.list_transactions_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Sets add transaction on click listener
     */
    private void setAddTransactionOnClick(){
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }


}
