package com.moneylog.android.moneylog.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * TransactionLocation domain class
 */
public class TransactionLocation implements Parcelable {

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "TransactionLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    public TransactionLocation() {
    }

    protected TransactionLocation(Parcel in) {
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<TransactionLocation> CREATOR = new Parcelable.Creator<TransactionLocation>() {
        @Override
        public TransactionLocation createFromParcel(Parcel source) {
            return new TransactionLocation(source);
        }

        @Override
        public TransactionLocation[] newArray(int size) {
            return new TransactionLocation[size];
        }
    };
}
