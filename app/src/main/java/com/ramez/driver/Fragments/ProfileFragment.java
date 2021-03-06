package com.ramez.driver.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.ramez.driver.databinding.ActivityEditProfileBinding;
import com.ramez.driver.databinding.FragmentHomeBinding;
import com.ramez.driver.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;


public class ProfileFragment extends FragmentBase {
    MemberModel memberModel;
    PickImageDialog pickImageDialog;
    int REQUEST_PICK_IMAGE = 11;
    int userId;
    File selectedPhotoFil = null;
    private FragmentProfileBinding binding;
    private String country;
    private ChoosePhotoHelper choosePhotoHelper;
    private Uri selectedPhotoUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        if (UtilityApp.isLogin()) {
            if (UtilityApp.getUserData() != null) {
                memberModel = UtilityApp.getUserData();
                initData(memberModel);
            }
        }

        memberModel = UtilityApp.getUserData();
        userId = memberModel.getId();


        binding.saveBut.setOnClickListener(view1 -> {

            updateProfile();

        });

        binding.editPhotoBut.setOnClickListener(view1 -> {

            openPicker();

        });
        return binding.getRoot();

    }


    public void getUserData(int user_id) {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<ProfileData> result = (ResultAPIModel<ProfileData>) obj;
            String message = getString(R.string.fail_to_get_data);

            if (isVisible()) {

                if (IsSuccess) {

                    MemberModel memberModel = UtilityApp.getUserData();
                    memberModel.setName(result.data.getUserName());
                    memberModel.setEmail(result.data.getEmail());
                    memberModel.setProfilePicture(result.data.getProfilePicture());
                    initData(memberModel);
                    UtilityApp.setUserData(memberModel);


                }
            }


        }).getUserDetails(user_id);
    }

    private void initData(MemberModel memberModel) {
        userId = memberModel.getId();
        binding.userNameTv.setText(memberModel.getName());
        binding.edtUserName.setText(memberModel.getName());
        binding.etEmail.setText(memberModel.getEmail());
        binding.edtPhoneNumber.setText(memberModel.getMobileNumber());
        Log.i("tag", "Log getName" + memberModel.getName());
        Log.i("tag", "Log getEmail" + memberModel.getEmail());

        Glide.with(getActivity()).asBitmap().load(memberModel.getProfilePicture()).placeholder(R.drawable.avatar).into(binding.userImg);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (choosePhotoHelper != null)
            choosePhotoHelper.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PICK_IMAGE) {

            try {

                if (data.getData() != null) {
                    selectedPhotoUri = data.getData();

                    selectedPhotoFil = FileUtil.from(getActivity(), selectedPhotoUri);

                    Glide.with(getActivity()).asBitmap().load(selectedPhotoUri).placeholder(R.drawable.avatar).into(binding.userImg);
                    uploadPhoto(userId, selectedPhotoFil);

                }


            } catch (Exception e) {
                e.printStackTrace();
                GlobalData.errorDialog(getActivity(), R.string.upload_photo, getString(R.string.textTryAgain));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void pickImage() {
        pickImageDialog = new PickImageDialog(getActivity(), (obj, func, IsSuccess) -> {

            if (func.equals(Constants.CAPTURE)) {

                choosePhotoHelper = ChoosePhotoHelper.with(getActivity()).asUri().build(uri -> {

                    selectedPhotoUri = uri;
                    try {

                        selectedPhotoFil = FileUtil.from(getActivity(), uri);

                        Glide.with(getActivity()).asBitmap().load(selectedPhotoUri).placeholder(R.drawable.avatar).into(binding.userImg);

                        selectedPhotoFil = new Compressor(getActivity()).compressToFile(selectedPhotoFil);

                        Log.i("tag", "Log selectedPhotoFil  " + selectedPhotoFil);
                        Log.i("tag", "Log uri " + uri);

                        uploadPhoto(userId, selectedPhotoFil);

                    } catch (IOException e) {
                        e.printStackTrace();
                        GlobalData.errorDialog(getActivity(), R.string.upload_photo, getString(R.string.textTryAgain));

                    }

                });

                choosePhotoHelper.takePhoto();


            } else if (func.equals(Constants.PICK)) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.selectImage)), REQUEST_PICK_IMAGE);

            }

        });
        pickImageDialog.show();

    }

    private final void openPicker() {
        try {
            PermissionCompat.Builder builder = new PermissionCompat.Builder((getActivity()));
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

        GlobalData.progressDialog(getActivity(), R.string.update_profile, R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();

            LoginResultModel result = (LoginResultModel) obj;
            if (func.equals(Constants.ERROR)) {

                String message = getString(R.string.failtoupdate_profile);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActivity(), R.string.failtoupdate_profile, message);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.errorDialog(getActivity(), R.string.failtoupdate_profile, getString(R.string.no_internet_connection));


            } else {
                if (IsSuccess) {

                    MemberModel user = result.data;
                    UtilityApp.setUserData(user);


                    GlobalData.successDialog(getActivity(), getString(R.string.update_profile), getString(R.string.success_update));


                } else {
                    Toast(getString(R.string.failtoupdate_profile));

                }
            }


        }).updateProfile(memberModel);

    }


    private void uploadPhoto(int userId, File photo) {

        Log.i("tag", "Log  userId " + userId);

        GlobalData.progressDialog(getActivity(), R.string.upload_photo, R.string.please_wait_to_upload_photo);

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
                    GlobalData.errorDialog(getActivity(), R.string.failtoupdate_profile, message);
                } else {

                    String data = null;

                    try {
                        JSONObject jsonObject = response;
                        int status = jsonObject.getInt("status");
                        if (status == 200) {
                            data = jsonObject.getString("data");
                            Log.i("tag", "Log data result " + data);

                            if (data != null) {
                                memberModel.setProfilePicture(data);
                                UtilityApp.setUserData(memberModel);
                                GlobalData.successDialog(getActivity(), getString(R.string.upload_photo), getString(R.string.success_update));

                            }


                        } else {
                            message = jsonObject.getString("message");
                            GlobalData.errorDialog(getActivity(), R.string.failtoupdate_profile, message);

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