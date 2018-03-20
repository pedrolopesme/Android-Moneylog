package com.moneylog.android.moneylog.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.dao.BaseDaoFactory;
import com.moneylog.android.moneylog.dao.TransactionDao;
import com.moneylog.android.moneylog.domain.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Add Transaction Fragment
 */
public class AddTransactionFragment extends Fragment {

    private BaseDaoFactory daoFactory;

    @BindView(R.id.tv_transaction)
    TextInputEditText mTvTransactionName;

    @BindView(R.id.tv_amount)
    TextInputEditText mTvAmount;

    @BindView(R.id.tv_place)
    TextInputEditText mTvPlace;

    @BindView(R.id.ms_transaction_type)
    MaterialSpinner mTransactionType;

    public AddTransactionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoFactory = new BaseDaoFactory(getActivity().getApplicationContext().getContentResolver());

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
        return view;
    }

    private void renderTransactionType() {
        mTransactionType.setItems("Debt", "Income");
        mTransactionType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Timber.i("Clicked " + item);
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

    public void saveTransaction() {
        try {
            Timber.i("Trying to save transaction");
            Transaction tx = buildTransaction();
            final TransactionDao transactionDao = daoFactory.getTransactionDao();
            transactionDao.insert(tx);
            Timber.i("Transaction saved: %s", tx);
        } catch (Exception ex) {
            Timber.e("It was impossible to save transaction");
            Timber.e(ex);
        }
    }

    public Transaction buildTransaction() {
        Transaction tx = null;
        try {
            Timber.i("Building transaction");
            String name = mTvTransactionName.getText().toString();
            Double amount = Double.valueOf(mTvAmount.getText().toString());
            String place = mTvTransactionName.getText().toString();
            // TODO : get lat log
            Double latitude = 0.0;
            Double longitude = 0.0;

            tx = new Transaction();
            tx.setName(name);
            tx.setAmount(amount);
            tx.setPlaceName(place);
            tx.setLatitude(latitude);
            tx.setLongitude(longitude);
        } catch (Exception ex) {
            Timber.e("It was impossible to build transaction");
            Timber.e(ex);
        }
        return tx;
    }
}
