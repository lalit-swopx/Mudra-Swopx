package com.mudra.Urls_Api;

import android.app.Dialog;
import android.content.Context;

import com.mudra.R;


/**
 * Created by apple on 7/16/16.
 */
public class CustomLoader extends Dialog {
    public CustomLoader(Context context) {
        super(context);
    }

    public CustomLoader(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.progress_view);
    }
    public CustomLoader(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
