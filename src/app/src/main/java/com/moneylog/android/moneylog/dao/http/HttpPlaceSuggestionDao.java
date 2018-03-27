package com.moneylog.android.moneylog.dao.http;

import android.net.Uri;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.dao.PlaceSuggestionDao;
import com.moneylog.android.moneylog.parsers.PlaceSuggestionsParser;
import com.moneylog.android.moneylog.utils.NetworkUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import timber.log.Timber;

/**
 * Http Place Suggestion Dao
 */
public class HttpPlaceSuggestionDao extends HttpBaseDao implements PlaceSuggestionDao {

    private static final int DEFAULT_RADIUS = 5000; // 5km

    public HttpPlaceSuggestionDao(String apiKey) {
        super(apiKey);

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    @Override
    public List<String> search(final String query, final double latitude, final double longitude) {
        try {
            Timber.i("Getting Place Suggestions");
            URL url = buildUrl(query, latitude, longitude);
            String jsonStr = NetworkUtil.getResponseFromHttpUrl(url);
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return PlaceSuggestionsParser.parseList(jsonStr);
            }
        } catch (Exception ex) {
            Timber.e("It was impossible to search suggestions for query %s", query);
            Timber.e(ex);
        }
        return null;
    }

    private URL buildUrl(final String query, double latitude, double longitude) {
        try {
            String position = String.valueOf(latitude) + "," + String.valueOf(longitude);

            Uri uri = Uri.parse("https://maps.googleapis.com/maps/api/place/queryautocomplete/json")
                    .buildUpon()
                    .appendQueryParameter("radius", String.valueOf(DEFAULT_RADIUS))
                    .appendQueryParameter("rankBy", "distance")
                    .appendQueryParameter("key", apiKey)
                    .appendQueryParameter("input", query)
                    .appendQueryParameter("location", position)
                    .build();
            return new URL(uri.toString());
        } catch (MalformedURLException ex) {
            Timber.e("It was impossible to build URL");
            Timber.e(ex);
        }
        return null;
    }
}
