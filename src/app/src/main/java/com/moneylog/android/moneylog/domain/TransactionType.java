package com.moneylog.android.moneylog.domain;


public enum TransactionType {

    INCOME("Income"), DEBT("Debt");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static TransactionType getType(String type) {
        switch (type) {
            case "Income":
                return INCOME;
            case "Debt":
                return DEBT;
        }
        return null;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "type='" + type + '\'' +
                '}';
    }
}
