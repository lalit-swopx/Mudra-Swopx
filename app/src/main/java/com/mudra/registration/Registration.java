package com.mudra.registration;

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
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mudra.R;
import com.mudra.Urls_Api.LoginApi;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SONY on 24-01-2018.
 */

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout registred;
    private EditText name,email,phn_no,passwrd,confirm_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        inttialize_views();
    }

    private void inttialize_views() {
        registred=(LinearLayout)findViewById(R.id.login);
        name=(EditText)findViewById(R.id.name);
        passwrd=(EditText)findViewById(R.id.passwrd);
        email=(EditText)findViewById(R.id.email);
        phn_no=(EditText)findViewById(R.id.phn_no);
        confirm_password=(EditText)findViewById(R.id.confirm_password);
        onClick();

    }

    private void onClick() {
        registred.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.registred:
                if(isValid())
                {
                    if(Constant.isNetConnected(Registration.this))
                    {
                        login();
                    }
                    else
                    {
                        Constant.ToastShort(Registration.this,getResources().getString(R.string.internet_connection));
                    }
                }
                break;

            default: break;
        }

    }

    private boolean isValid() {

        if(TextUtils.isEmpty(name.getText().toString().trim()))
        {
            Constant.ToastShort(this,getString(R.string.entr_user));
            return  false;
        }

        else  if(TextUtils.isEmpty(email.getText().toString().trim()))
        {
            Constant.ToastShort(this,getString(R.string.entr_email));
            return  false;
        }
        else   if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            Constant.ToastShort(this,getString(R.string.valid_email));
            return  false;
        }
        else   if(TextUtils.isEmpty(passwrd.getText().toString().trim()))
        {
            Constant.ToastShort(this,getString(R.string.entr_pass));
            return  false;
        }
        else   if(TextUtils.isEmpty(confirm_password.getText().toString().trim()))
        {
            Constant.ToastShort(this,getString(R.string.entr_pass));
            return  false;
        }
        else if(!confirm_password.getText().toString().trim().equalsIgnoreCase(passwrd.getText().toString().trim()))
        {
            Constant.ToastShort(this,getResources().getString(R.string.psswrd_mathces));
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
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("yes"))
                    {
                        Intent intent = new Intent(Registration.this, Home.class);
                        startActivity(intent);
                        finish();
                        JSONObject obj=response.getJSONObject("result");
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
}
