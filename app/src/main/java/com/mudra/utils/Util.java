package com.mudra.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.mudra.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Util {

    public static String imagePath = "";
    public static File imageFile = null;
    Context context;


    public static void SaveImage(Context context, Bitmap finalBitmap, String folderName) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + folderName);
        if (!myDir.isDirectory())
            myDir.mkdirs();

        String fname = generateUniqueFileName("image") + ".jpg";
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
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
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
            Log.e("Exception", "" + e);
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
}