package com.jruivodev.oogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Jojih on 06/04/2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent = getIntent();
        String userTypeString = intent.getExtras().getString("userType");

        TextView userTypeText = (TextView) findViewById(R.id.text_login_type);
        ImageView userTypeImage = (ImageView) findViewById(R.id.image_user_type);
        if (userTypeString.equals("normal")) {
            userTypeImage.setImageResource(R.drawable.normal);
            userTypeText.setText("Normal Log In");
        }

        if (userTypeString.equals("professional")) {
            userTypeImage.setImageResource(R.drawable.professional);
            userTypeText.setText("Professional Log In");

        }


    }
}
