package com.mudra.Urls_Api;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by apple on 7/16/16.
 */
public class LoginApi extends Api_CLass {

    public LoginApi(Context c, JSONObject obj, String login_url, boolean loader)
    {
     postJsonApi(c,login_url,obj,loader);
    }

    public LoginApi(Context c,String countries_url,boolean loader) {

        getJsonApi(c,countries_url,new JSONObject(),loader);
    }


    @Override
    public void response(JSONObject response) {
    }

    @Override
    public void error(VolleyError error)
    {

    }


}
