package com.ramez.shopp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kcode.permissionslib.main.OnRequestPermissionsCallBack;
import com.kcode.permissionslib.main.PermissionCompat;
import com.ramez.shopp.ApiHandler.DataFetcherCallBack;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.R;

import org.jetbrains.annotations.NotNull;


public class PickImageDialog extends Dialog {

    private LinearLayout captureImgBtn;
    private LinearLayout pickImgBtn;


    Activity activity;
    DataFetcherCallBack dataFetcherCallBack;

    public PickImageDialog(Activity activity, final DataFetcherCallBack dataFetcherCallBack) {
        super(activity);

        this.activity = activity;

        this.dataFetcherCallBack = dataFetcherCallBack;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.dialog_pick_image);

        captureImgBtn =  findViewById(R.id.captureImgBtn);
        pickImgBtn =  findViewById(R.id.pickImgBtn);

        captureImgBtn.setOnClickListener(view -> {
            if (dataFetcherCallBack != null)
                dataFetcherCallBack.Result(getDialog(), Constants.CAPTURE, true);
            dismiss();
        });

        pickImgBtn.setOnClickListener(view -> {

            if (dataFetcherCallBack != null)
                dataFetcherCallBack.Result(getDialog(), Constants.PICK, true);
            dismiss();
        });

        try {
            if (activity != null && !activity.isFinishing())
                show();
        } catch (Exception e) {
            dismiss();
        }

    }

    private PickImageDialog getDialog() {
        return this;
    }

    private final void openPicker() {
        try {
            PermissionCompat.Builder builder = new PermissionCompat.Builder((activity));
            builder.addPermissions(new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"});
            builder.addPermissionRationale(activity.getString(R.string.should_allow_permission));
            builder.addRequestPermissionsCallBack(new OnRequestPermissionsCallBack() {
                public void onGrant() {


                }
                public void onDenied(@NotNull String permission) {
                    Toast.makeText(activity, ""+activity.getString(R.string.some_permission_denied), Toast.LENGTH_SHORT).show();

                }
            });
            builder.build().request();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

}
