package com.moneylog.android.moneylog.dao;

import java.util.List;

/**
 * Place Suggestion Dao Interface
 */
public interface PlaceSuggestionDao {

    List<String> search(final String query, final double latitude, final double longitude);

}
