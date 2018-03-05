package com.mudra.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mudra.R;
import com.mudra.custom_fonts.TypefaceTextView;
import com.mudra.model.OrderSubModel;

import java.util.ArrayList;

/**
 * Created by Lenovo on 21-02-2018.
 */

public class OrderListSubAdapter extends RecyclerView.Adapter<OrderListSubAdapter.MyViewHolder> {

    private ArrayList<OrderSubModel> categoryItem;
    private Context mContext;
    private String from;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TypefaceTextView count, name;

        public MyViewHolder(View view) {
            super(view);

            count = (TypefaceTextView) view.findViewById(R.id.count);
            name = (TypefaceTextView) view.findViewById(R.id.name);
        }
    }

    public OrderListSubAdapter(ArrayList<OrderSubModel> categoryItems, Context mContext, String from) {
        this.categoryItem = categoryItems;
        this.mContext = mContext;
        this.from = from;
    }

    @Override
    public OrderListSubAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_sublist_adapter, parent, false);

        return new OrderListSubAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderListSubAdapter.MyViewHolder holder, final int position) {
        final OrderSubModel categoryItemTemp = categoryItem.get(position);

        if (categoryItemTemp.getCount() != null)
            holder.count.setText("" + categoryItemTemp.getCount());
        if (categoryItemTemp.getName() != null)
            holder.name.setText("" + categoryItemTemp.getName());

    }

    @Override
    public int getItemCount() {
        return categoryItem.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}