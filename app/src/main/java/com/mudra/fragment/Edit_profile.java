package com.mudra.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mudra.R;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.custom_fonts.TypefaceEditText;
import com.mudra.model.LoginRequest;
import com.mudra.model.LoginResponse;
import com.mudra.utils.Constant;
import com.mudra.utils.Util;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_profile extends Fragment {
    CircleImageView profile_image;
    TypefaceEditText name, phone, email, add;
    LinearLayout update;

    public static Edit_profile newInstance(String data) {
        Edit_profile fragment = new Edit_profile();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        profile_image = (CircleImageView) v.findViewById(R.id.profile_image);

        name = (TypefaceEditText) v.findViewById(R.id.name);
        phone = (TypefaceEditText) v.findViewById(R.id.phone);
        email = (TypefaceEditText) v.findViewById(R.id.email);
        add = (TypefaceEditText) v.findViewById(R.id.add);

        update = (LinearLayout) v.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetConnected(getActivity())) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();

                    LoginResponse loginResponse = new Gson().fromJson(Util.getLoginData(getActivity()), LoginResponse.class);

                    LoginRequest customerListRequest = new LoginRequest();
                    customerListRequest.setEmail(email.getText().toString().trim());
                    customerListRequest.setPhone(phone.getText().toString().trim());
                    customerListRequest.setName(name.getText().toString().trim());
                    customerListRequest.setId(loginResponse.getClient_detail().getId());
                    customerListRequest.setAddress(add.getText().toString().trim());
                    customerListRequest.setFlag("0");
                    customerListRequest.setDevice_id(Util.getDeviceId(getActivity()));
                    customerListRequest.setPassword(loginResponse.getClient_detail().getPassword());

                    Util.UpdateProfile(getActivity(), "update-profile", new Gson().toJson(customerListRequest).toString(), mProgressDialog);
                    //login();
                } else {
                    Constant.ToastShort(getActivity(), getResources().getString(R.string.internet_connection));
                }
            }
        });

        String loginData = Util.getLoginData(getActivity());
        LoginResponse loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

        if (loginData != null && loginResponse.getClient_detail() != null) {
            if (loginResponse.getClient_detail().getName() != null)
                name.setText(loginResponse.getClient_detail().getName());
            if (loginResponse.getClient_detail().getPhone() != null)
                phone.setText(loginResponse.getClient_detail().getPhone());
            if (loginResponse.getClient_detail().getEmail() != null)
                email.setText(loginResponse.getClient_detail().getEmail());
            if (loginResponse.getClient_detail().getAddress() != null)
                add.setText(loginResponse.getClient_detail().getAddress());
        }

        Glide.with(this).load(Url_Links.Image_Base_Url).error(R.drawable.login_logo)
                .into(profile_image);

        return v;
    }
}
