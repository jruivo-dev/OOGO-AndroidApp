package com.jruivodev.oogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                postData.put("email", username);
                postData.put("password", password);

                PostResponseAsyncTask task1 = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if (s.contains("success"))
                            Toast.makeText(LoginActivity.this, "Success Login", Toast.LENGTH_LONG).show();
                    }
                });
                task1.execute("http://10.0.3.2/2-login.php");
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupTypeActivity.class);
                startActivity(i);
            }
        });


    }
}
