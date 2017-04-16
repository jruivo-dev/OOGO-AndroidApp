package com.jruivodev.oogo.objects_and_adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jojih on 14/04/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private View listRow;
    private String mOrderId;

    public UserAdapter(Context context, List<User> users, String orderId) {
        super(context, 0, users);
        mOrderId = orderId;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        listRow = convertView;

        if (listRow == null) {
            listRow = LayoutInflater.from(getContext()).inflate(R.layout.row_my_order_users, parent, false);
        }

        User currentUser = getItem(position);

        TextView userName = (TextView) listRow.findViewById(R.id.textview_user_name);
        userName.setText(currentUser.getName());
        setListeners(position);

        return listRow;
    }

    private void setListeners(final int position) {

        Button acceptBtn = (Button) listRow.findViewById(R.id.button_accept_user);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "UserID: " + getItem(position).getId(), Toast.LENGTH_SHORT).show();
                new AcceptUser().execute(mOrderId, getItem(position).getId());

            }
        });
    }

    // Used to update the status of the order.
    private class AcceptUser extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL = "http://10.0.3.2/android/accept_user.php";
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
                params.put("userID", args[1]);
                Log.d("Setting accept user", "with params:" + params);
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                if (json != null) {
                    Log.d("#SETTING JSON result", json.toString());
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
                Log.d("User accepted", json.toString());
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
            cancel(true);
        }

    }

}
