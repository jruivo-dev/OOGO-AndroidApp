package com.jruivodev.oogo.login_and_signup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jojih on 07/04/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private final String LOG = "MainActivity";
    private HashMap postData = new HashMap();
    private EditText usernameEditText, passwordEditText, nameEditText;
    private final String URL_HOST = "http://10.0.3.2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        nameEditText = (EditText) findViewById(R.id.input_name);
        usernameEditText = (EditText) findViewById(R.id.input_email);
        passwordEditText = (EditText) findViewById(R.id.input_password);

        Button btnCreateAccount = (Button) findViewById(R.id.button_create_account);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                new PostAsync().execute(name, username, password);
            }
        });
    }


    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String SIGNUP_URL = "http://10.0.3.2/android/registar.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("name", args[0]);
                params.put("username", args[1]);
                params.put("password", args[2]);

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

                Toast.makeText(SignupActivity.this, json.toString(),
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
//                Intent i = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(i);
                Log.d("Failure", message);
            }
        }

    }
}
