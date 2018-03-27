package com.moneylog.android.moneylog.domain;

/**
 * Place Suggestion Search
 */
public class PlaceSuggestionSearch {

    private final String query;
    private final double latitude;
    private final double longitude;

    public PlaceSuggestionSearch(String query, double latitude, double longitude) {
        this.query = query;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getQuery() {
        return query;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
