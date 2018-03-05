package com.mudra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
/*import android.util.Log;*/
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mudra.R;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.model.DashboardResponse;
import com.mudra.model.ImageUploadResponse;
import com.mudra.model.LoginRequest;
import com.mudra.model.LoginResponse;
import com.mudra.model.OrderListResponse;
import com.mudra.model.OrderRequest;
import com.mudra.model.ProductListResponse;
import com.mudra.model.RegisterResponse;
import com.mudra.registration.Home;
import com.mudra.registration.Login;
import com.mudra.registration.Registration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class Util {

    public static String imagePath = "";
    public static File imageFile = null;
    Context context;


    public static void SaveImage(Context context, Bitmap finalBitmap, String folderName) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + folderName);
        if (!myDir.isDirectory())
            myDir.mkdirs();

        String fname = generateUniqueFileName("image_swopx") + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            imageFile = file;
            scanMedia(file.getAbsolutePath(), context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri contentUri = Uri.fromFile(myDir);
            scanIntent.setData(contentUri);
            context.sendBroadcast(scanIntent);
        } else {
            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            context.sendBroadcast(intent);
        }
    }

    public static String imageingallery(Context context, Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        //Log.e("root", "root : " + root);
        /*File myDir = new File(root + "/SWOPX");
        myDir.mkdirs();*/

        File myDir = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "SWOPX");
        if (!myDir.mkdirs()) {
            //Log.e("myDir", "Directory not created");
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        //Log.i("here tag", "" + file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri contentUri = Uri.fromFile(myDir);
            scanIntent.setData(contentUri);
            context.sendBroadcast(scanIntent);
        } else {
            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            context.sendBroadcast(intent);
        }

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Log.e("here path", " is : " + file);
        return fname;
    }

    public static String generateUniqueFileName(String fileName) {
        String filename = fileName;
        long millis = System.currentTimeMillis();
        filename = filename + millis;
        return filename;
    }

    static void scanMedia(String path, Context context) {
        MediaScannerConnection.scanFile(context,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        //Log.i("ExternalStorage", "Scanned " + path + ":");
                        //Log.i("ExternalStorage", "-> uri=" + uri);
                        imagePath = path;

                    }
                });
    }

    public static boolean isEmpty(EditText etText) {

        return etText.getText().toString().trim().length() != 0;
    }


    public static Bitmap mutableBitmap(Bitmap bmp) {
        bmp = bmp.copy(bmp.getConfig(), true);
        return bmp;
    }

    public static Bitmap takeShot(View bitView) {
        Bitmap bm;
        bitView.setDrawingCacheEnabled(true);
        bitView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        bm = Bitmap.createBitmap(bitView.getDrawingCache());
        bitView.setDrawingCacheEnabled(false);
        return bm;
    }

    public static void openCamera(Context mContext, int requestCode, int cameraId) {

        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            //Log.e("Exception", "" + e);
            e.printStackTrace();
        }
        if (cameraId > 0) {
            Constant.Log("No of Camera2", "" + cameraId);
            Intent cameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

            cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            ((Activity) mContext).startActivityForResult(cameraIntent, requestCode);
        } else {
            Constant.Log("No of Camera3", "" + cameraId);
            Intent cameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            ((Activity) mContext).startActivityForResult(cameraIntent, requestCode);
        }

    }

    public static void openGallery(Context mContext, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    @SuppressLint("SimpleDateFormat")
    public static File createImageFile() throws IOException {
        String getRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(getRoot + "/temp/");
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm")
                .format(new Date());
        String imageFileName = "FX" + timeStamp;
        File image = File.createTempFile(imageFileName, ".jpg", myDir);

        return image;
    }

    public static boolean isStorageAvailable(Context con) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Toast.makeText(con, "sd card is not writeable", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(con, "SD is not available..!!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public static Bitmap customDecodeFile(String path, int reqW, int reqH) {
        final BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, ops);
        ops.inSampleSize = calculateInSampleSize(ops, reqW, reqH);
        ops.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, ops);
        return bmp;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio <= widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public static void shareImage(File file, Context act) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        act.startActivity(Intent.createChooser(share, "Share Image"));
    }


    public static void initShareIntent(String type, Context con, File imaFile) {
        if (isNetConnected(con)) {
            boolean found = false;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");

            // gets the list of intents that can be loaded.
            List<ResolveInfo> resInfo = con.getPackageManager()
                    .queryIntentActivities(share, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    if (info.activityInfo.packageName.toLowerCase().contains(
                            type)
                            || info.activityInfo.name.toLowerCase().contains(
                            type)) {
                        share.putExtra(Intent.EXTRA_SUBJECT, "Awesome App..");
                        share.putExtra(
                                Intent.EXTRA_TEXT,
                                "Get It on GooglePlay..\n"
                                        + " https://play.google.com/store/apps/details?id="
                                        + con.getPackageName());
                        share.putExtra(Intent.EXTRA_STREAM,
                                Uri.fromFile(imaFile));
                        share.setPackage(info.activityInfo.packageName);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(con, "App was not installed.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                con.startActivity(Intent.createChooser(share, "Select"));
            }
        }
    }


    public static boolean isNetConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Toast.makeText(ctx, "Enable Internet Connection..",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void moreapps(Context ctx, String publishername) {
        if (isNetConnected(ctx)) {
            Intent pubPage = null;
            pubPage = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:" + publishername));
            pubPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(pubPage);
        }
    }

   /* public static void rate(Context ctx) {
        if (isNetConnected(ctx)) {
            ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("market://details?id=" + ctx.getPackageName()))
                    .addFlags(268435456));
        }
    }*/

    public static void share(Context ctx) {
        if (isNetConnected(ctx)) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
                    R.string.app_name);
            sharingIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id="
                            + ctx.getPackageName());

            Intent i = Intent.createChooser(sharingIntent, "Share using");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
        }
    }

    public static Bitmap getImageThumbnail(Bitmap bmp, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bmp, width, height);
    }


    public static boolean saveImage(Bitmap save_bitmap, Context act,
                                    String rootDir) {
        if (isStorageAvailable(act)) {

            String root = Environment.getExternalStorageDirectory().toString();
            File rootFile = new File(root, rootDir);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String fname = generateUniqueName("pic") + ".jpg";
            imageFile = new File(rootFile, fname);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            FileOutputStream f = null;
            try {
                f = new FileOutputStream(imageFile);
                save_bitmap.compress(Bitmap.CompressFormat.PNG, 90, f);
                f.flush();
                f.close();
                galleryAddPic(imageFile, act);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static void galleryAddPic(File file, final Context context) {
        MediaScannerConnection.scanFile(context, new String[]{file.toString()}, (String[]) null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, final Uri uri) {

                    }
                });
    }

    @SuppressLint("SimpleDateFormat")
    private static String generateUniqueName(String filename) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        filename = filename + timeStamp;
        return filename;
    }

    public static String getRealFilePath(final Context context, final Uri uri) {

        if (null == uri)
            return null;

        final String scheme = uri.getScheme();
        String data = null;

        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String getRealPathFromURI(final Context context, Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};

        if ("content".equalsIgnoreCase(contentUri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
                Constant.Log("Image Path in ON COLUMN INDEX", "" + path + ";" + column_index);
            }

            cursor.close();
            Constant.Log("Image Path in ON COntent", "" + path);
            return path;
        } else if ("file".equalsIgnoreCase(contentUri.getScheme())) {
            Constant.Log("Image Path in ON File", "" + contentUri.getPath());
            return contentUri.getPath();
        }
        Constant.Log("Image Path in ON outside", "" + path);
        return null;
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }

    public static Matrix ratio(Bitmap bitmap, float width, float height) {
        float oldWidth = bitmap.getWidth();
        float oldHeight = bitmap.getHeight();
        float ratio;
        float bitratio;
        Matrix m = new Matrix();
        if (oldWidth >= oldHeight)
            bitratio = oldHeight;
        else
            bitratio = oldWidth;
        if (width >= height) {
            ratio = (float) width / bitratio;

        } else {
            ratio = (float) height / bitratio;
        }
        m.postScale(ratio, ratio);
        return m;
    }

//    public static void hidekeyboard(Context context) {
//        ((Activity) context).getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//        );
//    }


    public static void hidekeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //    public void showSoftKeyboard(View view) {
//        if (view.requestFocus()) {
//            InputMethodManager imm = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
//        }
//    }
    public static String getDateCurrentTimeZone(long timestamp) {
//        try{
//            Calendar calendar = Calendar.getInstance();
//            TimeZone tz = TimeZone.getDefault();
//            calendar.setTimeInMillis(timestamp * 1000);
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy , HH:mm:ss");
//            Date currenTimeZone = (Date) calendar.getTime();
//            return sdf.format(currenTimeZone);
//        }catch (Exception e) {
//        }
//        return "";
        String DATE_FORMAT = "dd:MM:yyyy";

        return new SimpleDateFormat(DATE_FORMAT).format(timestamp * 1000);
    }

    public static void LoginRequest(final Context context, final String method, final String data, final ProgressDialog mProgressDialog) {
        try {
            final LoginRequest customerListRequest = new Gson().fromJson(data, LoginRequest.class);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<LoginResponse> call = git.LoginRequest("" + Url_Links.Base_Url + "login", customerListRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                    //Log.e("resposne", "is : " + new Gson().toJson(response.body()).toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    if (response.body().getResult().equalsIgnoreCase("404")) {

                        new AlertDialog.Builder(context)
                                .setTitle("Confirmation")
                                .setMessage(response.body().getMsg())
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        final ProgressDialog mProgressDialog = new ProgressDialog(context);
                                        mProgressDialog.setIndeterminate(true);
                                        mProgressDialog.setMessage("Loading...");
                                        mProgressDialog.show();

                                        LoginRequest customerListRequest = new Gson().fromJson(data, LoginRequest.class);
                                        customerListRequest.setFlag("1");

                                        Util.LoginRequest(context, "login", new Gson().toJson(customerListRequest).toString(), mProgressDialog);
                                    }
                                }).show();
                    } else {
                        Util.setLoginData(context, new Gson().toJson(response.body()).toString());
                        //Log.e("response", "success : " + new Gson().toJson(response.body()).toString());

                        Util.DashboardBrand(context, "dashboard", "", mProgressDialog);
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void RegisterRequest(final Context context, final String method, final String data, final ProgressDialog mProgressDialog) {
        try {
            LoginRequest customerListRequest = new Gson().fromJson(data, LoginRequest.class);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<RegisterResponse> call = git.RegisterRequest("" + Url_Links.Base_Url + "registeruser", customerListRequest);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    Util.setLoginData(context, new Gson().toJson(response.body()).toString());
                    // Log.e("response", "success : " + new Gson().toJson(response.body()).toString());
                    Util.DashboardBrand(context, "dashboard", "", mProgressDialog);
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UpdateProfile(final Context context, final String method, final String data, final ProgressDialog mProgressDialog) {
        try {
            LoginRequest customerListRequest = new Gson().fromJson(data, LoginRequest.class);

            // Log.e("response", "success : " + data);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<LoginResponse> call = git.UpdateProfile("" + Url_Links.Base_Url + "update-profile", customerListRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    Util.setLoginData(context, new Gson().toJson(response.body()).toString());
                    Constant.ToastShort(context, new Gson().toJson(response.body().getMsg()).toString());

                    //Log.e("response", "success : " + new Gson().toJson(response.body()).toString());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    // Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ProductList(final Context context, final String method, final String data, final ProgressDialog mProgressDialog, final String from) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<ProductListResponse> call = git.ProductList("" + Url_Links.Base_Url + method);
            call.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, retrofit2.Response<ProductListResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    //Log.e("response", "is: " + new Gson().toJson(response.body()).toString());

                    Util.setProduct(context, new Gson().toJson(response.body()).toString());

                    if (from.equalsIgnoreCase("favourite"))
                        ((Home) context).Favourite("");
                    else
                        ((Home) context).Search("");
                }

                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                    // Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OrderList(final Context context, final String method, final String data, final ProgressDialog mProgressDialog, final String from) {
        try {

            String loginData = Util.getLoginData(context);
            LoginResponse loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<OrderListResponse> call = git.OrderList("" + Url_Links.Base_Url + method + "/" + loginResponse.getClient_detail().getId());
            call.enqueue(new Callback<OrderListResponse>() {
                @Override
                public void onResponse(Call<OrderListResponse> call, retrofit2.Response<OrderListResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    //Log.e("response", "is: " + new Gson().toJson(response.body()).toString());

                    ((Home) context).Order(new Gson().toJson(response.body()).toString());
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void DashboardBrand(final Context context, final String method, final String data, final ProgressDialog mProgressDialog) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<DashboardResponse> call = git.DashboardBrand("" + Url_Links.Base_Url + "dashboard");
            call.enqueue(new Callback<DashboardResponse>() {
                @Override
                public void onResponse(Call<DashboardResponse> call, retrofit2.Response<DashboardResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    Util.setCartItem(context, "");
                    Util.setProductToCart(context, "");
                    Util.setDashboardBrand(context, new Gson().toJson(response.body()).toString());

                    Intent homeIntent = new Intent(context, Home.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(homeIntent);
                }

                @Override
                public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void ForgotPassword(final Context context, final String method, final String data, final ProgressDialog mProgressDialog, final Dialog dialog) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<ProductListResponse> call = git.ForgotPassword("" + Url_Links.Base_Url + "forget-password/" + data);
            call.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, retrofit2.Response<ProductListResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    dialog.dismiss();
                    Constant.ToastShort(context, new Gson().toJson(response.body().getMsg()).toString());
                }

                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                    // Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CheckSignup(final Context context, final String from, final String data, final ProgressDialog mProgressDialog) {
        try {

            LoginRequest loginRequest = new Gson().fromJson(data, LoginRequest.class);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<ProductListResponse> call = git.CheckSignup("" + Url_Links.Base_Url + "check-unique", loginRequest.getEmail(), loginRequest.getPhone());
            call.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, retrofit2.Response<ProductListResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    //Log.e("coming", "here : " + new Gson().toJson(response.body().getResult()));
                    ((Registration) context).CheckValid(from, new Gson().toJson(response.body().getResult()));
                    Constant.ToastShort(context, new Gson().toJson(response.body().getMsg()).toString());
                }

                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UploadImage(final Context context, final String method, final File file, final ProgressDialog mProgressDialog) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            Call<ImageUploadResponse> call = git.UploadImage("" + Url_Links.Base_Url + "image-upload", body);
            call.enqueue(new Callback<ImageUploadResponse>() {
                @Override
                public void onResponse(Call<ImageUploadResponse> call, retrofit2.Response<ImageUploadResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    //Log.e("response", "success " + new Gson().toJson(response.body()).toString());
                }

                @Override
                public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OrderRequest(final Context context, final String method, final String data, final ProgressDialog mProgressDialog) {
        try {
            OrderRequest customerListRequest = new Gson().fromJson(data, OrderRequest.class);

            //Log.e("request", "is : " + data);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //Call<DashboardResponse> call = git.DashboardData("" + AppUrl.BASE_URL_App + "getDashboardDetail/1");
            Call<LoginResponse> call = git.OrderRequest("" + Url_Links.Base_Url + "new-order", customerListRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    Toast.makeText(context, "Order Submitted Successfully", Toast.LENGTH_SHORT).show();

                    ((Home) context).Dashboard();
                    //Log.e("response", "success : " + new Gson().toJson(response.body()).toString());

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setLoginData(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Url_Links.loginPref, key);
        editor.commit();
    }

    public static String getLoginData(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        String key = myPrefs.getString(Url_Links.loginPref, null);
        return key;
    }

    public static void setDashboardBrand(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Url_Links.dashboardbrandPref, key);
        editor.commit();
    }

    public static String getDashboardBrand(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        String key = myPrefs.getString(Url_Links.dashboardbrandPref, null);
        return key;
    }

    public static void setProduct(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Url_Links.productPref, key);
        editor.commit();
    }

    public static String getProduct(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        String key = myPrefs.getString(Url_Links.productPref, null);
        return key;
    }

    public static void setCartItem(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Url_Links.cartPref, key);
        editor.commit();
    }

    public static String getCartItem(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        String key = myPrefs.getString(Url_Links.cartPref, null);
        return key;
    }

    public static void setProductToCart(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Url_Links.productToPref, key);
        editor.commit();
    }

    public static String getProductToCart(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        String key = myPrefs.getString(Url_Links.productToPref, null);
        return key;
    }

    public static void setToken(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Url_Links.tokenToPref, key);
        editor.commit();
    }

    public static String getDeviceId(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(Url_Links.appPref, context.MODE_PRIVATE);
        String key = myPrefs.getString(Url_Links.tokenToPref, null);
        return key;
    }
}