package com.mudra.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mudra.R;
import com.mudra.custom_fonts.TypefaceTextView;

/**
 * Created by Lenovo on 16-02-2018.
 */

public class CustomerType extends AppCompatActivity implements View.OnClickListener {

    TypefaceTextView mr, doctor, hospital, wholeSale;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_type);

        mr = (TypefaceTextView) findViewById(R.id.mr);
        doctor = (TypefaceTextView) findViewById(R.id.doctor);
        hospital = (TypefaceTextView) findViewById(R.id.hospital);
        wholeSale = (TypefaceTextView) findViewById(R.id.wholeSale);

        mr.setOnClickListener(this);
        doctor.setOnClickListener(this);
        hospital.setOnClickListener(this);
        wholeSale.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mr) {
            Register("MR");
        }
        if (v == doctor) {
            Register("Doctor");
        }
        if (v == hospital) {
            Register("Hospital");
        }
        if (v == wholeSale) {
            Register("Whole Sale Dealer");
        }
    }

    public void Register(String type) {

        Intent registerIntent = new Intent(CustomerType.this, Registration.class);
        registerIntent.putExtra("type", type);
        startActivity(registerIntent);
    }
}
