package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ramez.shopp.Activities.ConfirmPhoneActivity;
import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.ErrorMessagesDialog;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.LoginResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ResultAPIModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.FragmentLoginBinding;

public class LoginFragment extends FragmentBase {
    final String TAG = "Log";
    String FCMToken;
    private FragmentLoginBinding binding;
    private  ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
         viewPager=container.findViewById(R.id.viewPager);
        View view = binding.getRoot();

        getDeviceToken();


        binding.textForgotPassword.setOnClickListener(view1 -> {
            startRestPassword();
        });

        binding.skipButton.setOnClickListener(view1 -> {
            startMain();
        });
        binding.loginBut.setOnClickListener(view1 -> {
            if (isValidForm()) {
                loginUser();

            }


        });

        binding.registerBut.setOnClickListener(view1 -> {
            startLogin();


        });


        return view;
    }

    private void loginUser() {

        final String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
        final String passwordStr = NumberHandler.arabicToDecimal(binding.edtPassword.getText().toString());

        final MemberModel memberModel = new MemberModel();
        memberModel.setMobileNumber(mobileStr);
        memberModel.setPassword(passwordStr);
        memberModel.setDeviceType(Constants.deviceType);
        memberModel.setDeviceToken(FCMToken);
//        memberModel.setDeviceId(UtilityApp.getUnique());
        memberModel.setDeviceId(FCMToken);
        memberModel.setUserType(Constants.user_type);

        GlobalData.progressDialog(getActivityy(), R.string.text_login_login, R.string.please_wait_login);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            LoginResultModel result = (LoginResultModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActivityy(), R.string.fail_signin, message);
            } else {
                if (IsSuccess) {
                    MemberModel user = result.data;
                    UtilityApp.setUserData(user);
                    UpdateToken();
                    startMain();

                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).loginHandle(memberModel);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startRestPassword() {
        startActivity(new Intent(getActivityy(), ConfirmPhoneActivity.class));
    }

    private void startLogin() {
        viewPager.setCurrentItem(0);


    }

    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.addField(binding.edtPhoneNumber, new NonEmptyRule(getString(R.string.enter_phone_number))).addField(binding.edtPassword, new NonEmptyRule(R.string.enter_password)).validate();
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
    public  void startMain(){
        Intent intent = new Intent(getActivityy(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivityy().finish();

    }

    private void UpdateToken() {
        MemberModel memberModel=UtilityApp.getUserData();
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            LoginResultModel result = (LoginResultModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
            } else {
                if (IsSuccess) {


                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).UpdateTokenHandle(memberModel);

    }


}