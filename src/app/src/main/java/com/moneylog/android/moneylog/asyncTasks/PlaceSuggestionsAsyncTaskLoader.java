package com.moneylog.android.moneylog.asyncTasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.dao.DaoFactory;
import com.moneylog.android.moneylog.dao.PlaceSuggestionDao;
import com.moneylog.android.moneylog.domain.PlaceSuggestionSearch;

import java.util.List;

import timber.log.Timber;

/**
 * Place Suggestions fetcher async task loader
 */
public class PlaceSuggestionsAsyncTaskLoader extends AsyncTaskLoader<List<String>> {

    private DaoFactory daoFactory;

    private PlaceSuggestionSearch search;

    private List<String> suggestions;

    public PlaceSuggestionsAsyncTaskLoader(Context context, DaoFactory daoFactory, PlaceSuggestionSearch search) {
        super(context);
        this.daoFactory = daoFactory;
        this.search = search;
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected void onStartLoading() {
        if (suggestions != null) {
            deliverResult(suggestions);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<String> loadInBackground() {
        try {
            Timber.i("Refreshing suggestions");
            if (search != null) {
                final PlaceSuggestionDao placeSuggestionDao = daoFactory.getPlaceSuggestionDao();
                return placeSuggestionDao.search(search.getQuery(), search.getLatitude(), search.getLongitude());
            }
        } catch (Exception ex) {
            Timber.e("Something bad has happened");
            Timber.e(ex);
        }
        return null;
    }

    @Override
    public void deliverResult(List<String> suggestions) {
        this.suggestions = suggestions;
        super.deliverResult(suggestions);
    }

    public void setSearch(PlaceSuggestionSearch search) {
        this.search = search;
    }
}