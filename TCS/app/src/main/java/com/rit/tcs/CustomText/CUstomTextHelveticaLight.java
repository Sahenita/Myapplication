package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CUstomTextHelveticaLight extends TextView {
    public CUstomTextHelveticaLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomTextHelveticaLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomTextHelveticaLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Helvetica-Light.ttf");
            setTypeface(tf);
        }
    }
}
