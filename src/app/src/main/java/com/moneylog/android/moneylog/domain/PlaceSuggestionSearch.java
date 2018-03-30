package com.moneylog.android.moneylog.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Place Suggestion Search
 */
public class PlaceSuggestionSearch implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.query);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected PlaceSuggestionSearch(Parcel in) {
        this.query = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<PlaceSuggestionSearch> CREATOR = new Parcelable.Creator<PlaceSuggestionSearch>() {
        @Override
        public PlaceSuggestionSearch createFromParcel(Parcel source) {
            return new PlaceSuggestionSearch(source);
        }

        @Override
        public PlaceSuggestionSearch[] newArray(int size) {
            return new PlaceSuggestionSearch[size];
        }
    };
}
