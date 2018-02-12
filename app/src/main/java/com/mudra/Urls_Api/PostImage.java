package com.mudra.Urls_Api;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.mudra.R;
import com.mudra.utils.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public abstract class PostImage extends AsyncTask<String, Void, String> {

    String response, Url, FileName, Type;
    Context context;
    TextView txtPercentage;
    File f;

    public abstract void receiveData(String response);

    public abstract void receiveError();

    ProgressDialog pDialog;

    public PostImage(File fl, String url, String filename, Context c, String type) {
        f = fl;
        Url = url;
        FileName = filename;
//        this.token = token;
        Type = type;
        context = c;
    }

    @Override
    protected void onPreExecute() {
//        pDialog = new ProgressDialog(context);
//        pDialog.show();
//        pDialog.setProgress(0);
        pDialog = new ProgressDialog(context, R.style.DialogTheme);
        pDialog.setCancelable(false);
        pDialog.show();
        View v = LayoutInflater.from(context).inflate(R.layout.progress_view, null, false);
        pDialog.setContentView(v);
        super.onPreExecute();

//     View v = LayoutInflater.from(context).inflate(R.layout.progress_view, null, false);
//        pDialog.setContentView(v);
        pDialog.setCancelable(false);
    }
//    @Override
//    protected void onProgressUpdate(Integer... progress) {
//        // Making progress bar visible
////        pDialog.setVisibility(View.VISIBLE);
//
//
//        pDialog.setProgress(progress[0]);
//
//
//        txtPercentage.setText(String.valueOf(progress[0]) + "%");
//    }


    @SuppressWarnings("static-access")
    @Override
    protected String doInBackground(String... params) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(f);
            Constant.Log("File Path====","pick_Request===4 "+f);
            HttpFileUploader htfu = new HttpFileUploader(Url, " image", FileName, Type);
            htfu.doStart(fis);
            response = htfu.updateCall();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            pDialog.hide();
            receiveError();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String res) {
        pDialog.hide();
        Log.e("Response", "adsjf;ladsl;f   " + res);
//        showAlert(res);


        receiveData(res);

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}