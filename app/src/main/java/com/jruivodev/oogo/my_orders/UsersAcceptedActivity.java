package com.jruivodev.oogo.my_orders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jruivodev.oogo.R;

/**
 * Created by Jojih on 14/04/2017.
 */

public class UsersAcceptedActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_accepted_users);

        Button btn = (Button) findViewById(R.id.button_go_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
