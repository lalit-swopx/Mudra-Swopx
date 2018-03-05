package com.mudra.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mudra.Adapter.AdPagerAdapter;
import com.mudra.Adapter.DashboarAdapter;
import com.mudra.R;
import com.mudra.custom_fonts.TypefaceEditText;
import com.mudra.custom_fonts.TypefaceTextView;
import com.mudra.model.AdvertiseObject;
import com.mudra.model.DashboardObject;
import com.mudra.model.DashboardResponse;
import com.mudra.registration.Home;
import com.mudra.utils.Util;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lenovo on 15-02-2018.
 */

public class dashboard extends Fragment {
    CircleImageView profile_image;
    TypefaceEditText name, phone, email, add;
    TypefaceTextView viewMoreBrand;
    RecyclerView recycler_view;
    TextView noData;

    DashboarAdapter mAdapter;
    ArrayList<DashboardObject> events = new ArrayList<DashboardObject>();
    ArrayList<AdvertiseObject> ad;

    ViewPager viewPager;
    Activity mActivity;

    public static dashboard newInstance(String data) {
        dashboard fragment = new dashboard();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_fragment, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.view_pager);

        mActivity = getActivity();
        recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);
        noData = (TextView) v.findViewById(R.id.noData);
        viewMoreBrand = (TypefaceTextView) v.findViewById(R.id.viewMoreBrand);
        viewMoreBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "This feature will live soon!! Stay tuned !! ", Toast.LENGTH_SHORT).show();
            }
        });
        String response = Util.getDashboardBrand(getActivity());
        DashboardResponse dashboard = new Gson().fromJson(response, DashboardResponse.class);
        ArrayList<DashboardObject> list = dashboard.getList();
        ad = dashboard.getAd();

        AdPagerAdapter mCustomPagerAdapter = new AdPagerAdapter(getActivity(), ad);
        viewPager.setAdapter(mCustomPagerAdapter);

        events = new ArrayList<DashboardObject>();
        if (list != null && list.size() > 0)
            for (DashboardObject dashboardObject : list) {
                events.add(dashboardObject);
            }

        if (events != null && events.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);

            mAdapter = new DashboarAdapter(events, getActivity());
            recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            //recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.getItemAnimator().setChangeDuration(0);
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.addItem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).GetSearchData();
            }
        });


        ViewPager.OnPageChangeListener vOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.setOnPageChangeListener(vOnPageChangeListener);
        Timer swipeTimer = new Timer();
        try {
            swipeTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            todoLoop(ad);

                        }
                    });
                }
            }, 500, 3000);

        } catch (Exception e) {
        }


        return v;
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public void todoLoop(ArrayList<AdvertiseObject> ad) {
        int current = getItem(+1);

        if (current < ad.size()) {
            // move to next screen
            viewPager.setCurrentItem(current);

        } else if (current == ad.size()) {
            current = 0;
            viewPager.setCurrentItem(current);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}