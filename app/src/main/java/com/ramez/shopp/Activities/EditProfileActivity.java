package com.ramez.shopp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack;
import com.kcode.permissionslib.main.PermissionCompat;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.PickImageDialog;
import com.ramez.shopp.Models.LoginResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.Utils.PathUtil;
import com.ramez.shopp.databinding.ActivityEditProfileBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class EditProfileActivity extends ActivityBase {

    MemberModel memberModel;
    PickImageDialog pickImageDialog;
    int REQUEST_PICK_IMAGE = 11;
    int REQUEST_CAPTURE_IMAGE = 12;
    int userId;
    File selectedPhotoFil = null;
    private ActivityEditProfileBinding binding;
    private Bitmap userImageBitmap;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        setTitle(R.string.text_title_edit_profile);

        memberModel = UtilityApp.getUserData();
        userId = memberModel.getId();

        binding.userNameTv.setText(memberModel.getName());
        binding.edtUserName.setText(memberModel.getName());
        binding.etEmail.setText(memberModel.getEmail());
        binding.edtPhoneNumber.setText(memberModel.getMobileNumber());
        Log.i("tag", "Log data" + memberModel.getProfilePicture());

        Glide.with(getActiviy()).asBitmap().load(memberModel.getProfilePicture()).placeholder(R.drawable.avatar).into(binding.userImg);


        binding.saveBut.setOnClickListener(view1 -> {

            updateProfile();

        });

        binding.editPhotoBut.setOnClickListener(view1 -> {

            openPicker();

        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                userImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Glide.with(getActiviy()).load(userImageBitmap).into(binding.userImg);
                String imagePath = PathUtil.getPath(getActiviy(), data.getData());
                selectedPhotoFil = new File(imagePath);

                uploadPhoto(userId, selectedPhotoFil);

            } catch (Exception e) {
                e.printStackTrace();
                GlobalData.errorDialog(getActiviy(), R.string.upload_photo, getString(R.string.textTryAgain));
            }
        }

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {

            try {
                userImageBitmap = (Bitmap) data.getExtras().get("data");
                Glide.with(getActiviy()).load(userImageBitmap).into(binding.userImg);
                String imagePath = PathUtil.getPath(getActiviy(), data.getData());
                selectedPhotoFil = new File(imagePath);
                uploadPhoto(userId, selectedPhotoFil);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", "onActivityResult: Exception GALLERY_CONSTANT: " + e.getMessage());
                GlobalData.errorDialog(getActiviy(), R.string.upload_photo, getString(R.string.textTryAgain));
            }

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void pickImage() {
        pickImageDialog = new PickImageDialog(getActiviy(), (obj, func, IsSuccess) -> {

            if (func.equals(Constants.CAPTURE)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);

            } else if (func.equals(Constants.PICK)) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, getString(R.string.selectImage)), REQUEST_PICK_IMAGE);

            }
        });
        pickImageDialog.show();

    }

    private final void openPicker() {
        try {
            PermissionCompat.Builder builder = new PermissionCompat.Builder((getActiviy()));
            builder.addPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            builder.addPermissionRationale(getString(R.string.should_allow_permission));

            builder.addRequestPermissionsCallBack(new OnRequestPermissionsCallBack() {
                public void onGrant() {

                    pickImage();
                }

                public void onDenied(@NotNull String permission) {
                    Toast(R.string.some_permission_denied);

                }
            });
            builder.build().request();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }


    private void updateProfile() {

        final String name = NumberHandler.arabicToDecimal(binding.edtUserName.getText().toString());
        final String email = NumberHandler.arabicToDecimal(binding.etEmail.getText().toString());

        memberModel.setName(name);
        memberModel.setEmail(email);

        GlobalData.progressDialog(getActiviy(), R.string.update_profile, R.string.please_wait_upload);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();

            LoginResultModel result = (LoginResultModel) obj;
            if (func.equals(Constants.ERROR)) {

                String message = getString(R.string.failtoupdate_profile);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActiviy(), R.string.failtoupdate_profile, message);
            }

            else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.errorDialog(getActiviy(), R.string.failtoupdate_profile, getString(R.string.no_internet_connection));


            }
            else {
                if (IsSuccess) {

                    MemberModel user = result.data;
                    UtilityApp.setUserData(user);


                    GlobalData.successDialog(getActiviy(), getString(R.string.update_profile), getString(R.string.success_update));


                } else {
                    Toast(getString(R.string.failtoupdate_profile));

                }
            }


        }).updateProfile(memberModel);

    }
//
//    private void uploadPhoto(int userId, File photo) {
//
//
//        GlobalData.progressDialog(getActiviy(), R.string.upload_photo, R.string.please_wait_to_upload_photo);
//
//        new DataFeacher(true, (obj, func, IsSuccess) -> {
//            GlobalData.hideProgressDialog();
//
//            ResultAPIModel<GeneralModel> result = (ResultAPIModel<GeneralModel>) obj;
//            if (func.equals(Constants.ERROR)) {
//                String message = getString(R.string.failtoupdate_profile);
//                if (result != null && result.message != null) {
//                    message = result.message;
//                }
//                GlobalData.errorDialog(getActiviy(), R.string.failtoupdate_profile, message);
//            } else {
//                if (IsSuccess) {
//                    GlobalData.successDialog(getActiviy(), getString(R.string.upload_photo), getString(R.string.success_update));
//
//                } else {
//                    Toast(getString(R.string.failtoupdate_profile));
//
//                }
//            }
//
//
//        }).uploadPhoto(userId, photo);
//
//    }

    private void uploadPhoto(int userId, File photo) {

        Log.i("tag", "Log  userId " + userId);
        Log.i("tag", "Log  photo " + photo.getName());
        Log.i("tag", "Log  uploadPhoto " + photo.getName());

        GlobalData.progressDialog(getActiviy(), R.string.upload_photo, R.string.please_wait_to_upload_photo);

        if (UtilityApp.getLocalData().getShortname() != null) {
            country = UtilityApp.getLocalData().getShortname();

        } else {
            country = GlobalData.COUNTRY;

        }

        AndroidNetworking.upload(GlobalData.BetaBaseURL + country + GlobalData.grocery + GlobalData.Api + "v3/Account/UploadPhoto" + "?user_id=" + userId).addMultipartFile("file", photo)

                .addHeaders("ApiKey", Constants.api_key).build().
                setUploadProgressListener((bytesUploaded, totalBytes) -> {
                    // do anything with progress
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                GlobalData.hideProgressDialog();
                Log.i("tag", "Log data response " + response);

                String message = getString(R.string.failtoupdate_profile);

                if (response.equals(Constants.ERROR)) {
                    GlobalData.errorDialog(getActiviy(), R.string.failtoupdate_profile, message);
                } else {

                    String data = null;
                    try {
                        JSONObject jsonObject = response;
                        int status = jsonObject.getInt("status");
                        if (status == 200) {
                            data = jsonObject.getString("data");

                            memberModel.setProfilePicture(data);
                            UtilityApp.setUserData(memberModel);
                            Log.i("tag", "Log data result " + data);
                            GlobalData.successDialog(getActiviy(), getString(R.string.upload_photo), getString(R.string.success_update));

                        } else {
                            message = jsonObject.getString("message");
                            GlobalData.errorDialog(getActiviy(), R.string.failtoupdate_profile, message);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onError(ANError error) {
                // handle error
            }
        });
    }


}