package com.mudra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mudra.model.LoginRequest;
import com.mudra.registration.Login;
import com.mudra.utils.Constant;
import com.mudra.utils.CustomPreference;
import com.mudra.utils.Util;

public class Splash extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hello
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(!TextUtils.isEmpty(CustomPreference.readString(Splash.this,CustomPreference.Client_id,"")))
                {
                    if (Util.getLoginData(Splash.this) != null) {
                        if (Constant.isNetConnected(Splash.this)) {

                            final ProgressDialog mProgressDialog = new ProgressDialog(Splash.this);
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setMessage("Loading...");
                            mProgressDialog.show();

                            Util.DashboardBrand(Splash.this, "dashboard", "", mProgressDialog);
                            //login();
                        } else {
                            Constant.ToastShort(Splash.this, getResources().getString(R.string.internet_connection));
                        }
                    } else {
                        Intent intent = new Intent(Splash.this, Login.class);
//                      intent.putExtra("dashboard","dashboard");
                        startActivity(intent);
                        finish();
                    }

                }

            }


        }, SPLASH_TIME_OUT);
    }

}
