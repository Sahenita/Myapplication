package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CUstomTextHelveticaNormal extends TextView {
    public CUstomTextHelveticaNormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomTextHelveticaNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomTextHelveticaNormal(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Helvetica-Normal.ttf");
            setTypeface(tf);
        }
    }
}
