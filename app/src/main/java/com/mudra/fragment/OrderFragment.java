package com.mudra.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mudra.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 23-02-2018.
 */

public class OrderFragment extends Fragment {

    private OrderFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    ArrayList<String> titleString = new ArrayList<>();
    String response;

    public static OrderFragment newInstance(String data) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_fragment, container, false);

        response = getArguments().getString("data");

        titleString.add("View Recent Order");
        titleString.add("View All Order");

        mViewPager = (ViewPager) v.findViewById(R.id.containerpager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);

        mSectionsPagerAdapter = new OrderFragment.SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        return v;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0)
                return Order.newInstance(position, response);
            else
                return Order.newInstance(position, response);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {

            return titleString.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleString.get(position);
        }
    }

}