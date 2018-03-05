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
import android.widget.Toast;

import com.google.gson.Gson;
import com.mudra.Adapter.OrderListAdapter;
import com.mudra.R;
import com.mudra.model.OrderListResponse;
import com.mudra.model.OrderRequest;

import java.util.ArrayList;

public class Order extends Fragment {

    String response;
    RecyclerView recycler_view;
    TextView noData;
    LinearLayout update;

    OrderListAdapter mAdapter;
    ArrayList<OrderRequest> events = new ArrayList<OrderRequest>();

    public static Order newInstance(int position, String data) {
        Order fragment = new Order();
        Bundle args = new Bundle();
        args.putString("response", data);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_list, container, false);

        recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);
        noData = (TextView) v.findViewById(R.id.noData);
        update = (LinearLayout) v.findViewById(R.id.update);
        update.setVisibility(View.GONE);

        String response = getArguments().getString("response");
        int position = getArguments().getInt("position");

        OrderListResponse dashboard = new Gson().fromJson(response, OrderListResponse.class);
        ArrayList<OrderRequest> list = dashboard.getList();

        if (list != null && list.size() > 0) {
            for (OrderRequest dashboardObject : list) {
                if (position == 0) {
                    if (dashboardObject.getStatus().equalsIgnoreCase("1"))
                        events.add(dashboardObject);
                } else {
                    if (!dashboardObject.getStatus().equalsIgnoreCase("1"))
                        events.add(dashboardObject);
                }
            }
        } else {
            events = new ArrayList<>();
            Toast.makeText(getActivity(), "No data Found!!", Toast.LENGTH_SHORT).show();
        }

        if (events != null && events.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);

            mAdapter = new OrderListAdapter(events, getActivity(), "search");
            recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            //recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.getItemAnimator().setChangeDuration(0);
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }

        return v;
    }
}