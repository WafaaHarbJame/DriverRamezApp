package com.ramez.shopp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.ramez.shopp.R;

public class EmptyCartDialog extends Dialog {


    Activity activity;

    public EmptyCartDialog(Context context, int message, int okStr, int cancelStr, final ConfirmDialog.Click okCall, final ConfirmDialog.Click cancelCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.dialog_empty_cart);
        setCancelable(false);


        try {
            if (activity != null && !activity.isFinishing())
                show();
        } catch (Exception e) {
            dismiss();
        }


    }

    public static abstract class Click {
        public abstract void click();
    }
}
