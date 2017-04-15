package com.jruivodev.oogo.objects_and_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jruivodev.oogo.R;

import java.util.List;

/**
 * Created by Jojih on 09/04/2017.
 */

public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }


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
        TextView orderCategory = (TextView) listRow.findViewById(R.id.order_category);

        orderTitle.setText(currentOrder.getTitle());
        orderDescription.setText(currentOrder.getDescription());
        orderPrice.setText(currentOrder.getPrice() + "€");
        orderCategory.setText(currentOrder.getCategory());


        return listRow;
    }
}
