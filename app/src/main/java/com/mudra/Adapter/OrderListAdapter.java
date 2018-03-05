package com.mudra.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mudra.R;
import com.mudra.custom_fonts.TypefaceTextView;
import com.mudra.model.OrderRequest;
import com.mudra.model.OrderSubModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 21-02-2018.
 */

/**
 * Created by Lenovo on 21-02-2018.
 */

public class OrderListAdapter extends RecyclerView.Adapter<com.mudra.Adapter.OrderListAdapter.MyViewHolder> {

    private ArrayList<OrderRequest> categoryItem;
    private Context mContext;
    private String from;
    ArrayList<OrderSubModel> orderSubModels = new ArrayList<>();
    OrderListSubAdapter mAdapter;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        TypefaceTextView date, ordernumber, quantity, status;
        RecyclerView recycler_view;
        TextView noData;

        public MyViewHolder(View view) {
            super(view);

            date = (TypefaceTextView) view.findViewById(R.id.date);
            ordernumber = (TypefaceTextView) view.findViewById(R.id.ordernumber);
            quantity = (TypefaceTextView) view.findViewById(R.id.quantity);
            status = (TypefaceTextView) view.findViewById(R.id.status);
            card_view = (CardView) view.findViewById(R.id.card_view);

            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            noData = (TextView) view.findViewById(R.id.noData);
        }
    }

    public OrderListAdapter(ArrayList<OrderRequest> categoryItems, Context mContext, String from) {
        this.categoryItem = categoryItems;
        this.mContext = mContext;
        this.from = from;
    }

    @Override
    public com.mudra.Adapter.OrderListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_adapter, parent, false);

        return new com.mudra.Adapter.OrderListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.mudra.Adapter.OrderListAdapter.MyViewHolder holder, final int position) {
        final OrderRequest categoryItemTemp = categoryItem.get(position);

        String[] list = categoryItemTemp.getOrder_list().split(",");
        String[] quant = categoryItemTemp.getQuantity().split(",");

        if (categoryItemTemp.getDate() != null)
            holder.quantity.setText("QTY : " + list.length);
        else
            holder.quantity.setText("QTY : " + "");

        if (categoryItemTemp.getId() != null)
            holder.ordernumber.setText("Order Id No : " + categoryItemTemp.getId());
        else
            holder.ordernumber.setText("Order Id No : " + "");

        if (categoryItemTemp.getDate() != null) {
            if (categoryItemTemp.getStatus().equalsIgnoreCase("1"))
                holder.status.setText("Status : " + "New");
            else if (categoryItemTemp.getStatus().equalsIgnoreCase("2"))
                holder.status.setText("Status : " + "On-going");
            else if (categoryItemTemp.getStatus().equalsIgnoreCase("3"))
                holder.status.setText("Status : " + "Complete");
            else
                holder.status.setText("Status : " + "Cancel");
        } else {
            holder.status.setText("Status : " + "");
        }

        if (categoryItemTemp.getDate() != null)
            holder.date.setText("" + categoryItemTemp.getDate());
        else
            holder.date.setText("Status : " + "");

        orderSubModels = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            OrderSubModel orderSubModel = new OrderSubModel();
            orderSubModel.setName(list[i]);
            orderSubModel.setCount(quant[i]);

            orderSubModels.add(orderSubModel);
        }

        if (orderSubModels != null && orderSubModels.size() > 0) {
            holder.noData.setVisibility(View.GONE);
            holder.recycler_view.setVisibility(View.VISIBLE);

            mAdapter = new OrderListSubAdapter(orderSubModels, mContext, "search");
            holder.recycler_view.setLayoutManager(new GridLayoutManager(mContext, 1));
            //recycler_view.setItemAnimator(new DefaultItemAnimator());
            holder.recycler_view.getItemAnimator().setChangeDuration(0);
            holder.recycler_view.setAdapter(mAdapter);

        } else {
            holder.noData.setVisibility(View.VISIBLE);
            holder.recycler_view.setVisibility(View.GONE);
        }

        Log.e("here", "selected : " + new Gson().toJson(orderSubModels).toString());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(orderSubModels, categoryItemTemp.getId(), categoryItemTemp.getDate());
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryItem.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public void dialog(ArrayList<OrderSubModel> orderSubModels, String orderNo, String orderDate) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.order_detail_layout, null);

        final Dialog dialog = new Dialog(mContext);
        TypefaceTextView ordernumber, date;
        LinearLayout deliver;
        RecyclerView recycler_view;
        TextView noData;

        ordernumber = (TypefaceTextView) view.findViewById(R.id.ordernumber);
        date = (TypefaceTextView) view.findViewById(R.id.date);
        deliver = (LinearLayout) view.findViewById(R.id.deliver);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        noData = (TextView) view.findViewById(R.id.noData);

        dialog.setTitle("OTP Verification");
        dialog.setCancelable(false);
        dialog.setContentView(view);

        if (orderNo != null)
            ordernumber.setText("Order Id : #" + orderNo);
        if (orderDate != null)
            date.setText(orderDate);

        if (orderSubModels != null && orderSubModels.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);

            mAdapter = new OrderListSubAdapter(orderSubModels, mContext, "search");
            recycler_view.setLayoutManager(new GridLayoutManager(mContext, 1));
            //recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.getItemAnimator().setChangeDuration(0);
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
