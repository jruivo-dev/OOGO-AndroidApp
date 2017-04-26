package com.jruivodev.oogo.all_orders;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.objects_and_adapters.Order;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.login_and_signup.LoginActivity;
import com.ramotion.foldingcell.FoldingCell;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jojih on 09/04/2017.
 */

public class PostedOrdersFragment extends Fragment {

    private final String LOG = "MainActivity";
    //    private OrderAdapter mAdapter;
    private static AllOrdersCellAdapter mAdapter;

    private static ArrayList<Order> orders = new ArrayList<>();
    private static ListView listView;
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 500;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_all_orders, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view_all_orders);

//        mAdapter = new OrderAdapter(getContext(), orders);
        mAdapter = new AllOrdersCellAdapter(getContext(), orders, false);

        // set on click event listener to list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                mAdapter.registerToggle(pos);
            }
        });

        // Refresh page
        setRefresher(rootView);

        new GetAsync().execute(LoginActivity.getUserId());
        return rootView;
    }

    private void setRefresher(View view) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        new GetAsync().execute(LoginActivity.getUserId());
                        listView.destroyDrawingCache();
                        listView.setVisibility(ListView.INVISIBLE);
                        listView.setVisibility(ListView.VISIBLE);
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    public static void updateAllOrders() {
        new GetAsync().execute(LoginActivity.getUserId());
        listView.destroyDrawingCache();
        listView.setVisibility(ListView.INVISIBLE);
        listView.setVisibility(ListView.VISIBLE);
    }

    private static class GetAsync extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://10.0.3.2/android/get_all_orders.php";
//      private static final String LOGIN_URL = "http://192.168.1.108/android/get_my_orders.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
//            pDialog = new ProgressDialog();
//            pDialog.setMessage("Loading orders...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
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

            mAdapter.clear();
//            mAdapter.addAll(orders);

            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
//                Toast.makeText(getContext(), json.toString(),
//                        Toast.LENGTH_LONG).show();


                try {
                    orders.clear();
                    JSONArray ordersArray = json.getJSONArray("orders");
                    for (int i = 0; i < ordersArray.length(); i++) {
                        JSONObject currentOrder = ordersArray.getJSONObject(i);
                        String id = currentOrder.getString("id");
                        String title = currentOrder.getString("title");
                        String description = currentOrder.getString("description");
                        String category = currentOrder.getString("category");
                        String price = currentOrder.getString("price");
                        String userName = currentOrder.getString("userName");
                        String location = currentOrder.getString("location");

                        orders.add(new Order(id, title, description, category, price, userName, location));
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
