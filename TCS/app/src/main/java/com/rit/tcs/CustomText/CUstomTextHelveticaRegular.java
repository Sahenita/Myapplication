package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CUstomTextHelveticaRegular extends TextView {
    public CUstomTextHelveticaRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomTextHelveticaRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomTextHelveticaRegular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Helvetica.ttf");
            setTypeface(tf);
        }
    }
}
