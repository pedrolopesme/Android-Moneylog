package com.moneylog.android.moneylog.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Transaction domain class
 */
public class Transaction implements Parcelable {

    private enum TxType {
        INCOME, DEBT
    }

    private long id;
    private String name;
    private TxType type;
    private Double amount;
    private Place place = new Place();
    private Location location = new Location();
    private Date createdAt = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TxType getType() {
        return type;
    }

    public void setType(TxType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getLatitude() {
        return location.getLatitude();
    }

    public void setLatitude(Double latitude) {
        location.setLatitude(latitude);
    }

    public Double getLongitude() {
        return location.getLongitude();
    }

    public void setLongitude(Double longitude) {
        location.setLongitude(longitude);
    }

    public String getPlaceName() {
        return place.getName();
    }

    public void setPlaceName(String name) {
        place.setName(name);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", place=" + place +
                ", amount=" + amount +
                ", location=" + location +
                ", createdAt=" + createdAt +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.place, flags);
        dest.writeSerializable(this.amount);
        dest.writeParcelable(this.location, flags);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
    }

    public Transaction() {
    }

    protected Transaction(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : TxType.values()[tmpType];
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.amount = (Double) in.readSerializable();
        this.location = in.readParcelable(Location.class.getClassLoader());
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
