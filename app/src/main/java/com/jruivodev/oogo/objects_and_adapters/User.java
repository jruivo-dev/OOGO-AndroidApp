package com.jruivodev.oogo.objects_and_adapters;

/**
 * Created by Jojih on 14/04/2017.
 */

public class User {
    private String mId, mEmail, mName, mAddress, mContact, mPhoto;
    private Boolean isPremium;

    public User(String mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }


    public String getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getContact() {
        return mContact;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public Boolean getPremium() {
        return isPremium;
    }
}
