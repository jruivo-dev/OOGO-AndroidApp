package com.jruivodev.oogo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import com.jruivodev.oogo.all_orders.MainAllOrdersPosted;
import com.jruivodev.oogo.fragments.AccountFragment;
import com.jruivodev.oogo.fragments.NewOrderFragment;
import com.jruivodev.oogo.fragments.OrdersLocationFragment;
import com.jruivodev.oogo.my_orders.MainMyOrdersFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 * Created by Jojih on 09/04/2017.
 */

public class MainScreen extends AppCompatActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);


        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noResizeGoodness();
        mBottomBar.setItemsFromMenu(R.menu.menu_bottom, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                switch (menuItemId) {
                    case R.id.item1_all_orders:
                        MainAllOrdersPosted mainAllOrdersPosted = new MainAllOrdersPosted();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, mainAllOrdersPosted).commit();
                        break;

                    case R.id.item2_my_orders:
                        MainMyOrdersFragment mySubmittedOrdersFragment = new MainMyOrdersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, mySubmittedOrdersFragment).commit();
                        break;

                    case R.id.item3_new_order:
                        NewOrderFragment newOrderFragment = new NewOrderFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, newOrderFragment).commit();
                        break;
                    case R.id.item4_map:
                        OrdersLocationFragment ordersLocationFragment = new OrdersLocationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, ordersLocationFragment).commit();
                        break;

                    case R.id.item5_my_account:
                        AccountFragment accountFragment = new AccountFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, accountFragment).commit();
                        break;

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });


//        mBottomBar.mapColorForTab(0, "#f44336");
//        mBottomBar.mapColorForTab(1, "#9C27B0");
//        mBottomBar.mapColorForTab(2, "#03A9F4");
//        mBottomBar.mapColorForTab(3, "#8BC34A");
        mBottomBar.mapColorForTab(0, ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mBottomBar.mapColorForTab(1, ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        mBottomBar.mapColorForTab(2, ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mBottomBar.mapColorForTab(3, ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        mBottomBar.mapColorForTab(4, ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));

    }
}
