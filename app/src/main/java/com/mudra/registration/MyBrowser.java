package com.mudra.registration;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Lenovo on 26-02-2018.
 */

public class MyBrowser extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}