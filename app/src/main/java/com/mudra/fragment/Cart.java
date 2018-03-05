package com.mudra.fragment;

import android.app.ProgressDialog;
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
import com.mudra.custom_fonts.TypefaceTextView;
import com.mudra.model.LoginResponse;
import com.mudra.model.OrderRequest;
import com.mudra.model.ProductListObject;
import com.mudra.model.ProductListResponse;
import com.mudra.registration.Home;
import com.mudra.utils.Constant;
import com.mudra.utils.Util;

import java.util.ArrayList;

public class Cart extends Fragment {

    String response;
    RecyclerView recycler_view;
    TextView noData;
    LinearLayout update, confirm;
    TypefaceTextView continueButton, submitButton;

    ProductListAdapter mAdapter;
    ArrayList<ProductListObject> events = new ArrayList<ProductListObject>();

    public static Search newInstance(String data) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString("position", data);
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
        confirm = (LinearLayout) v.findViewById(R.id.confirm);
        continueButton = (TypefaceTextView) v.findViewById(R.id.continueButton);
        submitButton = (TypefaceTextView) v.findViewById(R.id.submitButton);

        update.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.VISIBLE);

        String response = Util.getCartItem(getActivity());

        events = new ArrayList<ProductListObject>();

        if (response != null && response.length() > 0) {
            ProductListResponse dashboard = new Gson().fromJson(response, ProductListResponse.class);
            ArrayList<ProductListObject> list = dashboard.getList();

            for (ProductListObject dashboardObject : list) {
                events.add(dashboardObject);
            }
        }

        if (events != null && events.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);

            mAdapter = new ProductListAdapter(events, getActivity(), "cart");
            recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            //recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.getItemAnimator().setChangeDuration(0);
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetConnected(getActivity())) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();

                    Util.ProductList(getActivity(), "product-list" + "/" + "", "", mProgressDialog, "search");
                } else {
                    Constant.ToastShort(getActivity(), getResources().getString(R.string.internet_connection));
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String orderList = "";
                String quantity = "";
                String images = "";

                String response = Util.getCartItem(getActivity());

                if (response != null && response.length() > 0) {
                    ProductListResponse dashboard = new Gson().fromJson(response, ProductListResponse.class);
                    ArrayList<ProductListObject> list = dashboard.getList();

                    for (ProductListObject dashboardObject : list) {
                        if (list.size() == 1) {
                            orderList = orderList + dashboardObject.getName();
                            quantity = quantity + dashboardObject.getCount();
                            images = images + dashboardObject.getImage();
                        } else {
                            orderList = dashboardObject.getName() + "," + orderList;
                            quantity = dashboardObject.getCount() + "," + quantity;
                            images = dashboardObject.getImage() + "," + images;
                        }
                    }
                }

                if (Constant.isNetConnected(getActivity())) {

                    if (orderList.length() > 0 && quantity.length() > 0) {
                        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.show();

                        String loginData = Util.getLoginData(getActivity());
                        LoginResponse loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

                        OrderRequest customerListRequest = new OrderRequest();
                        customerListRequest.setClientId(loginResponse.getClient_detail().getId());
                        customerListRequest.setOrder_list(orderList);
                        customerListRequest.setQuantity(quantity);
                        customerListRequest.setOrder_image(images);

                        Util.OrderRequest(getActivity(), "new-order", new Gson().toJson(customerListRequest).toString(), mProgressDialog);
                        //login();
                    } else {
                        Constant.ToastShort(getActivity(), "Please select product!!");
                    }
                } else {
                    Constant.ToastShort(getActivity(), getResources().getString(R.string.internet_connection));
                }
            }
        });

        return v;
    }
}