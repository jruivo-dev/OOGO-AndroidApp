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
 * Created by Jojih on 28/04/2017.
 */

public class RatingAdapter extends ArrayAdapter<Rating> {


    public RatingAdapter(Context context, List<Rating> ratings) {
        super(context, 0, ratings);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listRow = convertView;

        if (listRow == null) {
            listRow = LayoutInflater.from(getContext()).inflate(R.layout.row_feedback, parent, false);
        }

        Rating currentRating = getItem(position);

        TextView ratingTitle = (TextView) listRow.findViewById(R.id.rating_title);
        TextView ratingDescription = (TextView) listRow.findViewById(R.id.rating_description);
        TextView orderPrice = (TextView) listRow.findViewById(R.id.order_price);

        ratingTitle.setText(currentRating.getTitle());
        ratingDescription.setText(currentRating.getDescription());
        return listRow;
    }
}
