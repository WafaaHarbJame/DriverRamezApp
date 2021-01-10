package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.OtpModel;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.GeneralModel;
import com.ramez.shopp.Models.LoginResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.ActivityConfirmBinding;

public class ConfirmActivity extends ActivityBase {
    ActivityConfirmBinding binding;
    String mobileStr, passwordStr;
    Boolean verify_account;
    String FCMToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.confirm_phone));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        getDeviceToken();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobileStr = getIntent().getStringExtra(Constants.KEY_MOBILE);
            passwordStr = getIntent().getStringExtra(Constants.KEY_PASSWORD);
            verify_account = getIntent().getBooleanExtra(Constants.verify_account, false);

        }

        if (verify_account) {

            SendOtp(mobileStr);

        }
        binding.confirmBut.setOnClickListener(view1 -> {
            //String mobileStr=UtilityApp.getUserData().getMobileNumber();
            String codeStr = NumberHandler.arabicToDecimal(binding.codeTxt.getText().toString());
            VerifyOtp(mobileStr, codeStr);

        });

    }

    public void VerifyOtp(String mobile, String otp) {
        GlobalData.progressDialog(getActiviy(), R.string.confirm_code, R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_sen_otp);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActiviy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {
                    OtpModel otpModel = (OtpModel) obj;
                    Log.i("TAG", "Log otp " + otpModel.getMessage());

                    loginUser();


                } else {
                    Toast(R.string.fail_to_sen_otp);

                }
            }

        }).VerifyOtpHandle(mobile, otp);
    }


    public void SendOtp(String mobile) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_sen_otp);
            } else {
                if (IsSuccess) {
                    OtpModel otpModel = (OtpModel) obj;
                    Log.i("TAG", "Log otp " + otpModel.getData());

                } else {
                    Toast(R.string.fail_to_sen_otp);
                }
            }

        }).sendOpt(mobile);
    }


    private void loginUser() {

        final MemberModel memberModel = new MemberModel();
        memberModel.setMobileNumber(mobileStr);
        memberModel.setPassword(passwordStr);
        memberModel.setDeviceType(Constants.deviceType);
        memberModel.setDeviceToken(FCMToken);
        memberModel.setDeviceId(UtilityApp.getUnique());
        memberModel.setUserType(Constants.user_type);

        GlobalData.progressDialog(getActiviy(), R.string.confirm_code, R.string.please_wait_sending);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            LoginResultModel result = (LoginResultModel) obj;

            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }

                GlobalData.errorDialog(getActiviy(), R.string.fail_signin, message);
            } else if (func.equals(Constants.FAIL)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActiviy(), R.string.fail_signin, message);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActiviy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {

                    MemberModel user = result.data;
                    UtilityApp.setUserData(user);
                    // user.setRegisterType(Constants.BY_PHONE);

                    if (UtilityApp.getUserData() != null) {
                        UpdateToken();
                    }


                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).loginHandle(memberModel);

    }


    private void getDeviceToken() {

        FCMToken = UtilityApp.getFCMToken();
        if (FCMToken == null) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                FCMToken = instanceIdResult.getToken();
                UtilityApp.setFCMToken(FCMToken);
            });

        }

    }

    private void UpdateToken() {

        MemberModel memberModel = UtilityApp.getUserData();
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            GeneralModel result = (GeneralModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                Toast(message);

            } else {
                if (IsSuccess) {
                    startMain();

                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).UpdateTokenHandle(memberModel);

    }

    public void startMain() {
        Intent intent = new Intent(getActiviy(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActiviy().finish();

    }


}