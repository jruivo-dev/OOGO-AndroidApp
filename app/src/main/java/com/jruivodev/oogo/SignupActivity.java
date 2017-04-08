package com.jruivodev.oogo;

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
 * Created by Jojih on 07/04/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private final String LOG = "MainActivity";
    private HashMap postData = new HashMap();
    private EditText usernameEditText, passwordEditText, nameEditText;


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
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();

                postData.put("email", username);
                postData.put("password", password);
                postData.put("nome", name);

                PostResponseAsyncTask task1 = new PostResponseAsyncTask(SignupActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if (s.contains("Registo com sucesso"))
                            Toast.makeText(SignupActivity.this, "Register Success", Toast.LENGTH_LONG).show();
                    }
                });
                task1.execute("http://10.0.3.2/1-registo.php");
            }
        });

    }
}
