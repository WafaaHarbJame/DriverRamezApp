package com.ramez.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;

import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.HomeActivity;
import com.ramez.driver.Models.LocalModel;
import com.ramez.driver.Models.LoginResultModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.NumberHandler;
import com.ramez.driver.databinding.ActivitySignInBinding;

public class SignInActivity extends ActivityBase {
    final String TAG = "Log";
    String FCMToken;
    String country_name = "BH";
    String city_id = "7263";
    LocalModel localModel;
    int storeId, userId;
    MemberModel user;
    int role_id = 4;
    private ActivitySignInBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle(R.string.common_signin_button_text);
        localModel = UtilityApp.getLocalData();

        firebaseAuth = FirebaseAuth.getInstance();
        getDeviceToken();


        binding.pickerRB.setOnClickListener(view1 -> {
            role_id = 6;

        });


        binding.DRIVERrb.setOnClickListener(view1 -> {
            role_id = 4;

        });


        binding.toolBar.backBtn.setVisibility(View.GONE);

        binding.edtPassword.setTransformationMethod(new PasswordTransformationMethod());

        binding.textForgotPassword.setOnClickListener(view1 -> {
            startRestPassword();
        });


        binding.loginBut.setOnClickListener(view1 -> {
            if (isValidForm()) {
                loginUser();

            }


        });


        binding.showPassBut.setOnClickListener(view1 -> {

            if (binding.edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility_off);
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility);
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


    }

    private void loginUser() {

        final String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
        final String passwordStr = NumberHandler.arabicToDecimal(binding.edtPassword.getText().toString());

        final MemberModel memberModel = new MemberModel();
        memberModel.setMobileNumber(mobileStr);
        memberModel.setPassword(passwordStr);
        memberModel.setDeviceType(Constants.deviceType);
        memberModel.setDeviceToken(FCMToken);
        memberModel.setDeviceId(UtilityApp.getUnique());
        memberModel.setRole_id(role_id);

        if (role_id == 6) {
            memberModel.setUserType(Constants.picker_type);

        } else if (role_id == 4) {
            memberModel.setUserType(Constants.driver_type);


        }

        GlobalData.progressDialog(getActiviy(), R.string.text_login_login, R.string.please_wait_login);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            LoginResultModel result = (LoginResultModel) obj;

            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }

                GlobalData.errorDialog(getActiviy(), R.string.text_login_login, message);
            } else if (func.equals(Constants.FAIL)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActiviy(), R.string.text_login_login, message);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActiviy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {

                    if (result.getStatus() == 106) {
                        Intent intent = new Intent(getActiviy(), ConfirmActivity.class);
                        intent.putExtra(Constants.KEY_MOBILE, mobileStr);
                        intent.putExtra(Constants.verify_account, true);
                        intent.putExtra(Constants.KEY_PASSWORD, passwordStr);
                        startActivity(intent);

                    } else if (result.getStatus() == 0) {

                        String message = getString(R.string.fail_signin);
                        if (result != null && result.getMessage() != null) {
                            message = result.getMessage();
                        }

                        GlobalData.errorDialog(getActiviy(), R.string.text_login_login, message);
                    } else if (result.getStatus() == 200) {
                        MemberModel user = result.data;
                        user.setRole_id(role_id);
                        UtilityApp.setUserData(user);
                        if (UtilityApp.getUserData() != null) {
                            UpdateToken();
                        }
                    } else {

                        String message = getString(R.string.fail_signin);
                        if (result != null && result.getMessage() != null) {
                            message = result.getMessage();
                        }

                        GlobalData.errorDialog(getActiviy(), R.string.text_login_login, message);
                    }


                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).loginHandle(memberModel);

    }


    private void startRestPassword() {

        Intent intent = new Intent(getActiviy(), ConfirmPhoneActivity.class);
        intent.putExtra(Constants.reset_account, true);
        startActivity(intent);
    }


    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.addField(binding.edtPhoneNumber, new NonEmptyRule(getString(R.string.enter_phone_number))).addField(binding.edtPassword, new NonEmptyRule(R.string.enter_password)).validate();


    }

    private void getDeviceToken() {

        FCMToken = UtilityApp.getFCMToken();
        if (FCMToken == null) {
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                FCMToken = token;
            });

        }

    }

    public void startMain() {
        Intent intent = new Intent(getActiviy(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }


    private void UpdateToken() {

        GlobalData.progressDialog(getActiviy(), R.string.text_login_login, R.string.please_wait_login);
        MemberModel memberModel = UtilityApp.getUserData();
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            ResultAPIModel<String> result = (ResultAPIModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.message != null) {
                    message = result.message;
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


}




