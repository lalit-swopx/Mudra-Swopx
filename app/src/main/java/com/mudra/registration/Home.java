package com.mudra.registration;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.mudra.R;
import com.mudra.Urls_Api.PostImage;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.custom_fonts.TypefaceEditText;
import com.mudra.fragment.Account;
import com.mudra.fragment.Cart;
import com.mudra.fragment.Edit_profile;
import com.mudra.fragment.Favourite;
import com.mudra.fragment.Order;
import com.mudra.fragment.OrderFragment;
import com.mudra.fragment.Search;
import com.mudra.fragment.dashboard;
import com.mudra.utils.Constant;
import com.mudra.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class Home extends AppCompatActivity implements View.OnClickListener {
    public Fragment currentFragment;
    private ImageButton favourite, search, order, cart, account, home, back, searchButton, cameraButton;
    private TypefaceEditText searchText;
    private LinearLayout searchLayout;

    Bitmap myBitmap;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        intialise_views();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchText.getText() != null && searchText.getText().length() > 0)
                    if (Constant.isNetConnected(Home.this)) {
                        final ProgressDialog mProgressDialog = new ProgressDialog(Home.this);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.show();

                        Util.ProductList(Home.this, "product-list" + "/" + searchText.getText().toString().trim(), "", mProgressDialog, "search");
                    } else {
                        Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void intialise_views() {

        account = (ImageButton) findViewById(R.id.account);
        cart = (ImageButton) findViewById(R.id.cart);
        order = (ImageButton) findViewById(R.id.order);
        search = (ImageButton) findViewById(R.id.search);
        favourite = (ImageButton) findViewById(R.id.favourite);
        back = (ImageButton) findViewById(R.id.back);
        home = (ImageButton) findViewById(R.id.home);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        searchText = (TypefaceEditText) findViewById(R.id.searchText);

        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);

        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        onClicks();
    }

    private void onClicks() {
        account.setOnClickListener(this);
        cart.setOnClickListener(this);
        order.setOnClickListener(this);
        search.setOnClickListener(this);
        favourite.setOnClickListener(this);
        home.setOnClickListener(this);
        back.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        Dashboard();

    }

    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, currentFragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/

    }

    public void replaceFragmentBack(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/

    }

    /**
     * Dialog open when new version of app updated on playstore.
     */
    public void setUpdateDialog(final Activity activity) {
        final android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(activity);
        alertbox.setTitle("Update App");
        alertbox.setMessage("New version found. Please update your App");
        alertbox.setIcon(R.drawable.login_logo);
        alertbox.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // finish used for destroyed activity
                        if (Constant.isNetConnected(Home.this)) {

                            final String appPackageName = getPackageName();
//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse("market://details?id=" + appPackageName));
//                            startActivity(i);


                        }
                    }
                });

        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //GlobelMethod.setValuesUpdate(activity, values);
            }
        });

        alertbox.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.account:
                account.setImageResource(R.drawable.account_deselect);
                cart.setImageResource(R.drawable.cart_deselect);
                order.setImageResource(R.drawable.order_deselect);
                search.setImageResource(R.drawable.search_deselect);
                favourite.setImageResource(R.drawable.favourite_select);
                replaceFragment(new Account());
                SearchUtil();
                break;


            case R.id.order:
                if (Constant.isNetConnected(Home.this)) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();

                    Util.OrderList(this, "order-history", "", mProgressDialog, "favourite");
                } else {
                    Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
                }
                break;


            case R.id.cart:
                Cart();
                break;


            case R.id.search:
                if (Constant.isNetConnected(Home.this)) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();

                    Util.ProductList(this, "product-list", "", mProgressDialog, "search");
                } else {
                    Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
                }

                break;


            case R.id.favourite:

                if (Constant.isNetConnected(Home.this)) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();

                    Util.ProductList(this, "product-list", "", mProgressDialog, "favourite");
                } else {
                    Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
                }

                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.home:
                Dashboard();
                break;

            case R.id.searchButton:
                GetSearchData();
                break;

            case R.id.cameraButton:
                startActivityForResult(getPickImageChooserIntent(), 200);
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {

        if (currentFragment instanceof Favourite || currentFragment instanceof Account || currentFragment instanceof Search ||
                currentFragment instanceof Order || currentFragment instanceof Cart) {
            Dashboard();
            SearchUtil();
        } else {
            super.onBackPressed();
            SearchUtil();
        }

    }

    public void GetSearchData() {
        if (Constant.isNetConnected(Home.this)) {

            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

            Util.ProductList(this, "product-list" + "/" + searchText.getText().toString().trim(), "", mProgressDialog, "search");
        } else {
            Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
        }
    }

    public void EditProfile() {
        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_deselect);
        order.setImageResource(R.drawable.order_deselect);
        search.setImageResource(R.drawable.search_deselect);
        favourite.setImageResource(R.drawable.favourite_select);
        replaceFragment(new Edit_profile().newInstance(""));
        SearchUtil();
    }

    public void Order(String list) {
        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_deselect);
        order.setImageResource(R.drawable.order_select);
        search.setImageResource(R.drawable.search_deselect);
        favourite.setImageResource(R.drawable.favourite_deselect);
        replaceFragment(new OrderFragment().newInstance(list));
        SearchUtil();
    }

    public void Dashboard() {
        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_deselect);
        order.setImageResource(R.drawable.order_deselect);
        search.setImageResource(R.drawable.search_deselect);
        favourite.setImageResource(R.drawable.favourite_deselect);
        replaceFragment(new dashboard().newInstance(""));
        SearchUtil();
    }

    public void Cart() {
        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_select);
        order.setImageResource(R.drawable.order_deselect);
        search.setImageResource(R.drawable.search_deselect);
        favourite.setImageResource(R.drawable.favourite_deselect);
        replaceFragment(new Cart());
        SearchUtil();
    }

    public void Favourite(String list) {
        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_deselect);
        order.setImageResource(R.drawable.order_deselect);
        search.setImageResource(R.drawable.search_deselect);
        favourite.setImageResource(R.drawable.favourite_select);
        replaceFragment(new Favourite().newInstance(""));
        SearchUtil();
    }

    public void Search(String list) {
        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_deselect);
        order.setImageResource(R.drawable.order_deselect);
        search.setImageResource(R.drawable.search_select);
        favourite.setImageResource(R.drawable.favourite_deselect);
        replaceFragment(new Search().newInstance(""));
        SearchUtil();
    }

    public void SearchUtil() {
        if (currentFragment instanceof Search || currentFragment instanceof dashboard) {
            searchLayout.setVisibility(View.VISIBLE);
            searchButton.setVisibility(View.VISIBLE);
        } else {
            searchLayout.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
        }
    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);

                    /*CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                    croppedImageView.setImageBitmap(myBitmap);
                    imageView.setImageBitmap(myBitmap);*/
                    String path = Util.imageingallery(Home.this, myBitmap);

                    File myDir = new File(getExternalFilesDir(
                            Environment.DIRECTORY_PICTURES), "SWOPX");
                    File file = new File(myDir, path);

                    /*if (Constant.isNetConnected(Home.this)) {

                        final ProgressDialog mProgressDialog = new ProgressDialog(Home.this);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.show();

                        Util.UploadImage(Home.this, "", file, mProgressDialog);
                        //login();
                    } else {
                        Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
                    }*/
                    uploadImage(file, Url_Links.Base_Url + "image-upload", path);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;
                String path = Util.imageingallery(Home.this, myBitmap);

                File myDir = new File(getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES), "SWOPX");
                File file = new File(myDir, path);


                uploadImage(file, Url_Links.Base_Url + "image-upload", path);

                /*if (Constant.isNetConnected(Home.this)) {

                    final ProgressDialog mProgressDialog = new ProgressDialog(Home.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();

                    Util.UploadImage(Home.this, "", file, mProgressDialog);
                    //login();
                } else {
                    Constant.ToastShort(Home.this, getResources().getString(R.string.internet_connection));
                }*/
                /*CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }

                imageView.setImageBitmap(myBitmap);*/

            }

        }

    }

    void uploadImage(final File file, String url, String name) {
        PostImage post = new PostImage(file, url, name, Home.this, "image") {
            @Override
            public void receiveData(String response) {
                finish();
                try {
                    //Log.e("RESPONSE", "reasponseee" + response.toString());
                    JSONObject response1 = new JSONObject(response);
                    //Log.e("json", response);
//                    if(response1.getString("status").equalsIgnoreCase("200"))

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void receiveError() {
                //Log.e("PROFILE", "ERROR");
            }
        };

        post.execute(url, null, null);
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }
}
