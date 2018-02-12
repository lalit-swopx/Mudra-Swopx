package com.mudra.custom_fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.mudra.R;


/**
 * Created by apple on 3/31/17.
 */

public class TypefaceAutoTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView
{
    public TypefaceAutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface will not be applied in the layout editor.
        if (isInEditMode()) {
            return;
        }

        //To customize more items, add your objects under values->attrs
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefaceTextView);
        String font = styledAttrs.getString(R.styleable.TypefaceTextView_typeface_custom);
        styledAttrs.recycle();

        if (font != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
            setTypeface(typeface);
        }
    }
}
