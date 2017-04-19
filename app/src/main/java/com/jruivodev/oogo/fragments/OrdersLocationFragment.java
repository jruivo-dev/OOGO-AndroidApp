package com.jruivodev.oogo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jruivodev.oogo.R;

import static com.jruivodev.oogo.R.id.map;

/**
 * Created by Jojih on 09/04/2017.
 */

public class OrdersLocationFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;

    public static OrdersLocationFragment newInstance() {
        OrdersLocationFragment fragment = new OrdersLocationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps_fragment, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
        LatLng iscte = new LatLng(38.747841, -9.153443);
        mMap.addMarker(new MarkerOptions().position(iscte).title("Isto é o ISCTE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iscte, 13.5f));

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.747841, -9.153443), 14.0f));

    }
}
