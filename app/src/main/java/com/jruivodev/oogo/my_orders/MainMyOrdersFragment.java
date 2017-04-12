package com.jruivodev.oogo.my_orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.jruivodev.oogo.R;

/**
 * Created by Jojih on 11/04/2017.
 */

public class MainMyOrdersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.view_pager_my_orders, container, false);

        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager_my_orders);
        NavigationTabStrip mTopNavigationTabStrip = (NavigationTabStrip) root.findViewById(R.id.nav_my_orders);


        /** Important: Must use the child FragmentManager or you will see side effects. */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        mTopNavigationTabStrip.setViewPager(viewPager, 0);
        return root;
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new MySubmittedOrdersFragment();
            else
                return new LikedOrdersFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Child Fragment " + position;
        }
    }
}
