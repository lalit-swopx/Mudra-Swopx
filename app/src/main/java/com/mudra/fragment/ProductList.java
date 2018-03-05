package com.mudra.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mudra.Adapter.ProductListAdapter;
import com.mudra.R;
import com.mudra.model.ProductListObject;
import com.mudra.model.ProductListResponse;
import com.mudra.registration.Home;
import com.mudra.utils.FragmentActivityMessage;
import com.mudra.utils.GlobalBus;
import com.mudra.utils.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15-02-2018.
 */

public class ProductList extends Fragment {

    String response;
    RecyclerView recycler_view;
    TextView noData;
    LinearLayout update, cartContinue;

    ProductListAdapter mAdapter;
    ArrayList<ProductListObject> events = new ArrayList<ProductListObject>();
    ArrayList<ProductListObject> eventsTemp = new ArrayList<ProductListObject>();

    public static ProductList newInstance(int position, boolean flag) {
        ProductList fragment = new ProductList();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putBoolean("flag", flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_list, container, false);

        recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);
        noData = (TextView) v.findViewById(R.id.noData);

        cartContinue = (LinearLayout) v.findViewById(R.id.cartContinue);
        update = (LinearLayout) v.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).Cart();
            }
        });
        cartContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).Cart();
            }
        });

        if (getArguments().getBoolean("flag")) {
            String response = Util.getProduct(getActivity());
            ProductListResponse dashboard = new Gson().fromJson(response, ProductListResponse.class);
            ArrayList<ProductListObject> list = dashboard.getList();

            events = new ArrayList<ProductListObject>();

            FragmentActivityMessage activityActivityMessage =
                    new FragmentActivityMessage("", "cartupdate");
            GlobalBus.getBus().post(activityActivityMessage);

            if (list != null)
                for (ProductListObject dashboardObject : list) {
                    events.add(dashboardObject);
                }

            if (events != null && events.size() > 0) {
                noData.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);

                mAdapter = new ProductListAdapter(events, getActivity(), "productlist");
                recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                //recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.getItemAnimator().setChangeDuration(0);
                recycler_view.setAdapter(mAdapter);

            } else {
                noData.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.GONE);
            }
        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }

        return v;
    }

    public void CartSize() {
        String responseTemp = Util.getCartItem(getActivity());
        eventsTemp = new ArrayList<ProductListObject>();
        if (responseTemp != null && responseTemp.length() > 0) {
            ProductListResponse dashboard = new Gson().fromJson(responseTemp, ProductListResponse.class);
            ArrayList<ProductListObject> list = dashboard.getList();

            for (ProductListObject dashboardObject : list) {
                eventsTemp.add(dashboardObject);
            }
        }

        if (eventsTemp.size() > 0) {
            cartContinue.setVisibility(View.VISIBLE);
        } else {
            cartContinue.setVisibility(View.GONE);
        }

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("cartupdate")) {
            CartSize();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }
}