package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CUstomButtonRegular extends Button {
    public CUstomButtonRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomButtonRegular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/helveticaneue_regular.ttf");
            setTypeface(tf);
        }
    }
}
