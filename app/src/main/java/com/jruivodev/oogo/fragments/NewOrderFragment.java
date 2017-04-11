package com.jruivodev.oogo.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.jruivodev.oogo.Category;
import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.login_and_signup.LoginActivity;
import com.jruivodev.oogo.MainScreen;
import com.jruivodev.oogo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jojih on 09/04/2017.
 */

public class NewOrderFragment extends Fragment {

    private EditText titleEditText, descriptionEditText, priceEditText;
    private Button btnSubmit;
    private String mTitle, mDescription, mPrice;
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

        btnSubmit = (Button) rootView.findViewById(R.id.new_order_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle = titleEditText.getText().toString().trim();
                mDescription = descriptionEditText.getText().toString().trim();
                mPrice = priceEditText.getText().toString().trim();

                String spinnerCategory = spinner.getSelectedItem().toString();
                String categoryId = "";

                for (Category c : categories) {
                    if (c.getName().equals(spinnerCategory))
                        categoryId = c.getId();
                }

                new PostAsync().execute(mTitle, mDescription, mPrice, categoryId, LoginActivity.getUserId());
            }
        });


        return rootView;
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
