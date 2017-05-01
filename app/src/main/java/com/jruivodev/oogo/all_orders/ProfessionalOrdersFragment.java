package com.jruivodev.oogo.all_orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jruivodev.oogo.R;
import com.jruivodev.oogo.objects_and_adapters.Order;
import com.ramotion.foldingcell.FoldingCell;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

/**
 * Created by Jojih on 01/05/2017.
 */

public class ProfessionalOrdersFragment extends Fragment {

    //    private OrderAdapter mAdapter;
    private AllOrdersCellAdapter mAdapter;

    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listView;
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 500;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_all_orders, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view_all_orders);

        mAdapter = new AllOrdersCellAdapter(getContext(), orders, false);

        // set on click event listener to list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                mAdapter.registerToggle(pos);
            }
        });

        // Refresh page
        setRefresher(rootView);

//        new GetAsync().execute(LoginActivity.getUserId());
        return rootView;
    }

    private void setRefresher(View view) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
//                        new GetAsync().execute(LoginActivity.getUserId());
                        listView.destroyDrawingCache();
                        listView.setVisibility(ListView.INVISIBLE);
                        listView.setVisibility(ListView.VISIBLE);
                    }
                }, REFRESH_DELAY);
            }
        });
    }



}
