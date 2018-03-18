package com.moneylog.android.moneylog.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.fragments.AddTransactionFragment;

import timber.log.Timber;

public class AddTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_add_transaction);
        openFragment();
    }

    private void openFragment() {
        Timber.i("Opening Add Transaction Fragment");
        Fragment fragment = new AddTransactionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_transaction_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
