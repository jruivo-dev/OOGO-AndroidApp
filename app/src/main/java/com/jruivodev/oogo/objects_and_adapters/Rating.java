package com.jruivodev.oogo.objects_and_adapters;

/**
 * Created by Jojih on 28/04/2017.
 */

public class Rating {
    private String mUserName, mRating, mTitle, mDescription;

    public Rating(String mUserName, String mTitle, String mDescription) {
        this.mUserName = mUserName;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getRating() {
        return mRating;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
