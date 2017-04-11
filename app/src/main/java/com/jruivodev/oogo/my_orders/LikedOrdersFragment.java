package com.jruivodev.oogo.my_orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jruivodev.oogo.R;

/**
 * Created by Jojih on 11/04/2017.
 */

public class LikedOrdersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liked_orders, container, false);
        return root;
    }
}
