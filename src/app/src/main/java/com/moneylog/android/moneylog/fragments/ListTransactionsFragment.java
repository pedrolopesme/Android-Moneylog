package com.moneylog.android.moneylog.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.adapters.TransactionRecyclerViewAdapter;
import com.moneylog.android.moneylog.clickListener.TransactionItemClickListener;
import com.moneylog.android.moneylog.dao.BaseDaoFactory;
import com.moneylog.android.moneylog.dao.TransactionDao;
import com.moneylog.android.moneylog.domain.Transaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *  Add Transaction Fragment
 */
public class ListTransactionsFragment extends Fragment implements TransactionItemClickListener {

    // DAO Factory
    private BaseDaoFactory daoFactory;

    // Transactions Recycler View Adapter
    private TransactionRecyclerViewAdapter mTransactionRecyclerViewAdapter;

    // Layout manager
    private LinearLayoutManager layoutManager;


    public ListTransactionsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoFactory = new BaseDaoFactory(getActivity().getApplicationContext().getContentResolver());

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        Timber.d("Creating List Transactions Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.d("Creating fragment view");

        View view = inflater.inflate(R.layout.fragment_list_transactions, container, false);
        ButterKnife.bind(this, view);


        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mTransactionRecyclerViewAdapter = new TransactionRecyclerViewAdapter(getActivity().getApplicationContext(), this);

        RecyclerView mTransactionsRecyclerView = (RecyclerView) view.findViewById(R.id.rc_transactions);
        mTransactionsRecyclerView.setLayoutManager(layoutManager);
        mTransactionsRecyclerView.setHasFixedSize(false);
        mTransactionsRecyclerView.setAdapter(mTransactionRecyclerViewAdapter);
        refreshTransactionList();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onTransactionItemClick(Transaction transaction) {
        Timber.i("Item clicked %s", transaction);
    }

    /**
     * Refreshes transaction list
     */
    private void refreshTransactionList() {
        Timber.i("Refreshing transaction list");
        final TransactionDao transactionDao = daoFactory.getTransactionDao();
        final List<Transaction> transactions = transactionDao.getTransactions();
        mTransactionRecyclerViewAdapter.setTransactions(transactions);
    }

}
