package com.mudra.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mudra.R;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.model.DashboardObject;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15-02-2018.
 */

public class DashboarAdapter extends RecyclerView.Adapter<DashboarAdapter.MyViewHolder> {

    private ArrayList<DashboardObject> categoryItem;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public DashboarAdapter(ArrayList<DashboardObject> categoryItems, Context mContext) {
        this.categoryItem = categoryItems;
        this.mContext = mContext;
    }

    @Override
    public DashboarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_adapter, parent, false);

        return new DashboarAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DashboarAdapter.MyViewHolder holder, final int position) {
        final DashboardObject categoryItemTemp = categoryItem.get(position);

        Glide.with(mContext).load(Url_Links.Image_Url_brand + categoryItemTemp.getLogo()).error(R.drawable.login_logo)
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Item Clicked", Toast.LENGTH_SHORT).show();
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

}