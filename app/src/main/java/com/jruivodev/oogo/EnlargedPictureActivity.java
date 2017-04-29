package com.jruivodev.oogo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jojih on 29/04/2017.
 */

public class EnlargedPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enlarged_picture_activity);

        Bundle b = getIntent().getExtras();
        int image = b.getInt("image");

        ImageView enlargedImage = (ImageView) findViewById(R.id.order_enlarged_picture);
        enlargedImage.setImageResource(image);


        enlargedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
