package com.mudra.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;

import com.mudra.R;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.custom_fonts.TypefaceEditText;
import com.mudra.custom_fonts.TypefaceTextView;
import com.mudra.registration.Home;
import com.mudra.registration.MyBrowser;

public class Account extends Fragment {
    TypefaceTextView profileLabel;
    Switch notificationSwitch, messageSwitch;
    RelativeLayout privacyLayout, termLayout;
    WebView wv1;
    ScrollView scroll;

    public static Account newInstance(String data) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_fragment, container, false);

        privacyLayout = (RelativeLayout) v.findViewById(R.id.privacyLayout);
        termLayout = (RelativeLayout) v.findViewById(R.id.termLayout);

        scroll = (ScrollView) v.findViewById(R.id.scroll);
        wv1 = (WebView) v.findViewById(R.id.webview);

        wv1.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);

        profileLabel = (TypefaceTextView) v.findViewById(R.id.profileLabel);
        profileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).EditProfile();
            }
        });

        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wv1.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.GONE);

                wv1.setWebViewClient(new MyBrowser());

                String url = Url_Links.Base_Url + "pages/privacy";

                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);

            }
        });

        termLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv1.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.GONE);

                wv1.setWebViewClient(new MyBrowser());

                String url = Url_Links.Base_Url + "pages/termscondition";

                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });
        return v;
    }
}