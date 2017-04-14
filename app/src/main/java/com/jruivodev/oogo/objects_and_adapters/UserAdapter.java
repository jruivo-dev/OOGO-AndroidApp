package com.jruivodev.oogo.objects_and_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Jojih on 14/04/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listRow = convertView;
//
//        if (listRow == null) {
//            listRow = LayoutInflater.from(getContext()).inflate(R.layout.order_row, parent, false);
//        }
//
//        User currentUser = getItem(position);
//
//        TextView orderTitle = (TextView) listRow.findViewById(R.id.order_title);
//        TextView orderDescription = (TextView) listRow.findViewById(R.id.order_description);
//        TextView orderPrice = (TextView) listRow.findViewById(R.id.order_price);
//        TextView orderCategory = (TextView) listRow.findViewById(R.id.order_category);
//
//        orderTitle.setText(currentOrder.getTitle());
//        orderDescription.setText(currentOrder.getDescription());
//        orderPrice.setText(currentOrder.getPrice() + "€");
//        orderCategory.setText(currentOrder.getCategory());
//

        return listRow;
    }

}
