package com.jruivodev.oogo.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.jruivodev.oogo.Category;
import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.MainScreen;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.login_and_signup.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jojih on 09/04/2017.
 */

public class NewOrderFragment extends Fragment {

    private EditText titleEditText, descriptionEditText, priceEditText, locationEditText;
    private Button btnSubmit;
    private ImageButton btnLocation;
    private String mTitle, mDescription, mPrice, mLocation;
    private Spinner spinner;
    private ArrayList<Category> categories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_order, container, false);
        new GetCategoriesAsync().execute();

        spinner = (Spinner) rootView.findViewById(R.id.new_order_spinner);

        titleEditText = (EditText) rootView.findViewById(R.id.new_order_title);
        descriptionEditText = (EditText) rootView.findViewById(R.id.new_order_description);
        priceEditText = (EditText) rootView.findViewById(R.id.new_order_price);
        locationEditText = (EditText) rootView.findViewById(R.id.new_order_location);

        btnLocation = (ImageButton) rootView.findViewById(R.id.button_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocation = locationEditText.getText().toString().trim();
                getLocationFromAddress(mLocation);
            }
        });

        btnSubmit = (Button) rootView.findViewById(R.id.new_order_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle = titleEditText.getText().toString().trim();
                mDescription = descriptionEditText.getText().toString().trim();
                mPrice = priceEditText.getText().toString().trim();
                mLocation = locationEditText.getText().toString().trim();
                String formattedAddress = getFormattedAddress(mLocation);


                String spinnerCategory = spinner.getSelectedItem().toString();
                String categoryId = "";

                for (Category c : categories) {
                    if (c.getName().equals(spinnerCategory))
                        categoryId = c.getId();
                }

                new PostAsync().execute(mTitle, mDescription, mPrice, categoryId, LoginActivity.getUserId(), formattedAddress);
            }
        });


        return rootView;
    }

    public void getLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);

            //check for null
            if (address == null) {
                return;
            }

            //Lets take first possibility from the all possibilities.
            Address location = address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            Toast.makeText(getContext(), location.getAddressLine(0) + "", Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), latLng + "", Toast.LENGTH_LONG).show();
//            //Put marker on map on that LatLng
//            Marker srchMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.bb)));
//
//            //Animate and Zoon on that map location
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFormattedAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        Address location = null;
        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);

            //Lets take first possibility from the all possibilities.
            location = address.get(0);
            Toast.makeText(getContext(), location.getAddressLine(0) + "", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (location != null)
            return location.getAddressLine(0);
        return "";
    }

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String SIGNUP_URL = "http://10.0.3.2/android/add_new_order.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("title", args[0]);
                params.put("description", args[1]);
                params.put("price", args[2]);
                params.put("category", args[3]);
                params.put("userID", args[4]);
                params.put("location", args[5]);

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        SIGNUP_URL, "POST", params);

                Log.d("Params", params.toString());
                Log.d("Create Response", json.toString());


                if (json != null) {
                    Log.d("JSON result", json.toString());

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
                Toast.makeText(getActivity(), json.toString(),
                        Toast.LENGTH_LONG).show();
                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (success == 1) {
                Log.d("Success!", message);
            } else {
                Intent i = new Intent(getActivity(), MainScreen.class);
                startActivity(i);
                Log.d("Failure", message);
            }
        }
    }

    class GetCategoriesAsync extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://10.0.3.2/android/get_categories.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());

                    categories.clear();

                    JSONArray categoriesArray = json.getJSONArray("categories");
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject currentCategory = categoriesArray.getJSONObject(i);
                        String id = currentCategory.getString("id");
                        String name = currentCategory.getString("name");
                        categories.add(new Category(id, name));

                    }
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

            ArrayAdapter<Category> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
            spinner.setAdapter(adapter);

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                try {
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
