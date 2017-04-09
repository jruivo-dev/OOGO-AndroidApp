package com.jruivodev.oogo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 * Created by Jojih on 09/04/2017.
 */

public class MainScreen extends AppCompatActivity {

    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_bottom, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.item1:
                        AllOrdersFragment orderDisplayActivity = new AllOrdersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, orderDisplayActivity).commit();
                        break;

                    case R.id.item2:
                        MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, myOrdersFragment).commit();
                        break;
                    case R.id.item3:
                        NewOrderFragment newOrderFragment = new NewOrderFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, newOrderFragment).commit();
                        break;
                    case R.id.item4:
                        SubmittedOrdersFragment submittedOrdersFragment = new SubmittedOrdersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, submittedOrdersFragment).commit();
                        break;
                    case R.id.item5:
                        AccountFragment accountFragment = new AccountFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, accountFragment).commit();
                        break;

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        mBottomBar.mapColorForTab(0, "#f44336");
        mBottomBar.mapColorForTab(1, "#9C27B0");
        mBottomBar.mapColorForTab(2, "#03A9F4");
        mBottomBar.mapColorForTab(3, "#8BC34A");

    }
}
