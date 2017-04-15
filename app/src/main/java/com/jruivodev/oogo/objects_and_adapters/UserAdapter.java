package com.jruivodev.oogo.objects_and_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jruivodev.oogo.R;

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

        if (listRow == null) {
            listRow = LayoutInflater.from(getContext()).inflate(R.layout.row_my_order_users, parent, false);
        }

        User currentUser = getItem(position);

        TextView userName = (TextView) listRow.findViewById(R.id.textview_user_name);
        userName.setText(currentUser.getName());

        return listRow;
    }

}
