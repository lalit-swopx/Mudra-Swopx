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
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mudra.R;
import com.mudra.Splash;
import com.mudra.Urls_Api.LoginApi;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SONY on 24-01-2018.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView forgot_passwrd,registered_txt;
    private LinearLayout login;
    private EditText name,passwrd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inttialize_views();
    }

    private void inttialize_views() {

        forgot_passwrd=(TextView)findViewById(R.id.forgot_passwrd);
        registered_txt=(TextView)findViewById(R.id.registered_txt);
        login=(LinearLayout)findViewById(R.id.login);
        name=(EditText)findViewById(R.id.name);
        passwrd=(EditText)findViewById(R.id.passwrd);
        onClick();

    }

    private void onClick() {
        login.setOnClickListener(this);
        forgot_passwrd.setOnClickListener(this);
        SpannableString ss = new SpannableString(getResources().getString(R.string.not_registered_yet_sign_up));
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.colorBlue));
            }
        };
        ss.setSpan(clickableSpan1, 20, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        registered_txt.setText(ss);
        registered_txt.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.login:
                if(isValid())
                {
                    if(Constant.isNetConnected(Login.this))
                    {
                        login();
                    }
                    else
                    {
                        Constant.ToastShort(Login.this,getResources().getString(R.string.internet_connection));
                    }
                }
                break;

            case R.id.forgot_passwrd:
                break;

                default: break;
        }

    }

    private void login() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("email", name.getText().toString());
            params.put("password", passwrd.getText().toString());
            params.put("device_id", "5");



            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(Login.this, params, Url_Links.Login, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("yes"))
                    {
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                        finish();
//                        JSONObject obj=response.getJSONObject("result");
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
                Constant.ToastShort(Login.this, error.getMessage());
            }
        };
    }


    private boolean isValid() {

        if(TextUtils.isEmpty(name.getText().toString().trim()))
        {
            Constant.ToastShort(this,getString(R.string.entr_user));
            return  false;
        }
       /* else   if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            Constant.ToastShort(getActivity(),getString(R.string.enter_valid_email));
            return  false;
        }*/
        else   if(TextUtils.isEmpty(passwrd.getText().toString().trim()))
        {
            Constant.ToastShort(this,getString(R.string.entr_pass));
            return  false;
        }
        return true;
    }
}
