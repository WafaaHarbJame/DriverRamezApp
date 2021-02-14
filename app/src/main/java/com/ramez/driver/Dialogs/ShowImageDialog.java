package com.ramez.driver.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ramez.driver.R;


public class ShowImageDialog extends Dialog {
    Activity activity;
    String imageUrl;


    public ShowImageDialog(Activity activity, String imageUrl) {
        super(activity);

        this.activity = activity;
        this.imageUrl = imageUrl;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.show_image_dialog);
        ImageView closeBtn = findViewById(R.id.closeBtn);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(true);


        closeBtn.setOnClickListener(v -> {
            dismiss();

        });
        ImageView touchImageView = findViewById(R.id.iv_adImageView);
        Glide.with(activity).load(imageUrl).thumbnail(0.02f).into(touchImageView);

        try {
            if (activity != null && !activity.isFinishing()) show();
        } catch (Exception e) {
            dismiss();
        }


    }

    private ShowImageDialog getDialog() {
        return this;
    }

}
