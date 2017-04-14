package com.jruivodev.oogo.my_orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jruivodev.oogo.objects_and_adapters.Order;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.all_orders.AllOrdersCellAdapter;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

/**
 * Created by Jojih on 11/04/2017.
 */

public class LikedOrdersFragment extends Fragment {
    private AllOrdersCellAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.orders_list, container, false);

        ListView listView = (ListView) root.findViewById(R.id.mainListView);

        // prepare elements to display
        final ArrayList<Order> items = new ArrayList<>();

        adapter = new AllOrdersCellAdapter(root.getContext(), items, false);
        listView.setAdapter(adapter);

        // set on click event listener to list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

        return root;
    }
}
