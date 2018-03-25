package com.moneylog.android.moneylog.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.data.ConnectivityReceiver;
import com.moneylog.android.moneylog.domain.TransactionLocation;
import com.moneylog.android.moneylog.fragments.AddTransactionFragment;
import com.moneylog.android.moneylog.utils.PermissionsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AddTransactionActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    @BindView(R.id.btn_add_transaction)
    Button mBtnAddTransaction;


    @BindView(R.id.add_transaction_coordinator_layout)
    CoordinatorLayout rootLayout;

    private AddTransactionFragment fragment;

    // Google location client
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_add_transaction);
        ButterKnife.bind(this);

        openFragment();
        addTransactionOnClickListener();
        renderToolbar();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        BaseActivity.getInstance().setConnectivityListener(this);
        getUserLocation();

        mInstance = this;
    }

    private void renderToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_transaction_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void openFragment() {
        Timber.i("Opening Add Transaction Fragment");
        fragment = new AddTransactionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_transaction_fragment, fragment);
        transaction.commit();
    }

    private void addTransactionOnClickListener() {
        mBtnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment != null && fragment.saveTransaction()) {
                    setResult(MainActivity.FORM_SAVED);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getUserLocation() {
        if (PermissionsUtils.checkLocationPermission(this)) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                setLocation(location);
                            }
                        }
                    });
        }
    }

    public void setLocation(Location location) {
        Timber.i("Location registered %s", location);
        if (location != null) {
            TransactionLocation txLocation = new TransactionLocation();
            txLocation.setLatitude(location.getLatitude());
            txLocation.setLongitude(location.getLongitude());
            fragment.setTransactionLocation(txLocation);
        }
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = getString(R.string.connected);
            color = Color.WHITE;
        } else {
            message = getString(R.string.no_connection);
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(rootLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
