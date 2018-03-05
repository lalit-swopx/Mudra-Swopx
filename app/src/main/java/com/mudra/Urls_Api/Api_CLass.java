package com.mudra.Urls_Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mudra.app.AppController;
import com.mudra.utils.Constant;


import org.json.JSONObject;


/**
 * Created by apple on 7/16/16.
 */
public abstract class Api_CLass {

    CustomLoader customLoader;

    public abstract void response(JSONObject response);

    public abstract void error(VolleyError error);

    public void postJsonApi(final Context context, String url, final JSONObject headerObj, final boolean isloader) {
        if (isloader) {
            loader(context);
        }

        Constant.Log("Response===", "" + headerObj + "URL" + url);
        final Request.Priority mPriority = Request.Priority.NORMAL;
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, headerObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Constant.Log("Response===1", "" + response);

                if (isloader) {
                    customLoader.dismiss();
                }
                response(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Constant.Sop("Error:" + error.getMessage());
                if (isloader) {
                    customLoader.dismiss();
                }
                if (error.getMessage() == null) {
                    Toast.makeText(context, "Please Try Again", Toast.LENGTH_SHORT).show();
                }
                error(error);
            }
        }) {
            @Override
            public Priority getPriority() {
                return mPriority;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3 * 60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getJsonApi(Context context, String countries_url, JSONObject obj, final boolean isloader) {
        Constant.Sop("Response==" + "URL" + countries_url);
        if (isloader) {
            loader(context);
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, countries_url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("getPackage", response.toString());

                        if (isloader) {
                            customLoader.dismiss();
                        }
                        response(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getPackage", "Error: " + error.getMessage());
                if (isloader) {
                    customLoader.dismiss();
                }
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3 * 60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void loader(Context context) {
        customLoader = new CustomLoader(context, android.R.style.Theme_Translucent_NoTitleBar);
        customLoader.show();
        customLoader.setCanceledOnTouchOutside(false);
        customLoader.setCancelable(false);
    }
}
