package com.jruivodev.oogo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jruivodev.oogo.R;

/**
 * Created by Jojih on 09/04/2017.
 */

public class SubmittedOrdersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subbmited_orders, container, false);

        return rootView;
    }
}
