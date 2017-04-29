package com.jruivodev.oogo.login_and_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.MainScreen;
import com.jruivodev.oogo.R;

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
    private static String USER_ID = "";
    private CheckBox rememberCheckBox;
    private String username, password;
    private Boolean isRemember = true;

    public static String PREFS_NAME = "mypre";
    public static String PREF_USERNAME = "username";
    public static String PREF_PASSWORD = "password";

    //    public static String LOCALHOST_URL = "http://10.0.3.2";
    public static String LOCALHOST_URL = "http://192.168.1.108";
    private static String USER_NAME = "";

    public static String getUserId() {
        return USER_ID;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public void getUser() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            usernameEditText.setText(username);
            passwordEditText.setText(password);
        }
    }

    public void rememberMe(String user, String password) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, user)
                .putString(PREF_PASSWORD, password)
                .apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnSignUp = (Button) findViewById(R.id.button_signup);
        rememberCheckBox = (CheckBox) findViewById(R.id.checkbox_remember);
        rememberCheckBox.setChecked(true);
        usernameEditText = (EditText) findViewById(R.id.text_login_username);
        passwordEditText = (EditText) findViewById(R.id.text_login_password);
        Button btnLogin = (Button) findViewById(R.id.button_login);

        getUser();

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
                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                if (isRemember) {
                    rememberMe(username, password);
                }

                new PostAsync().execute(username, password);
            }
        });

        rememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRemember = isChecked;
            }
        });


    }

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private final String LOGIN_URL = LoginActivity.LOCALHOST_URL + "/android/login.php";
//        private static final String LOGIN_URL = "http://192.168.1.108/android/login.php";

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

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                Log.d("Create Response", json.toString());


                if (json != null) {
                    Log.d("JSON result", json.toString());
                    USER_ID = json.getString("uid");
                    USER_NAME = json.getString("userName");
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
                Intent i = new Intent(LoginActivity.this, MainScreen.class);
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
