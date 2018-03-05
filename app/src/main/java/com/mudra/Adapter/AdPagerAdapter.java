package com.mudra.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mudra.R;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.model.AdvertiseObject;

import java.util.ArrayList;

/**
 * Created by Lenovo on 24-02-2018.
 */

public class AdPagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<AdvertiseObject> objects;
    LayoutInflater mLayoutInflater;

    public AdPagerAdapter(Context context, ArrayList<AdvertiseObject> objects) {
        this.mContext = context;
        this.objects = objects;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.ad_adapter, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        Glide.with(mContext).load(Url_Links.Image_Url_adv + objects.get(position).getBanner())
                .error(R.drawable.imagenotavailable)
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}