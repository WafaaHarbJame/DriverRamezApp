package com.ramez.shopp.SpichalViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.ramez.shopp.Classes.Constants;


public class SEditText extends AppCompatEditText {
    public SEditText(Context context) {
        super(context);init();
    }

    public SEditText(Context context, AttributeSet attrs) {
        super(context, attrs);init();
    }

    public SEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);init();
    }

    private void init(){
       Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.NORMAL_FONT);

        setTypeface(typeface);
    }

}
