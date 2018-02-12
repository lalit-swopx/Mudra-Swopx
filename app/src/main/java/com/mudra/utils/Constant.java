package com.mudra.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.mudra.app.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by apple on 7/16/16.
 */
public class Constant {
    public static void Sop(String msg) {
        try {
//            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ToastShort(Context context, String msg) {
        try {
            Constant.Log("Toast===","Toast Long");
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ToastLong(Context context, String msg) {
        try {
            Constant.Log("Toast===","Toast Long");
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Log(String context, String msg) {
        try {
//            Log.d(context, msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        Constant.Log("No of Camera", "" + numberOfCameras);
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    /**
     * Hides the soft keyboard
     */
    public static void hidekeyboard(Context context, View view) {
        //((Activity)context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity ctx) {
        Constant.Sop("Hide keyboard=====");
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) c.getSystemService(c.INPUT_METHOD_SERVICE);
        view.requestFocus();
//        inputMethodManager.showSoftInput(view, 0);
    }

    public static boolean isNetConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void setDatePicker(final Context context, final TextView textView, final String value) {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String selected_date= dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//                        textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                        textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            compareDate(context,textView,selected_date,value);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static void compareDate(Context context, TextView textView, String selected_date, String value) {

        try {
            final Calendar calendar = Calendar.getInstance();
            String curDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date convertedDate = new Date();
            Date convertedDate2 = new Date();
            convertedDate = dateFormat.parse(selected_date);
            convertedDate2 = dateFormat.parse(curDate);

            Constant.Log("Current Date",""+curDate+"Selected Date"+selected_date+"Convert Date"+convertedDate2.after(convertedDate));
            if(value.equalsIgnoreCase("new"))
            {
                if (convertedDate2.after(convertedDate))
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String format = formatter.format(convertedDate);
                    textView.setText(format);
//                textView.setTag(format);
                }
                else {
                    Constant.ToastShort(context, "You cannot select future date.");
                    textView.setText("");
                }
            }
            else
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String format = formatter.format(convertedDate);
                textView.setText(format);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }





    }
    public static String displayFirebaseRegId(Context context) {
          String pharmacyRegId;
        Constant.Log("Current Fragment=====","======4:");
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        pharmacyRegId = regId;
        Constant.Log("Current Fragment=====","======4:"+pharmacyRegId);
        return pharmacyRegId;
    }


}
