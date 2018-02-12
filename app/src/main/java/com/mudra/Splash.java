package com.mudra;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.mudra.registration.Login;
import com.mudra.utils.CustomPreference;

public class Splash extends AppCompatActivity {

    private int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
//                if(!TextUtils.isEmpty(CustomPreference.readString(Splash.this,CustomPreference.Client_id,"")))
                {
                    Intent intent = new Intent(Splash.this, Login.class);
//                    intent.putExtra("dashboard","dashboard");
                    startActivity(intent);
                    finish();
                }
             /*   else
                {
                    Intent intent = new Intent(Splash.this, GetStarted_Screen.class);
                    intent.putExtra("dashboard","dashboard");
                    startActivity(intent);
                    finish();
                }*/


            }


        },SPLASH_TIME_OUT);
    }

}
