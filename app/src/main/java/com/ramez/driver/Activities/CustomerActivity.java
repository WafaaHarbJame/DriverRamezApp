package com.ramez.driver.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack;
import com.kcode.permissionslib.main.PermissionCompat;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Dialogs.PickImageDialog;
import com.ramez.driver.Models.LoginResultModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.ProfileData;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.FileUtil;
import com.ramez.driver.Utils.NumberHandler;
import com.ramez.driver.databinding.ActivityCustomerBinding;
import com.ramez.driver.databinding.ActivityEditProfileBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class CustomerActivity extends ActivityBase {

    MemberModel memberModel;
    int userId;
    private ActivityCustomerBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle(getString(R.string.profile));

        setTitle(R.string.text_title_edit_profile);

        getExtraData();


    }




    private void getExtraData() {
        if (getIntent().getExtras() != null) {
            String userID = getIntent().getExtras().getString(Constants.user_id, "");
               userId= Integer.parseInt(userID);
               getUserData(userId);

        }
    }


    public void getUserData(int user_id) {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<ProfileData> result = (ResultAPIModel<ProfileData>) obj;
            String message = getString(R.string.fail_to_get_data);

                if (IsSuccess) {

                    binding.userNameTv.setText(result.data.getUserName());
                    binding.edtUserName.setText(result.data.getUserName());
                    binding.etEmail.setText(result.data.getEmail());
                    if(result.data.getProfilePicture()!=null){
                        Glide.with(getActiviy()).asBitmap().load(result.data.getProfilePicture()).error(R.drawable.avatar)
                                .placeholder(R.drawable.avatar).into(binding.userImg);

                    }



            }


        }).getUserDetails(user_id);
    }
}