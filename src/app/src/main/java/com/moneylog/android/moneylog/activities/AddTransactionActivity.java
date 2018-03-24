package com.moneylog.android.moneylog.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.domain.TransactionLocation;
import com.moneylog.android.moneylog.fragments.AddTransactionFragment;
import com.moneylog.android.moneylog.utils.PermissionsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import widgets.MoneyLogWidgetService;

public class AddTransactionActivity extends AppCompatActivity {


    @BindView(R.id.btn_add_transaction)
    Button mBtnAddTransaction;

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
        getUserLocation();
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
                    onBackPressed();
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
}
