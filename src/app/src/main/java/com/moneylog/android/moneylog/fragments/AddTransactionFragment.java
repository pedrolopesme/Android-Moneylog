package com.moneylog.android.moneylog.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.activities.MainActivity;
import com.moneylog.android.moneylog.business.TransactionBusiness;
import com.moneylog.android.moneylog.dao.BaseDaoFactory;
import com.moneylog.android.moneylog.domain.Transaction;
import com.moneylog.android.moneylog.domain.TransactionLocation;
import com.moneylog.android.moneylog.domain.TransactionType;
import com.moneylog.android.moneylog.utils.PermissionsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Add Transaction Fragment
 */
public class AddTransactionFragment extends Fragment {

    private TransactionBusiness transactionBusiness;

    @BindView(R.id.tv_transaction_layout)
    TextInputLayout mTvTransactionNameLayout;

    @BindView(R.id.tv_transaction)
    TextInputEditText mTvTransactionName;

    @BindView(R.id.tv_amount_layout)
    TextInputLayout mTvAmountLayout;

    @BindView(R.id.tv_amount)
    TextInputEditText mTvAmount;

    @BindView(R.id.tv_place)
    TextInputEditText mTvPlace;

    @BindView(R.id.ms_transaction_type)
    MaterialSpinner mTransactionType;

    @BindView(R.id.transaction_map)
    MapView mMapView;

    private GoogleMap googleMap;

    private TransactionType selectedTxType = TransactionType.DEBT;

    private TransactionLocation transactionLocation = null;

    public AddTransactionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionBusiness = new TransactionBusiness(new BaseDaoFactory(getActivity().getApplicationContext().getContentResolver()));

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.i("Creating fragment view");

        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        ButterKnife.bind(this, view);

        renderTransactionType();
        createMap(savedInstanceState);
        return view;
    }

    private void renderTransactionType() {
        mTransactionType.setItems(TransactionType.DEBT.getType(),
                TransactionType.INCOME.getType());
        mTransactionType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectedTxType = TransactionType.getType(item);
            }
        });
    }

    private void createMap(Bundle savedInstanceState) {
        Timber.i("Creating MAP");
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Timber.e(e);
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                Timber.i("Map is ready");
                googleMap = mMap;
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                setMapLocation();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean saveTransaction() {
        try {
            Timber.i("Trying to save transaction");
            if (validate()) {
                Transaction tx = buildTransaction();
                transactionBusiness.insertAndNotifyStatusChanging(tx,
                        getContext(),
                        MainActivity.class,
                        getString(R.string.balance_positive),
                        getString(R.string.balance_negative));
                Timber.i("Transaction saved: %s", tx);
                return true;
            }
        } catch (Exception ex) {
            Timber.e("It was impossible to save transaction");
            Timber.e(ex);
        }
        return false;
    }

    public boolean validate() {

        boolean validated = true;

        if (mTvTransactionName.getText().toString().trim().isEmpty()) {
            mTvTransactionNameLayout.setError(getString(R.string.error_required));
            validated = false;
        }

        if (mTvAmount.getText().toString().trim().isEmpty()) {
            mTvAmountLayout.setError(getString(R.string.error_required));
            validated = false;
        }

        return validated;
    }

    public Transaction buildTransaction() {
        Transaction tx = null;
        try {
            Timber.i("Building transaction");
            String name = mTvTransactionName.getText().toString();
            Double amount = Double.valueOf(mTvAmount.getText().toString());
            String place = mTvPlace.getText().toString();

            Double latitude = 0.0;
            Double longitude = 0.0;

            if (transactionLocation != null) {
                latitude = transactionLocation.getLatitude();
                longitude = transactionLocation.getLongitude();
            }

            tx = new Transaction();
            tx.setName(name);
            tx.setAmount(amount);
            tx.setPlaceName(place);
            tx.setLatitude(latitude);
            tx.setLongitude(longitude);
            tx.setType(selectedTxType);
        } catch (Exception ex) {
            Timber.e("It was impossible to build transaction");
            Timber.e(ex);
        }
        return tx;
    }

    public void setTransactionLocation(TransactionLocation transactionLocation) {
        this.transactionLocation = transactionLocation;
        setMapLocation();
    }

    private void setMapLocation() {
        Timber.e("Setting Map Location");
        if (transactionLocation != null && googleMap != null) {
            // For dropping a marker at a point on the Map
            LatLng currLocation = new LatLng(transactionLocation.getLatitude(), transactionLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(currLocation));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currLocation).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
