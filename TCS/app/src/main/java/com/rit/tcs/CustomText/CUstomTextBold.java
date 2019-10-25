package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CUstomTextBold extends TextView {
    public CUstomTextBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomTextBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/helveticaneue_bold.ttf");
            setTypeface(tf);
        }
    }
}
