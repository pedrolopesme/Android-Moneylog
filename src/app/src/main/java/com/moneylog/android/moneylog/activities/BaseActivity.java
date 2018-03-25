package com.moneylog.android.moneylog.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.moneylog.android.moneylog.data.ConnectivityReceiver;

/**
 * Base Activity
 */
public class BaseActivity extends AppCompatActivity {

    protected static BaseActivity mInstance;

    public static synchronized BaseActivity getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}