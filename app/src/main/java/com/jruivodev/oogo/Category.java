package com.jruivodev.oogo;

/**
 * Created by Jojih on 10/04/2017.
 */

public class Category {
    private String mId;
    private String mName;

    public Category(String mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }
}
