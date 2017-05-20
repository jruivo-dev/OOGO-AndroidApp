package com.jruivodev.oogo.my_orders;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;

import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.OrderState;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.login_and_signup.LoginActivity;
import com.jruivodev.oogo.objects_and_adapters.User;
import com.jruivodev.oogo.objects_and_adapters.UserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jojih on 14/04/2017.
 */

public class UsersAcceptedActivity extends AppCompatActivity {

    private ListView listView;
    private UserAdapter mAdapter;
    private String mOrderId, mUserId;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_accepted_users);

        Bundle b = getIntent().getExtras();
        mOrderId = b.getString("orderId");

//        Toast.makeText(this, "orderid: " + mOrderId, Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listview_accepted_users);
        mAdapter = new UserAdapter(this, new ArrayList<User>(), mOrderId, listView);



        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        // Paints the rating stars color
//        Drawable progress = ratingBar.getProgressDrawable();
//        DrawableCompat.setTint(progress, ContextCompat.getColor(this, R.color.colorPrimary));



        Button acceptBtn = (Button) findViewById(R.id.button_accept_user);
        if (acceptBtn != null)
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                new acceptUser.execute(mUserId, mOrderId);
                }
            });

        Button btn = (Button) findViewById(R.id.button_go_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        new GetMyOrderUsers().execute(mOrderId);
    }


    private class GetMyOrderUsers extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private final String LOGIN_URL = LoginActivity.LOCALHOST_URL + "/android/get_my_order_users.php";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Attempting login...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("orderID", args[0]);
                Log.d("request", "starting");
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);
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

                    users.clear();

                    JSONArray ordersArray = json.getJSONArray("users");
                    for (int i = 0; i < ordersArray.length(); i++) {
                        JSONObject currentUser = ordersArray.getJSONObject(i);

                        String userId = currentUser.getString("id");
                        String name = currentUser.getString("name");

                        String userEmail = currentUser.getString("userEmail");
                        String photo = currentUser.getString("photo");
                        String contact = currentUser.getString("contact");
                        String address = currentUser.getString("address");
                        String orderState = currentUser.getString("orderState");

                        User newUser = new User(userId, name);
                        OrderState.setOrderState(mOrderId, userId, orderState);
//                        newUser.setOrderState(mOrderId, orderState);

                        users.add(newUser);

                    }

                    mAdapter.addAll(users);
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
