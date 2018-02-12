package com.mudra.registration;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.mudra.R;
import com.mudra.fragment.Account;
import com.mudra.fragment.Cart;
import com.mudra.fragment.Edit_profile;
import com.mudra.fragment.Favourite;
import com.mudra.fragment.Order;
import com.mudra.fragment.Search;
import com.mudra.utils.Constant;

public class Home extends AppCompatActivity implements View.OnClickListener {
    public Fragment currentFragment;
    private ImageButton favourite, search, order, cart, account,home,back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        intialise_views();
    }

    private void intialise_views() {

        account=(ImageButton)findViewById(R.id.account);
        cart=(ImageButton)findViewById(R.id.cart);
        order=(ImageButton)findViewById(R.id.order);
        search=(ImageButton)findViewById(R.id.search);
        favourite=(ImageButton)findViewById(R.id.favourite);
        back=(ImageButton)findViewById(R.id.back);
        home=(ImageButton)findViewById(R.id.home);
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

        account.setImageResource(R.drawable.account_deselect);
        cart.setImageResource(R.drawable.cart_deselect);
        order.setImageResource(R.drawable.order_deselect);
        search.setImageResource(R.drawable.search_deselect);
        favourite.setImageResource(R.drawable.favourite_select);
        replaceFragmentBack(new Favourite());

    }

    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, currentFragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/
        ;
    }

    public void replaceFragmentBack(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/
        ;
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

        switch (view.getId())
        {
            case R.id.account:

                account.setImageResource(R.drawable.account_select);
                cart.setImageResource(R.drawable.cart_deselect);
                order.setImageResource(R.drawable.order_deselect);
                search.setImageResource(R.drawable.search_deselect);
                favourite.setImageResource(R.drawable.favourite_deselect);
                replaceFragment(new Account());
                break;


            case R.id.order:
                account.setImageResource(R.drawable.account_deselect);
                cart.setImageResource(R.drawable.cart_deselect);
                order.setImageResource(R.drawable.order_select);
                search.setImageResource(R.drawable.search_deselect);
                favourite.setImageResource(R.drawable.favourite_deselect);
                replaceFragment(new Order());
                break;


            case R.id.cart:
                account.setImageResource(R.drawable.account_deselect);
                cart.setImageResource(R.drawable.cart_select);
                order.setImageResource(R.drawable.order_deselect);
                search.setImageResource(R.drawable.search_deselect);
                favourite.setImageResource(R.drawable.favourite_deselect);
                replaceFragment(new Cart());
                break;



            case R.id.search:
                account.setImageResource(R.drawable.account_deselect);
                cart.setImageResource(R.drawable.cart_deselect);
                order.setImageResource(R.drawable.order_deselect);
                search.setImageResource(R.drawable.search_select);
                favourite.setImageResource(R.drawable.favourite_deselect);
                replaceFragment(new Search());
                break;


            case R.id.favourite:
                account.setImageResource(R.drawable.account_deselect);
                cart.setImageResource(R.drawable.cart_deselect);
                order.setImageResource(R.drawable.order_deselect);
                search.setImageResource(R.drawable.search_deselect);
                favourite.setImageResource(R.drawable.favourite_select);
                replaceFragment(new Favourite());
                break;

            case R.id.back:
              onBackPressed();
                break;

            case R.id.home:
                account.setImageResource(R.drawable.account_deselect);
                cart.setImageResource(R.drawable.cart_deselect);
                order.setImageResource(R.drawable.order_deselect);
                search.setImageResource(R.drawable.search_deselect);
                favourite.setImageResource(R.drawable.favourite_select);
                replaceFragment(new Favourite());
                break;
                default:
                    break;
        }

    }

    @Override
    public void onBackPressed() {

        if (currentFragment instanceof Favourite || currentFragment instanceof Account || currentFragment instanceof Search ||
                currentFragment instanceof Order || currentFragment instanceof Cart)
        {
            finish();
        }
        else {
            super.onBackPressed();
        }

    }
}
