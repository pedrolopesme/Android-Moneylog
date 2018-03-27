package com.moneylog.android.moneylog.business;

import com.moneylog.android.moneylog.dao.DaoFactory;

/**
 * Base Business
 */
public class BaseBusiness {

    protected final DaoFactory daoFactory;

    BaseBusiness(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

}
