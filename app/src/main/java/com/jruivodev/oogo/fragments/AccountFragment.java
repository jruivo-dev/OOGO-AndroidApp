package com.jruivodev.oogo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jruivodev.oogo.R;

/**
 * Created by Jojih on 09/04/2017.
 */

public class AccountFragment extends Fragment {

    private EditText mUserNameET, mEmailET, mWebsiteET, mPhoneET, mAddressET;
    private TextView mUserName, mEmail, mWebsite, mPhone, mAddress;
    private Button editButton, saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_account, container, false);
        mUserName = (TextView) rootView.findViewById(R.id.user_name);
        mUserNameET = (EditText) rootView.findViewById(R.id.user_name_edit_text);
        mEmail = (TextView) rootView.findViewById(R.id.email);
        mEmailET = (EditText) rootView.findViewById(R.id.email_edit_text);
        mWebsite = (TextView) rootView.findViewById(R.id.website);
        mWebsiteET = (EditText) rootView.findViewById(R.id.website_edit_text);
        mPhone = (TextView) rootView.findViewById(R.id.phone);
        mPhoneET = (EditText) rootView.findViewById(R.id.phone_edit_text);
        mAddress = (TextView) rootView.findViewById(R.id.address);
        mAddressET = (EditText) rootView.findViewById(R.id.address_edit_text);


        editButton = (Button) rootView.findViewById(R.id.edit_button);
        saveButton = (Button) rootView.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserNameET.setVisibility(View.GONE);
                mUserName.setVisibility(View.VISIBLE);

                mEmailET.setVisibility(View.GONE);
                mEmail.setVisibility(View.VISIBLE);

                mWebsiteET.setVisibility(View.GONE);
                mWebsite.setVisibility(View.VISIBLE);

                mPhoneET.setVisibility(View.GONE);
                mPhone.setVisibility(View.VISIBLE);

                mAddressET.setVisibility(View.GONE);
                mAddress.setVisibility(View.VISIBLE);


                editButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName.setVisibility(View.GONE);
                mUserNameET.setVisibility(View.VISIBLE);

                mEmail.setVisibility(View.GONE);
                mEmailET.setVisibility(View.VISIBLE);

                mWebsite.setVisibility(View.GONE);
                mWebsiteET.setVisibility(View.VISIBLE);

                mPhone.setVisibility(View.GONE);
                mPhoneET.setVisibility(View.VISIBLE);

                mAddress.setVisibility(View.GONE);
                mAddressET.setVisibility(View.VISIBLE);


                editButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });


        return rootView;
    }
}
