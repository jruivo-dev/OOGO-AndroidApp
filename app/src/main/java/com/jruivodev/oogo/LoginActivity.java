package com.jruivodev.oogo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Jojih on 06/04/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private final String LOG = "MainActivity";
    private HashMap postData = new HashMap();
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnSignUp = (Button) findViewById(R.id.button_signup);


        usernameEditText = (EditText) findViewById(R.id.text_login_username);
        passwordEditText = (EditText) findViewById(R.id.text_login_password);
        Button btnLogin = (Button) findViewById(R.id.button_login);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupTypeActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                new PostAsync().execute(username, password);
            }
        });

    }

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://10.0.3.2/android/login.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("username", args[0]);
                params.put("password", args[1]);

                Log.d("request", "starting");
                Log.d("PARAMS", params.toString());

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

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
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                Log.d("Success!", message);
                Intent i = new Intent(LoginActivity.this, OrderDisplayActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(LoginActivity.this, "Failed to", Toast.LENGTH_LONG).show();

//                Intent i = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(i);
                Log.d("Failure", message);
            }
        }

    }
}
