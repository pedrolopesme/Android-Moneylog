package com.moneylog.android.moneylog.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.clickListener.TransactionItemClickListener;
import com.moneylog.android.moneylog.domain.Transaction;
import com.moneylog.android.moneylog.utils.DateUtil;
import com.moneylog.android.moneylog.utils.NumberUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Transaction Recycler View Adapter
 */
public class TransactionRecyclerViewAdapter extends
        RecyclerView.Adapter<TransactionRecyclerViewAdapter.TransactionViewHolder> {

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
        Timber.d("Executing onBindViewHolder for position #%d", position);
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

        @BindView(R.id.transaction_item_name)
        TextView mTransactionName;

        @BindView(R.id.transaction_item_amount)
        TextView mTransactionAmount;

        @BindView(R.id.transaction_item_date)
        TextView mTransactionDate;

        @BindView(R.id.bg_transaction_type)
        View mTransactionType;

        @BindView(R.id.ib_delete)
        ImageView mIbDelete;

        TransactionViewHolder(final View itemView) {
            super(itemView);

            if (getAdapterPosition() >= 0)


                itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            deleteOnClick();
            Timber.i("View Holder Created");
        }

        /**
         * Configuring onClick on viewholder item
         *
         * @param v
         */
        @Override
        public void onClick(final View v) {
            Transaction transaction = transactions.get(getAdapterPosition());
            if (transactions != null && transaction != null) {
                mOnClickListener.onTransactionItemClick(transaction);
                Timber.d("View Holder clicked on transaction %s", transaction);
            }
        }

        /**
         * Configuring onClick on delete imageView
         */
        public void deleteOnClick() {
            mIbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Transaction transaction = transactions.get(getAdapterPosition());
                    mOnClickListener.onTransactionItemDeleteClick(transaction);
                    Timber.d("Delete button clicked for transaction " + transaction);
                }
            });
        }

        /**
         * Renders a transaction item
         *
         * @param transaction
         */
        public void render(Transaction transaction) {
            Timber.d("Rendering transaction %s", transaction);
            mTransactionName.setText(transaction.getName());
            mTransactionAmount.setText(String.format("$ %s", NumberUtil.stringify(transaction.getAmount())));
            mTransactionDate.setText(DateUtil.format(transaction.getCreatedAt(), "MM/dd"));

            if (transaction.getAmount() >= 0)
                mTransactionType.setBackgroundResource(R.drawable.bg_tx_income);
            else
                mTransactionType.setBackgroundResource(R.drawable.bg_tx_debt);
        }


    }

}
