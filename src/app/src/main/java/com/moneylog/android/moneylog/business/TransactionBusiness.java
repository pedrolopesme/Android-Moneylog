package com.moneylog.android.moneylog.business;

import com.moneylog.android.moneylog.dao.DaoFactory;
import com.moneylog.android.moneylog.dao.TransactionDao;
import com.moneylog.android.moneylog.domain.Transaction;
import com.moneylog.android.moneylog.domain.TransactionType;

import java.util.List;

/**
 * Transaction Business
 */

public class TransactionBusiness extends BaseBusiness {

    public TransactionBusiness(DaoFactory daoFactory) {
        super(daoFactory);
    }

    /**
     * Inserts a transaction. It changes tx amount value according to its type.
     *
     * @param transaction
     */
    public void insert(final Transaction transaction) {
        final TransactionDao transactionDao = daoFactory.getTransactionDao();

        if (transaction.getType() != null && transaction.getType().equals(TransactionType.DEBT))
            transaction.setAmount(transaction.getAmount() * -1);

        transactionDao.insert(transaction);
    }

    /**
     * Removes a transaction
     *
     * @param transactionId
     */
    public void delete(final long transactionId) {
        final TransactionDao transactionDao = daoFactory.getTransactionDao();
        transactionDao.delete(transactionId);
    }

    /**
     * Get all transactions
     *
     * @return
     */
    public List<Transaction> getTransactions() {
        final TransactionDao transactionDao = daoFactory.getTransactionDao();
        return transactionDao.getTransactions();
    }

    /**
     * Get all transactions value
     *
     * @return
     */
    public double getTransactionAmount() {
        final TransactionDao transactionDao = daoFactory.getTransactionDao();

        double amount = 0.0;
        for (Transaction tx : getTransactions())
            amount += tx.getAmount();

        return amount;
    }


}
