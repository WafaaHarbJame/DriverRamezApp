package com.ramez.shopp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.github.dhaval2404.form_validation.rule.EmailRule;
import com.github.dhaval2404.form_validation.rule.EqualRule;
import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ramez.shopp.Activities.ChooseCityActivity;
import com.ramez.shopp.Activities.ConfirmActivity;
import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.Activities.SplashScreenActivity;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.OtpModel;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.ConfirmDialog;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.LoginResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.Utils.SharedPManger;
import com.ramez.shopp.databinding.FragmentRegisterBinding;

public class RegisterFragment extends FragmentBase {
    String FCMToken;
    String CountryCode = "+966";
    boolean select_country = false;
    String country_name = "BH";
    String city_id = "7263";
    String prefix = "973";
    SharedPManger sharedPManger;
    private FragmentRegisterBinding binding;
    private ViewPager viewPager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewPager = container.findViewById(R.id.viewPager);

        getDeviceToken();
        sharedPManger = new SharedPManger(getActivityy());

        binding.loginBut.setOnClickListener(view1 -> {
            startLogin();


        });


        binding.registerBut.setOnClickListener(view1 -> {
            if (isValidForm()) {
                RegisterUser();

            }
        });
        return view;
    }

    private void RegisterUser() {
        final String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
        final String passwordStr = NumberHandler.arabicToDecimal(binding.edtPassword.getText().toString());
        final String nameStr = NumberHandler.arabicToDecimal(binding.edtFirstName.getText().toString());
        final String emailStr = NumberHandler.arabicToDecimal(binding.edtEmail.getText().toString());
        LocalModel localModel = UtilityApp.getLocalData();

        country_name = localModel.getShortname();
        CountryCode = String.valueOf(localModel.getPhonecode());
        city_id = localModel.getCityId();

        MemberModel memberModel = new MemberModel();
        memberModel.setMobileNumber(mobileStr);
        memberModel.setPassword(passwordStr);
        memberModel.setName(nameStr);
        memberModel.setEmail(emailStr);
        memberModel.setCity(city_id);
        memberModel.setCountry(country_name);
        memberModel.setDeviceToken(FCMToken);
        memberModel.setDeviceId(UtilityApp.getUnique());
        memberModel.setDeviceType(Constants.deviceType);
        memberModel.setPrefix(CountryCode);
        memberModel.setUserType(Constants.user_type);

        GlobalData.progressDialog(getActivityy(), R.string.register, R.string.please_wait_register);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            LoginResultModel result = (LoginResultModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_register);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActivityy(), R.string.fail_register, message);
            } else {
                if (IsSuccess) {
                    Log.i("TAG", "Log otp " + result.getOtp());
                    MemberModel user = result.data;
                    user.setRegisterType(Constants.BY_PHONE);
                    UtilityApp.setUserData(user);
                    SendOtp(mobileStr);
                } else {
                    Toast(getString(R.string.fail_register));

                }
            }


        }).RegisterHandle(memberModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startLogin() {
        viewPager.setCurrentItem(1);

    }

    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.addField(binding.edtFirstName, new NonEmptyRule(R.string.enter_name)).addField(binding.edtEmail, new NonEmptyRule(getString(R.string.enter_email))).addField(binding.edtEmail, new EmailRule(R.string.enter_valid_email)).addField(binding.edtPhoneNumber, new NonEmptyRule(R.string.enter_phone_please)).addField(binding.edtPassword, new NonEmptyRule(R.string.enter_password)).addField(binding.edtConfirmPassword, new NonEmptyRule(R.string.enter_confirm_password)).addField(binding.edtConfirmPassword, new EqualRule(String.valueOf(binding.edtPassword.getText()), R.string.password_confirm_not_match)).validate();
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

    private void GoToChooseCity() {
        Intent intent = new Intent(getActivityy(), ChooseCityActivity.class);
        startActivity(intent);
        getActivityy().finish();

    }


    public void SendOtp(String mobile) {
        final String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_sen_otp);
            } else {
                if (IsSuccess) {
                    OtpModel otpModel = (OtpModel) obj;
                    Log.i("TAG", "Log otp " + otpModel.getData());
                    Intent intent = new Intent(getActivityy(), ConfirmActivity.class);
                    intent.putExtra(Constants.KEY_MOBILE, mobileStr);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                } else {
                    Toast(R.string.fail_to_sen_otp);
                }
            }

        }).sendOpt(mobile);
    }

}