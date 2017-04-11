package com.jruivodev.oogo.all_orders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jruivodev.oogo.Order;
import com.jruivodev.oogo.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Jojih on 11/04/2017.
 */

public class FoldingCellListAdapter extends ArrayAdapter<Order> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public FoldingCellListAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        Order item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.all_orders_cell, parent, false);

            Order currentOrder = getItem(position);
            Log.d("FOLDING ADAPTER", currentOrder.getTitle());

            viewHolder.orderTitle = (TextView) cell.findViewById(R.id.order_title);
            viewHolder.orderDescription = (TextView) cell.findViewById(R.id.order_description);
            viewHolder.orderPrice = (TextView) cell.findViewById(R.id.order_price);
            viewHolder.orderCategory = (TextView) cell.findViewById(R.id.order_category);
            cell.setTag(viewHolder);

        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        viewHolder.orderTitle.setText(item.getTitle());
        viewHolder.orderDescription.setText(item.getDescription());
        viewHolder.orderPrice.setText(item.getPrice() + "€");
        viewHolder.orderCategory.setText(item.getCategory());


        return cell;
    }

    // simple methods for register cell state changes

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView orderTitle;
        TextView orderDescription;
        TextView orderPrice;
        TextView orderCategory;
    }

}
