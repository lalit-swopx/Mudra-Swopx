package com.mudra.custom_fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.mudra.R;


/**
 * Created by alcanzar on 17/03/17.
 */

public class TypefaceButton extends android.support.v7.widget.AppCompatButton
{

    public TypefaceButton(Context context, AttributeSet attrs) {
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
