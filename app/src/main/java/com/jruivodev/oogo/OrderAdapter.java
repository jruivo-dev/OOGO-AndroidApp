package com.jruivodev.oogo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jojih on 09/04/2017.
 */

public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listRow = convertView;

        if (listRow == null) {
            listRow = LayoutInflater.from(getContext()).inflate(R.layout.order_row, parent, false);
        }

        Order currentOrder = getItem(position);

        TextView orderTitle = (TextView) listRow.findViewById(R.id.order_title);
        TextView orderDescription = (TextView) listRow.findViewById(R.id.order_description);
        TextView orderPrice = (TextView) listRow.findViewById(R.id.order_price);

        orderTitle.setText(currentOrder.getTitle());
        orderDescription.setText(currentOrder.getDescription());
        orderPrice.setText(currentOrder.getPrice() + "€");


        return listRow;
    }
}
