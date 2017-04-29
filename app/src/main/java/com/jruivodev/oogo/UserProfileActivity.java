package com.jruivodev.oogo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;

import com.jruivodev.oogo.objects_and_adapters.Rating;
import com.jruivodev.oogo.objects_and_adapters.RatingAdapter;

import java.util.ArrayList;

/**
 * Created by Jojih on 28/04/2017.
 */

public class UserProfileActivity extends AppCompatActivity {

    private ListView listView;
    private RatingAdapter mAdapter;
    private String mOrderId, mUserId;
    private ArrayList<Rating> ratings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        ratings.add(new Rating("Johny", "Title1", "Ddescriptin hello thanks goodbye my friends; love you all"));
        ratings.add(new Rating("Ana", "Title2", "Ddescriptin hello thanks goodbye my friends; love you all"));
        ratings.add(new Rating("Marg", "Title3", "Ddescriptin hello thanks goodbye my friends; love you all"));
        ratings.add(new Rating("Peter", "Title4", "Ddescriptin hello thanks goodbye my friends; love you all"));


        listView = (ListView) findViewById(R.id.rating_list_view);
        mAdapter = new RatingAdapter(this, ratings);
        listView.setAdapter(mAdapter);

// Paints the rating stars color
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, ContextCompat.getColor(this, R.color.colorPrimary));

        Button acceptBtn = (Button) findViewById(R.id.button_accept_user);
        if (acceptBtn != null)
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                new acceptUser.execute(mUserId, mOrderId);
                }
            });

        Button btn = (Button) findViewById(R.id.button_go_back_profile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
