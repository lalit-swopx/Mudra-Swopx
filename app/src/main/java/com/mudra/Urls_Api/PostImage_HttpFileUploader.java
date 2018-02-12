package com.mudra.Urls_Api;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mac on 30/03/16.
 */
public class PostImage_HttpFileUploader extends AsyncTask<Void, Integer, String> implements Runnable {

    String response, Url, FileName, Type;
    Context context;
    TextView txtPercentage;
    File f;
    URL connectURL;
    String mediaType;
    //	String Token;
    String TypefileUploader;
    String fileName;
    String Tag = "HTTPREQUEST";

    public void receiveData(String response) {
    }

    public void receiveError() {
    }

    ProgressDialog pDialog;

    public PostImage_HttpFileUploader(File fl, String url, String filename, Context c, String type) {
        f = fl;
        Url = url;
        FileName = filename;
        Type = type;
        context = c;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.show();
        pDialog.setProgress(0);
        super.onPreExecute();
        pDialog.setCancelable(false);
    }

    @Override
    protected void onPostExecute(String res) {
        pDialog.hide();
        //showAlert(res);

        super.onPostExecute(res);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Making progress bar visible
////        pDialog.setVisibility(View.VISIBLE);
//
//
        pDialog.setProgress(progress[0]);

        txtPercentage.setText(String.valueOf(progress[0]) + "%");
    }

    @Override
    protected String doInBackground(Void... params) {
        FileInputStream fis;


        return response;
        //return uploadFile();
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

    public PostImage_HttpFileUploader(String urlString, String mediaType, String fileName, String type) {

        try {
            connectURL = new URL(urlString);
            Log.e("final url", connectURL.toString());
        } catch (Exception ex) {
            Log.e("URL FORMATION", "MALFORMATED URL");
        }
        this.mediaType = mediaType;
        this.fileName = fileName;
//		this.Token = token;
        this.Type = type;
    }

    FileInputStream fileInputStream = null;
    private static String s;

    public void doStart(FileInputStream stream) {
        fileInputStream = stream;
        thirdTry();
    }

    void thirdTry() {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        try {
            // ------------------ CLIENT REQUEST
            Log.e(Tag, "Request Start");
            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; " + mediaType + "\"" + 2 + "\";" + "filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag, "Headers are written");

            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 1024;

            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            bufferSize = entity.getContentLength();
//            httppost.setEntity(entity);
            byte[] buffer = new byte[bufferSize];
            // read file and write it into form...
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e(Tag, "File is written");
            fileInputStream.close();
            dos.flush();
            InputStream is = conn.getInputStream();

            Log.e(Tag, "File is written done");
            // retrieve the response from server
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            s = b.toString();
            dos.close();
        } catch (IOException ioe) {
            Log.e(Tag, "error: " + ioe.getMessage(), ioe);
        }
    }


    @Override
    public void run() {

    }

    public static String updateCall() {
        String st = s;
        s = "";
        return st;
    }
}
