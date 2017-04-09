package com.jruivodev.oogo;

/**
 * Created by Jojih on 09/04/2017.
 */

public class Order {
    private String mTitle, mDescription, mCategory, mPrice;

    public Order(String mTitle, String mDescription, String mCategory, String mPrice) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mPrice = mPrice;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getPrice() {
        return mPrice;
    }
}
