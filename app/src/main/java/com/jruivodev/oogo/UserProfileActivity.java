package com.jruivodev.oogo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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


        ratings.add(new Rating("Johny", "Title1", "DDESCRIPTIN HELLO THANKS GOODBYE MY FRIENDS; LOVE YOU ALL"));
        ratings.add(new Rating("Ana", "Title2", "DDESCRIPTIN HELLO THANKS GOODBYE MY FRIENDS; LOVE YOU ALL"));
        ratings.add(new Rating("Marg", "Title3", "DDESCRIPTIN HELLO THANKS GOODBYE MY FRIENDS; LOVE YOU ALL"));
        ratings.add(new Rating("Peter", "Title4", "DDESCRIPTIN HELLO THANKS GOODBYE MY FRIENDS; LOVE YOU ALL"));


        listView = (ListView) findViewById(R.id.rating_list_view);
        mAdapter = new RatingAdapter(this, ratings);
        listView.setAdapter(mAdapter);

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
