package com.moneylog.android.moneylog.dao.http;

import com.moneylog.android.moneylog.utils.NetworkUtil;

import java.net.URL;
import java.util.List;

import timber.log.Timber;

/**
 * Http Base Dao
 */
public class HttpBaseDao {

    protected String apiKey;

    HttpBaseDao(final String apiKey) {
        this.apiKey = apiKey;
    }

    String getApiKey() {
        return apiKey;
    }

}
