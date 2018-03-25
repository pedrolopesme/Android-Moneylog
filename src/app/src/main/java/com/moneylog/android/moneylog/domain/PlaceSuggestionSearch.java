package com.moneylog.android.moneylog.domain;

/**
 * Place Suggestion Search
 */
public class PlaceSuggestionSearch {

    private String query;
    private double latitude;
    private double longitude;

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
