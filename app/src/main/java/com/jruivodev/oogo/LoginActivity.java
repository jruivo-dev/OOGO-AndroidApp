package com.jruivodev.oogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by Jojih on 06/04/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private final String LOG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnSignUp = (Button) findViewById(R.id.button_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupTypeActivity.class);
                startActivity(i);
            }
        });


//        TextView userTypeText = (TextView) findViewById(R.id.text_login_type);
//        ImageView userTypeImage = (ImageView) findViewById(R.id.image_user_type);
//        if (userTypeString.equals("normal")) {
//            userTypeImage.setImageResource(R.drawable.normal);
//            userTypeText.setText("Normal Log In");
//        }
//
//        if (userTypeString.equals("professional")) {
//            userTypeImage.setImageResource(R.drawable.professional);
//            userTypeText.setText("Professional Log In");
//
//        }

//        HashMap postData = new HashMap();
//        postData.put("email", "mail@gmail.com");
//        postData.put("password", "pass");
//
//        PostResponseAsyncTask task1 = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
//            @Override
//            public void processFinish(String s) {
//                Log.d(LOG, s);
//            }
//        });
//        task1.execute("http://10.0.3.2/2-login.php");
    }
}
