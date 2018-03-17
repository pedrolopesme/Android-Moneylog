package com.moneylog.android.moneylog.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.clickListener.TransactionItemClickListener;
import com.moneylog.android.moneylog.domain.Transaction;

import java.util.List;

import timber.log.Timber;

/**
 * Transaction Recycler View Adapter
 */
public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.TransactionViewHolder> {

    // Transactions
    private List<Transaction> transactions = null;

    // Click Listener
    private final TransactionItemClickListener mOnClickListener;

    // Activity Context
    private final Context context;

    /**
     * TransactionRecyclerViewAdapter
     *
     * @param context  Activity Context
     * @param listener TransactionItemClickListener
     */
    public TransactionRecyclerViewAdapter(final Context context, final TransactionItemClickListener listener) {
        this.context = context;
        mOnClickListener = listener;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Timber.d("Creating view holder ...");
        Context context = parent.getContext();
        int transactionItemLayoutId = R.layout.item_transaction;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(transactionItemLayoutId, parent, false);
        TransactionViewHolder viewHolder = new TransactionViewHolder(view);
        Timber.d("onCreateViewHolder created!");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Timber.d("Executing onBindViewHolder for position #" + position);
        Transaction transaction = transactions.get(position);
        holder.render(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    public void setTransactions(final List<Transaction> transactions) {
        Timber.d("Refreshing transaction list");
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    /**
     * Caches children views
     */
    class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TransactionViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Timber.i("View Holder Created");
        }

        @Override
        public void onClick(final View v) {
            if (transactions != null) {
                Transaction transaction = transactions.get(getAdapterPosition());
                if (transaction != null) {
                    mOnClickListener.onTransactionItemClick(transaction);
                    Timber.d("View Holder clicked on transaction " + transaction);
                }
            }
        }

        /**
         * Renders a transaction item
         *
         * @param transaction
         */
        public void render(Transaction transaction) {
            // TODO: Code to render a transaction
            Timber.d("Rendering transaction " + transaction);
        }


    }

}
