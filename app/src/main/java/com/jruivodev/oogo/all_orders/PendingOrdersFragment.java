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
import com.jruivodev.oogo.OrderState;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.login_and_signup.LoginActivity;
import com.jruivodev.oogo.objects_and_adapters.Order;
import com.ramotion.foldingcell.FoldingCell;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.jruivodev.oogo.all_orders.PostedOrdersFragment.REFRESH_DELAY;

/**
 * Created by Jojih on 11/04/2017.
 */

public class PendingOrdersFragment extends Fragment {
    //    private OrderAdapter mAdapter;
    private AllOrdersCellAdapter mAdapter;

    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listView;
    private PullToRefreshView mPullToRefreshView;
    private String orderState;
    private String mUserId = LoginActivity.getUserId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_pending_orders, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_pending_orders);

//        mAdapter = new OrderAdapter(getContext(), orders);

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
        setRefresher(rootView);
        new GetAsync().execute(LoginActivity.getUserId());

        mAdapter = new AllOrdersCellAdapter(getContext(), orders, false);

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

    private class GetAsync extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private final String LOGIN_URL = LoginActivity.LOCALHOST_URL + "/android/get_pending_requests.php";
//      private static final String LOGIN_URL = "http://192.168.1.108/android/get_my_orders.php";

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
                params.put("userID", args[0]);

                Log.d("PendingOrdersFragment", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                Log.d("PendingOrdersFrag JSON", json.toString());
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

            mAdapter.clear();
            mAdapter.addAll(orders);

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

                        orderState = currentOrder.getString("orderState");

                        String userName = currentOrder.getString("userName");
                        String location = currentOrder.getString("location");

                        orders.add(new Order(id, title, description, category, price, userName, location));
                        OrderState.setOrderState(id, mUserId, orderState);
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