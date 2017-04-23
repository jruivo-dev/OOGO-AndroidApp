package com.jruivodev.oogo.objects_and_adapters;

/**
 * Created by Jojih on 09/04/2017.
 */

public class Order {
    private String mTitle, mDescription, mCategory, mPrice, mId, mUserName, mLocation;

    public Order(String id, String mTitle, String mDescription, String mCategory, String mPrice, String userName, String mLocation) {
        mId = id;
        this.mTitle = mTitle;
        this.mLocation = mLocation;

        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mPrice = mPrice;
        mUserName = userName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getUserName() {
        return mUserName;
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
