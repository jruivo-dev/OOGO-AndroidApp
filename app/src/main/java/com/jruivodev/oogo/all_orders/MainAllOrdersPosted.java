package com.jruivodev.oogo.all_orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.jruivodev.oogo.R;

/**
 * Created by Jojih on 12/04/2017.
 */

public class MainAllOrdersPosted extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.view_pager_all_orders, container, false);

        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager_my_orders);
        NavigationTabStrip mTopNavigationTabStrip = (NavigationTabStrip) root.findViewById(R.id.nav_all_orders);

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                Toast.makeText(getContext(), "aqui", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

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
                return new PostedOrdersFragment();
            else
                return new PendingOrdersFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
            return super.getItemPosition(POSITION_NONE);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Child Fragment " + position;
        }
    }
}
