package com.jruivodev.oogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SignupTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_choice);

        Button btnNormal = (Button) findViewById(R.id.button_normal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupTypeActivity.this, SignupActivity.class);
                intent.putExtra("userType", "normal");
                startActivity(intent);
            }
        });

        Button btnProfessional = (Button) findViewById(R.id.button_professional);
        btnProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupTypeActivity.this, SignupActivity.class);
                intent.putExtra("userType", "professional");
                startActivity(intent);
            }
        });
    }

}
