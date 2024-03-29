package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CUstomTextHelveticaBold extends TextView {
    public CUstomTextHelveticaBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomTextHelveticaBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomTextHelveticaBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Helvetica Bold.ttf");
            setTypeface(tf);
        }
    }
}
