package com.moneylog.android.moneylog.asyncTasks;

import android.os.AsyncTask;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.dao.DaoFactory;
import com.moneylog.android.moneylog.dao.PlaceSuggestionDao;
import com.moneylog.android.moneylog.domain.PlaceSuggestionSearch;
import com.moneylog.android.moneylog.fragments.PlaceSuggestionsFragment;

import java.util.List;

import timber.log.Timber;

/**
 * Place Suggestions fetcher async task
 */
public class PlaceSuggestionsAsyncTask extends AsyncTask<PlaceSuggestionSearch, Void, Boolean> {

    private final PlaceSuggestionsFragment fragment;

    private final PlaceSuggestionDao placeSuggestionDao;

    public PlaceSuggestionsAsyncTask(PlaceSuggestionsFragment fragment, DaoFactory daoFactory) {
        this.fragment = fragment;
        this.placeSuggestionDao = daoFactory.getPlaceSuggestionDao();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }


    @Override
    protected Boolean doInBackground(PlaceSuggestionSearch... searches) {
        try {
            Timber.i("Refreshing suggestions");
            PlaceSuggestionSearch queryString = searches[0];
            final List<String> suggestions = placeSuggestionDao.search(queryString.getQuery(),
                    queryString.getLatitude(),
                    queryString.getLongitude());

            fragment.renderPlaceSuggestions(suggestions);
            return true;
        } catch (Exception ex) {
            Timber.e("Something bad has happened");
            Timber.e(ex);
            return false;
        }
    }

}
