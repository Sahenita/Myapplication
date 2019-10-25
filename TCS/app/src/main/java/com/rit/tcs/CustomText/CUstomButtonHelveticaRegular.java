package com.rit.tcs.CustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CUstomButtonHelveticaRegular extends Button {
    public CUstomButtonHelveticaRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CUstomButtonHelveticaRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CUstomButtonHelveticaRegular(Context context) {
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
