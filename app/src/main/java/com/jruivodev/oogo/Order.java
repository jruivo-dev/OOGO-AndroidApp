package com.jruivodev.oogo;

/**
 * Created by Jojih on 09/04/2017.
 */

public class Order {
    private String mTitle, mDescription, mCategory;

    public Order(String mTitle, String mDescription) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
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
}
