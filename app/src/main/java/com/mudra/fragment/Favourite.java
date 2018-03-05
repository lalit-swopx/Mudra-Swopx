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

public class Favourite extends Fragment {

    private Favourite.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    ArrayList<String> titleString = new ArrayList<>();
    String response;

    public static Favourite newInstance(String data) {
        Favourite fragment = new Favourite();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourite_fragment, container, false);

        response = getArguments().getString("data");

        titleString.add("Favourite");
        titleString.add("All Product");

        mViewPager = (ViewPager) v.findViewById(R.id.containerpager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);

        mSectionsPagerAdapter = new Favourite.SectionsPagerAdapter(getChildFragmentManager());
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
                return ProductList.newInstance(position, false);
            else
                return ProductList.newInstance(position, true);
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