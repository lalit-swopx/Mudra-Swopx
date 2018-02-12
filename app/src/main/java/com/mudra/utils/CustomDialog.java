package com.mudra.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mudra.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


/**
 * Created by apple on 7/18/16.
 */
public class CustomDialog
{

   /* public Activity c;
    public Dialog d;
    public TextView yes, no;
    private TextView title,header;
    private String key;
    public CustomDialog(Activity a, String key) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.key=key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.hideSoftKeyboard(c);
       getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Constant.Sop("Custom Dialog=========");
        setContentView(R.layout.commom_custom_dialog);
        yes = (TextView) findViewById(R.id.btn_yes);
        no = (TextView) findViewById(R.id.btn_no);
        header=(TextView)findViewById(R.id.header);
        title=(TextView)findViewById(R.id.title);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        if(key.equalsIgnoreCase("logout"))
        {
            no.setVisibility(View.VISIBLE);
            yes.setVisibility(View.VISIBLE);
            title.setText(c.getResources().getString(R.string.str_logout));
            header.setText(c.getResources().getString(R.string.str_logout_questn));
        }
        else
        {
            no.setVisibility(View.VISIBLE);
            yes.setVisibility(View.GONE);
            title.setText(c.getResources().getString(R.string.coming_soon));
            header.setText(c.getResources().getString(R.string.this_feature_is_coming_soon));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                if(key.equalsIgnoreCase("logout"))
                {
                    if(Constant.isNetConnected(c))
                    {

                        CustomPreference.writeString(c,CustomPreference.Client_id,"");
                        CustomPreference.writeString(c, CustomPreference.Client_name, "");
                        CustomPreference.writeString(c, CustomPreference.Refferal_Code, "");
                        dismiss();
                        Intent i=new Intent(c, GetStarted_Screen.class);
                        c.startActivity(i);
                        c.finish();

//                        Web_Service(Url_Links.Logout);

                    }
                    else
                    {
                        Constant.ToastShort(c,c.getResources().getString(R.string.internet_connection));
                    }
                }
                else
                {

                }


                break;
            case R.id.btn_no:
                if(key.equalsIgnoreCase("logout"))
                {
                    dismiss();
                }
                    else
                {
                    c.finish();
                }

                break;
            default:
                break;
        }

    }

    public void Web_Service(String forget_url) {
        String url=null;
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            {
//                params.put("auth_token",CustomPreference.readString(c, CustomPreference.AUTH_TOKEN, ""));
//                params.put("client_id", CustomPreference.readString(c, CustomPreference.Client_Id, ""));
                body.put("studyroom", params);
                url=forget_url;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(c, body, url,true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    JSONObject data=response.getJSONObject("studyroom");
                    if(data.getString("res_code").equalsIgnoreCase("1"))
                    {
                        Constant.ToastShort(c, data.getString("res_msg"));
                        Constant.hideSoftKeyboard(c);
                        dismiss();

                    *//*    new DBHelper(c).truncateData(DBHelper.TABLE_SUBJECT);
                        new DBHelper(c).truncateData(DBHelper.TABLE_INSITUTION);
                        new DBHelper(c).truncateData(DBHelper.TABLE_EMPLOYER);
                        new DBHelper(c).truncateData(DBHelper.TABLE_JOB_PROFILE);
                        new DBHelper(c).truncateData(DBHelper.TABLE_DESIGNATION);
                        new DBHelper(c).truncateData(DBHelper.TABLE_NAME_STATE);
                        new DBHelper(c).truncateData(DBHelper.TABLE_NAME_CURRENT_STATE);
                        new DBHelper(c).truncateData(DBHelper.TABLE_NAME_CURRENT_CITY);
                        new DBHelper(c).truncateData(DBHelper.TABLE_NAME_CITY);
                        new DBHelper(c).truncateData(DBHelper.TABLE_Courses);
                        new DBHelper(c).truncateData(DBHelper.TABLE_Domain);*//*

                     *//*   CustomPreference.writeString(c,CustomPreference.Client_id,"");
                        CustomPreference.writeString(c, CustomPreference.U_UID, "");
                        CustomPreference.writeString(c, CustomPreference.U_NAME, "");*//*

                        Intent i=new Intent(c, GetStarted_Screen.class);
                        c.startActivity(i);
                        c.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {
                super.error(error);
            }
        };
    }

    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }*/
}
