package com.jruivodev.oogo.fragments;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.login_and_signup.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jruivodev.oogo.R.id.map;

/**
 * Created by Jojih on 09/04/2017.
 */

public class OrdersLocationFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ArrayList<LatLng> locations = new ArrayList<>();

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


    // Gets lat and Lng from a String address.
    public LatLng getLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);

            //check for null
            if (address == null) {
                return null;
            }

            //Lets take first possibility from the all possibilities.
            Address location = address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            return latLng;
//            Toast.makeText(getContext(), location.getAddressLine(0) + "", Toast.LENGTH_LONG).show();
//            Toast.makeText(getContext(), latLng + "", Toast.LENGTH_LONG).show();
            //Put marker on map on that LatLng
//            Marker srchMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Destination")));

//            mMap.addMarker(new MarkerOptions().position(latLng).title("Tests"));

            //Animate and Zoon on that map location
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        new GetOrderLocation().execute(LoginActivity.getUserId());
//
//        Toast.makeText(getContext(), locations.size(), Toast.LENGTH_LONG).show();
        LatLng iscte = new LatLng(38.747841, -9.153443);

        mMap.addMarker(new MarkerOptions().position(iscte).title("Aqui estuda-se"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iscte, 9.5f));


//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.747841, -9.153443), 14.0f));
    }

    private class GetOrderLocation extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private final String LOGIN_URL = LoginActivity.LOCALHOST_URL + "/android/get_order_location.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading locations...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("userID", args[0]);

//                Log.d("ORDER DISPLAY ACTIVITY", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                if (json != null) {
//                    Log.d("JSON result", json.toString());

                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {


            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
//                Toast.makeText(getContext(), json.toString(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray ordersArray = json.getJSONArray("orders");
                    for (int i = 0; i < ordersArray.length(); i++) {
                        JSONObject currentOrder = ordersArray.getJSONObject(i);
                        String id = currentOrder.getString("id");
                        String title = currentOrder.getString("title");
                        String price = currentOrder.getString("price");
                        String location = currentOrder.getString("location");

                        locations.add(getLocationFromAddress(location));
                        for (LatLng loc : locations) {
                            mMap.addMarker(new MarkerOptions().position(loc).title(title + " : €" + price));
                        }
                    }
                    Toast.makeText(getContext(), locations.toString(), Toast.LENGTH_LONG).show();

                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }
}
