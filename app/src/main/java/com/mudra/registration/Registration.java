package com.mudra.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mudra.R;
import com.mudra.Urls_Api.LoginApi;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.model.LoginRequest;
import com.mudra.utils.Constant;
import com.mudra.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SONY on 24-01-2018.
 */

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout registred;
    private EditText name, email, phn_no, passwrd, confirm_password;
    private ImageView emailVerify, phoneVerify;
    private String type;
    boolean emailFlag = false;
    boolean phoneFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        type = getIntent().getExtras().getString("type");

        inttialize_views();
    }

    private void inttialize_views() {
        registred = (LinearLayout) findViewById(R.id.registred);
        name = (EditText) findViewById(R.id.name);
        passwrd = (EditText) findViewById(R.id.passwrd);
        email = (EditText) findViewById(R.id.email);
        phn_no = (EditText) findViewById(R.id.phn_no);
        confirm_password = (EditText) findViewById(R.id.confirm_password);

        phoneVerify = (ImageView) findViewById(R.id.phoneVerify);
        emailVerify = (ImageView) findViewById(R.id.emailVerify);

        onClick();

    }

    private void onClick() {
        registred.setOnClickListener(this);
        phoneVerify.setOnClickListener(this);
        emailVerify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.registred:
                if (isValid()) {
                    if (Constant.isNetConnected(Registration.this)) {

                        final ProgressDialog mProgressDialog = new ProgressDialog(this);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.show();

                        LoginRequest customerListRequest = new LoginRequest();
                        customerListRequest.setEmail(email.getText().toString().trim());
                        customerListRequest.setPassword(passwrd.getText().toString().trim());
                        customerListRequest.setPhone(phn_no.getText().toString().trim());
                        customerListRequest.setName(name.getText().toString().trim());
                        customerListRequest.setDevice_id(Util.getDeviceId(this));
                        customerListRequest.setCust_type(type);
                        customerListRequest.setFlag("0");

                        Util.RegisterRequest(this, "registeruser", new Gson().toJson(customerListRequest).toString(), mProgressDialog);
                        //login();
                    } else {
                        Constant.ToastShort(Registration.this, getResources().getString(R.string.internet_connection));
                    }
                }
                break;

            case R.id.emailVerify:
                if (!TextUtils.isEmpty(email.getText().toString().trim())) {
                    if (Constant.isNetConnected(Registration.this)) {

                        final ProgressDialog mProgressDialog = new ProgressDialog(this);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.show();

                        LoginRequest customerListRequest = new LoginRequest();
                        customerListRequest.setEmail(email.getText().toString().trim());

                        Util.CheckSignup(Registration.this, "email", new Gson().toJson(customerListRequest).toString(), mProgressDialog);
                        //login();
                    } else {
                        Constant.ToastShort(Registration.this, getResources().getString(R.string.internet_connection));
                    }
                } else {
                    Constant.ToastShort(this, getString(R.string.entr_email));
                }
                break;

            case R.id.phoneVerify:
                if (!TextUtils.isEmpty(phn_no.getText().toString().trim())) {
                    if (Constant.isNetConnected(Registration.this)) {

                        final ProgressDialog mProgressDialog = new ProgressDialog(this);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.show();

                        LoginRequest customerListRequest = new LoginRequest();
                        customerListRequest.setPhone(phn_no.getText().toString().trim());

                        Util.CheckSignup(Registration.this, "phone", new Gson().toJson(customerListRequest).toString(), mProgressDialog);
                        //login();
                    } else {
                        Constant.ToastShort(Registration.this, getResources().getString(R.string.internet_connection));
                    }
                } else {
                    Constant.ToastShort(this, getString(R.string.entr_phone));
                }
                break;

            default:
                break;
        }

    }

    private boolean isValid() {

        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            Constant.ToastShort(this, getString(R.string.entr_user));
            return false;
        } else if (TextUtils.isEmpty(email.getText().toString().trim())) {
            Constant.ToastShort(this, getString(R.string.entr_email));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Constant.ToastShort(this, getString(R.string.valid_email));
            return false;
        } else if (TextUtils.isEmpty(passwrd.getText().toString().trim())) {
            Constant.ToastShort(this, getString(R.string.entr_pass));
            return false;
        } else if (TextUtils.isEmpty(confirm_password.getText().toString().trim())) {
            Constant.ToastShort(this, getString(R.string.entr_pass));
            return false;
        } else if (!confirm_password.getText().toString().trim().equalsIgnoreCase(passwrd.getText().toString().trim())) {
            Constant.ToastShort(this, getResources().getString(R.string.psswrd_mathces));
            return false;
        }

        if (emailFlag == false) {
            Constant.ToastShort(this, getResources().getString(R.string.email_verify));
            return false;
        }

        if (phoneFlag == false) {
            Constant.ToastShort(this, getResources().getString(R.string.phone_verify));
            return false;
        }
        return true;
    }


    private void login() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("name", name.getText().toString());
            params.put("password", passwrd.getText().toString());
            params.put("phone", phn_no.getText().toString());
            params.put("email", email.getText().toString());
//            params.put("device_id", FirebaseInstanceId.getInstance().getToken());


            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(Registration.this, params, Url_Links.Registration, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if (response.getJSONObject("result").getString("status").equalsIgnoreCase("yes")) {
                        Intent intent = new Intent(Registration.this, Home.class);
                        startActivity(intent);
                        finish();
                        JSONObject obj = response.getJSONObject("result");
//                        if(response.getJSONObject("result").getString("is_active").equalsIgnoreCase("active"))
//                        {
//                            CustomPreference.writeString(getActivity(),CustomPreference.Client_id,CustomPreference.client_id);
//                            CustomPreference.writeString(getActivity(),CustomPreference.Client_name,obj.getString("name"));
//                            CustomPreference.writeString(getActivity(),CustomPreference.Refferal_Code,obj.getString("referral"));
//                            Intent intent = new Intent(getActivity(), WalkThrough_Activity.class);
//                            startActivity(intent);
//                            getActivity().finish();
//                            Constant.ToastShort(getActivity(), obj.getString("msg"));
////                          register_tofirebase(obj.getString("id"),obj.getString("name"),obj.getString("msg"));
//                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Registration.this, error.getMessage());
            }
        };
    }

    public void CheckValid(String from, String status) {
        if (from.equalsIgnoreCase("email") && status.contains("200")) {
            emailVerify.setBackgroundResource(R.drawable.ic_check_circle_icon);
            emailFlag = true;
            Log.e("coming", "true");
        } else {
            emailVerify.setBackgroundResource(R.drawable.ic_info_icon);
            emailFlag = false;
            Log.e("coming", "false");
        }

        if (from.equalsIgnoreCase("phone") && status.contains("200")) {
            phoneVerify.setBackgroundResource(R.drawable.ic_check_circle_icon);
            phoneFlag = true;
        } else {
            phoneVerify.setBackgroundResource(R.drawable.ic_info_icon);
            phoneFlag = false;
        }
    }
}
