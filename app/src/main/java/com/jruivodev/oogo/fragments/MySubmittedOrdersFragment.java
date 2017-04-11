package com.jruivodev.oogo.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.LoginActivity;
import com.jruivodev.oogo.Order;
import com.jruivodev.oogo.OrderAdapter;
import com.jruivodev.oogo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jojih on 09/04/2017.
 */

public class MySubmittedOrdersFragment extends Fragment {

    private final String LOG = "MainActivity";
    private OrderAdapter mAdapter;
    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_submitted_orders, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view_my_orders);
        mAdapter = new OrderAdapter(getContext(), orders);

        new PostAsync().execute(LoginActivity.getUserId());
        return rootView;
    }

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;
        private static final String LOGIN_URL = "http://192.168.1.108/android/get_my_orders.php";


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
                params.put("userID", args[0]);

                Log.d("request", "starting");
                Log.d("my orders param BEFORE", params.toString());

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                Log.d("Params AFTER", params.toString());
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

                try {
                    orders.clear();

                    JSONArray ordersArray = json.getJSONArray("orders");

                    for (int i = 0; i < ordersArray.length(); i++) {
                        JSONObject currentOrder = ordersArray.getJSONObject(i);
                        String title = currentOrder.getString("title");
                        String description = currentOrder.getString("description");
                        String category = currentOrder.getString("category");
                        String price = currentOrder.getString("price");
                        orders.add(new Order(title, description, category, price));

                    }
                    listView.setAdapter(mAdapter);

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