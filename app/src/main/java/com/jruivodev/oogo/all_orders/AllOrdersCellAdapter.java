package com.jruivodev.oogo.all_orders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jruivodev.oogo.EnlargedPictureActivity;
import com.jruivodev.oogo.JSONParser;
import com.jruivodev.oogo.OrderState;
import com.jruivodev.oogo.R;
import com.jruivodev.oogo.UserProfileActivity;
import com.jruivodev.oogo.login_and_signup.LoginActivity;
import com.jruivodev.oogo.my_orders.UsersAcceptedActivity;
import com.jruivodev.oogo.objects_and_adapters.Order;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jojih on 11/04/2017.
 */

public class AllOrdersCellAdapter extends ArrayAdapter<Order> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private FoldingCell cell;
    private Boolean isEditMode = false;
    private Button btnViewUsers;
    private String mOrderState;
    private String mUserId = LoginActivity.getUserId();
    private String mOrderId;
    private Button btnCancel;
    private Button btnSubmitApplication;
    private ImageView profilePicture, unfoldProfilePicture, image1, image2, image3;

    public AllOrdersCellAdapter(Context context, List<Order> orders, Boolean isEditMode) {
        super(context, 0, orders);
        this.isEditMode = isEditMode;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // get item for selected view
        Order item = getItem(position);


        mOrderId = item.getId();

        // if cell is exists - reuse it, if not - create the new one from resource
        cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell_all_orders, parent, false);


            viewHolder.orderTitle = (TextView) cell.findViewById(R.id.order_title);
            viewHolder.orderDescription = (TextView) cell.findViewById(R.id.order_description);
            viewHolder.orderPrice = (TextView) cell.findViewById(R.id.order_price);
            viewHolder.orderCategory = (TextView) cell.findViewById(R.id.order_category);
            viewHolder.orderLocation = (TextView) cell.findViewById(R.id.unfold_order_location);
            profilePicture = (ImageView) cell.findViewById(R.id.profile_image);


            unfoldProfilePicture = (ImageView) cell.findViewById(R.id.unfold_profile_image);
            viewHolder.unfoldOrderTitle = (TextView) cell.findViewById(R.id.unfold_order_title);
            viewHolder.unfoldOrderDescription = (TextView) cell.findViewById(R.id.unfold_order_description);
            viewHolder.unfoldOrderPrice = (TextView) cell.findViewById(R.id.unfold_order_price);
            viewHolder.unfoldOrderCategory = (TextView) cell.findViewById(R.id.unfold_order_category);
            viewHolder.orderStateView = (ImageView) cell.findViewById(R.id.order_state);

            viewHolder.orderUserName = (TextView) cell.findViewById(R.id.order_user_name);
            viewHolder.orderUserNameUnfold = (TextView) cell.findViewById(R.id.unfold_order_user_name);

            viewHolder.ratingBar = (RatingBar) cell.findViewById(R.id.rating_bar);


            btnCancel = (Button) cell.findViewById(R.id.button_cancel_application);
            btnSubmitApplication = (Button) cell.findViewById(R.id.button_submit_application);

            image1 = (ImageView) cell.findViewById(R.id.order_picture_1);
            image2 = (ImageView) cell.findViewById(R.id.order_picture_2);
            image3 = (ImageView) cell.findViewById(R.id.order_picture_3);


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


        mOrderState = OrderState.getOrderState(mOrderId, mUserId);

        if (mOrderState != null) {
//            mOrderState = User.getOrderState(item.getId());
//            Log.d("ORDER STATES.", mOrderState);

            if (mOrderState.equals(OrderState.State.PENDING.toString())) {
                viewHolder.orderStateView.setImageResource(R.drawable.timer);

                btnCancel.setVisibility(View.VISIBLE);
                btnSubmitApplication.setVisibility(View.GONE);


            } else if (mOrderState.equals(OrderState.State.ACCEPTED.toString())) {
                viewHolder.orderStateView.setImageResource(R.drawable.checkbox_marked_circle);
                btnSubmitApplication.setVisibility(View.GONE);
            } else {
                viewHolder.orderStateView.setImageResource(R.drawable.declined_circle);
            }
        } else
            viewHolder.orderStateView.setVisibility(View.GONE);


        viewHolder.orderTitle.setText(item.getTitle());
        viewHolder.orderDescription.setText(item.getDescription());
        viewHolder.orderPrice.setText(item.getPrice() + "€");
        viewHolder.orderCategory.setText(item.getCategory());
        viewHolder.orderUserName.setText(item.getUserName());

        viewHolder.orderUserNameUnfold.setText(item.getUserName());


        viewHolder.unfoldOrderTitle.setText(item.getTitle());
        viewHolder.unfoldOrderDescription.setText(item.getDescription());
        viewHolder.unfoldOrderPrice.setText(item.getPrice() + "€");
        viewHolder.orderLocation.setText(item.getLocation());
        viewHolder.unfoldOrderCategory.setText(item.getCategory());


        // Paints the rating stars color
        Drawable progress = viewHolder.ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, ContextCompat.getColor(getContext(), R.color.colorPrimary));

        LinearLayout normalMode = (LinearLayout) cell.findViewById(R.id.normal_layout_mode_buttons);
        LinearLayout editMode = (LinearLayout) cell.findViewById(R.id.edit_layout_mode_buttons);

        if (isEditMode) {
            editMode.setVisibility(View.VISIBLE);
            normalMode.setVisibility(View.GONE);
        } else {
            editMode.setVisibility(View.GONE);
            normalMode.setVisibility(View.VISIBLE);
        }


        setListeners(position);


        return cell;
    }

    private void setListeners(final int position) {
        btnSubmitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(cell.getContext(), "OrderID: " + getItem(position).getId() + "User ID:" + LoginActivity.getUserId(), Toast.LENGTH_SHORT).show();
                OrderState.setOrderState(mOrderId, mUserId, OrderState.State.PENDING.toString());
                new UpdateRequestOrderStatus().execute(getItem(position).getId(), LoginActivity.getUserId());
                btnSubmitApplication.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CancelOrderApplication().execute(mOrderId, mUserId);
                OrderState.removeOrderState(mOrderId, mUserId);
            }
        });

        LikeButton btnLike = (LikeButton) cell.findViewById(R.id.star_button);
        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
//                if (mOrderState != null)
//                    Toast.makeText(getContext(), mOrderState, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), OrderState.orderStateMap.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle clicked cell state
                ((FoldingCell) v).toggle(false);
                // register in adapter that state for selected cell is toggled
                registerToggle(position);
            }
        });


        btnViewUsers = (Button) cell.findViewById(R.id.button_view_users);
        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UsersAcceptedActivity.class);

                Bundle b = new Bundle();
                b.putString("orderId", getItem(position).getId()); //Your id
                i.putExtras(b);
                getContext().startActivity(i);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserProfileActivity.class);
                getContext().startActivity(i);
            }
        });

        unfoldProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserProfileActivity.class);
                getContext().startActivity(i);
            }
        });


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EnlargedPictureActivity.class);
                Bundle b = new Bundle();
                b.putInt("image", R.drawable.order_image_1); //Your id
                i.putExtras(b);
                getContext().startActivity(i);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EnlargedPictureActivity.class);
                Bundle b = new Bundle();
                b.putInt("image", R.drawable.order_image_2); //Your id
                i.putExtras(b);
                getContext().startActivity(i);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EnlargedPictureActivity.class);
                Bundle b = new Bundle();
                b.putInt("image", R.drawable.order_image_3); //Your id
                i.putExtras(b);
                getContext().startActivity(i);
            }
        });

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

//    public View.OnClickListener getDefaultRequestBtnClickListener() {
//        return defaultRequestBtnClickListener;
//    }
//
//    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
//        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
//    }

    // View lookup cache
    private static class ViewHolder {
        TextView orderLocation;
        TextView orderTitle;
        TextView orderDescription;
        TextView orderPrice;
        TextView orderUserName;
        TextView orderUserNameUnfold;
        TextView orderCategory;
        TextView unfoldOrderTitle;
        TextView unfoldOrderDescription;
        RatingBar ratingBar;
        TextView unfoldOrderPrice;
        TextView unfoldOrderCategory;
        ImageView profilePicture;
        ImageView unfoldProfilePicture;
        ImageView orderStateView;
    }


//                @Override
//              public int getPosition(Order item) {
//                  return  POSITION_NONE;
//
//    }

    // Used to update the status of the order.
    private class UpdateRequestOrderStatus extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private final String LOGIN_URL = LoginActivity.LOCALHOST_URL + "/android/set_request_order.php";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Attempting login...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("orderID", args[0]);
                params.put("userID", args[1]);
                Log.d("Setting pending status", "with params:" + params);
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                if (json != null) {
                    Log.d("#SETTING JSON result", json.toString());
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            int success = 0;
            String message = "";
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
//                Toast.makeText(MainActivity.this, json.toString(),
//                        Toast.LENGTH_LONG).show();
                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
            cancel(true);
        }

    }

    // Used to update the status of the order.
    private class CancelOrderApplication extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private final String LOGIN_URL = LoginActivity.LOCALHOST_URL + "/android/cancel_order_application.php";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Attempting login...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("orderID", args[0]);
                params.put("userID", args[1]);
                Log.d("Setting pending status", "with params:" + params);
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                if (json != null) {
                    Log.d("#SETTING JSON result", json.toString());
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            int success = 0;
            String message = "";
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
            cancel(true);
        }

    }
}
