package com.jruivodev.oogo;

/**
 * Created by Jojih on 09/04/2017.
 */

public class Order {
    private String mTitle, mDescription, mCategory, mPrice, mId;

    public Order(String id, String mTitle, String mDescription, String mCategory, String mPrice) {
        mId = id;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mPrice = mPrice;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
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

    @Override
    public String toString() {
        return "Order Title:" + mTitle;
    }
}
