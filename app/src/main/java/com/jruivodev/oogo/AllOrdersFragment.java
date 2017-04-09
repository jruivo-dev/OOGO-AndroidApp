package com.jruivodev.oogo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jojih on 09/04/2017.
 */

public class AllOrdersFragment extends Fragment {

    private final String LOG = "MainActivity";
    private OrderAdapter mAdapter;
    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_orders, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view);
        mAdapter = new OrderAdapter(getContext(), new ArrayList<Order>());
        listView.setAdapter(mAdapter);

        new GetAsync().execute();


        return rootView;
    }


    private class GetAsync extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://10.0.3.2/android/get_all_orders.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading orders...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();

                Log.d("ORDER DISPLAY ACTIVITY", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    orders.clear();

                    JSONArray ordersArray = json.getJSONArray("orders");
                    for (int i = 0; i < ordersArray.length(); i++) {
                        JSONObject currentOrder = ordersArray.getJSONObject(i);
                        String title = currentOrder.getString("title");
                        String description = currentOrder.getString("description");
                        String price = currentOrder.getString("price");


                        orders.add(new Order(title, description, "", price));


                    }
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {

            mAdapter.clear();
            mAdapter.addAll(orders);

            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                Toast.makeText(getContext(), json.toString(),
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
                Log.d("Failure", message);
            }
        }

    }
}
