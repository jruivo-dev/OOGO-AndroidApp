package com.jruivodev.oogo.my_orders;

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
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.all_orders.AllOrdersCellAdapter;
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
 * Created by Jojih on 09/04/2017.
 */

public class MySubmittedOrdersFragment extends Fragment {

    private final String LOG = "MainActivity";
    //    private OrderAdapter mAdapter;
    private AllOrdersCellAdapter mAdapter;

    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listView;
    private PullToRefreshView mPullToRefreshView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_my_submitted_orders, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view_my_submitted);
        mAdapter = new AllOrdersCellAdapter(getContext(), orders, true);

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

        new PostAsync().execute(LoginActivity.getUserId());
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
                        new PostAsync().execute(LoginActivity.getUserId());
                        listView.destroyDrawingCache();
                        listView.setVisibility(ListView.INVISIBLE);
                        listView.setVisibility(ListView.VISIBLE);
                    }
                }, REFRESH_DELAY);
            }
        });
    }


    private class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;
        private static final String LOGIN_URL = "http://10.0.3.2/android/get_my_orders.php";


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
                        String id = currentOrder.getString("id");
                        String title = currentOrder.getString("title");
                        String description = currentOrder.getString("description");
                        String category = currentOrder.getString("category");
                        String price = currentOrder.getString("price");
                        orders.add(new Order(id, title, description, category, price));

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
