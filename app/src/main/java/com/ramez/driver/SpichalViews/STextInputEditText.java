package com.ramez.driver.SpichalViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
import com.ramez.driver.Classes.Constants;


public class STextInputEditText extends TextInputEditText {

    Context context;

    public STextInputEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public STextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public STextInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
//        String name = context.getResources().getString(R.s)
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.NORMAL_FONT);

        setTypeface(typeface);
    }


}
