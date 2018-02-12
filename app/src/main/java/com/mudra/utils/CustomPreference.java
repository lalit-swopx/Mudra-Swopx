package com.mudra.utils;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.ArrayList;


public class CustomPreference {


    public static final String PREF_NAME = "Studyroom";

    @SuppressWarnings("deprecation")
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String Client_id = "client_id";
    public static final String Refferal_Code = "referral";
    public static final String Wallet_Amount = "wallet";
    public static final String Client_name = "client_name";
    public static  String client_id = "client";
    public static  String user_type = "user_type";
    public static  String User_Type = "user";
    public static final String U_UID = "USERID";
    public static final String U_NAME = "NAME";
    public static final String F_NAME = "f_name";
    public static final String L_NAME = "l_name";
    public static final String E_MAIL = "EMAIL";
    public static final String Mobile_no = "Mobile_no";
    public static final String Company_Id = "company_id";
    public static final String Guest_User_Id = "guest_id";
    public static final String Company_registered = "compny_regis";
    /*public static final String New_Version = "new_version";
    public static final String Old_version = "old_version";*/
//    public static final ArrayList<Items> phon_no_array=new ArrayList<>();



/*database String*/

    public static String id="id";
    public static String name="name";
    public static String short_name="short_name";
    public static String rowid="rowid";
    public static String parent_id="parent_id";
    public static String sync_tym="sync_tym";
    public static String status="status";

    public static String designation="designation";
    public static String job_profile="job_profile";
    public static String joning_date="joning_date";
    public static String leaving_date="leaving_date";
    public static String salary="salary";

    public static String s_id="s_id";
    public static String s_name="s_name";
    public static String percentage="percentage";
    public static String year="year";
    public static String subject="subject";
    public static String qualification="qualification";

    public static String language="language";
    public static String read_status="read_status";
    public static String write_status="write_status";
    public static String speak_status="speak_status";
    public static String uniquie_id="uniquie_id";
    public static String score="score";



    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void removeKey(Context context, String key) {

        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        editor.commit();
    }

    public static void removeAll(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.commit();
    }


}

