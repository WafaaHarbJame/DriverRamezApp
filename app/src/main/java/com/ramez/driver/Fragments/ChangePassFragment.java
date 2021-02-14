package com.ramez.driver.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.form_validation.rule.EqualRule;
import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.ramez.driver.Activities.SignInActivity;
import com.ramez.driver.Activities.SplashScreenActivity;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.OtpModel;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.GeneralModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.NumberHandler;
import com.ramez.driver.databinding.FragmentChnagePassBinding;

import es.dmoral.toasty.Toasty;


public class ChangePassFragment extends FragmentBase {
    private FragmentChnagePassBinding binding;
    MemberModel memberModel;
    String mobileStr = "";
    Boolean reset_account = false;
    int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChnagePassBinding.inflate(inflater, container, false);


        Bundle bundle = getActivityy().getIntent().getExtras();
        if (bundle != null) {
            mobileStr = getActivityy().getIntent().getStringExtra(Constants.KEY_MOBILE);
            reset_account = getActivityy().getIntent().getBooleanExtra(Constants.reset_account, false);

        }


        if (reset_account) {
            binding.edtPassword.setVisibility(View.GONE);
            binding.oldView.setVisibility(View.GONE);
            binding.oldShowPassBut.setVisibility(View.GONE);
        }


        binding.oldShowPassBut.setOnClickListener(view1 -> {

            if (binding.edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility_off);
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility);
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });



        binding.newShowPassBut.setOnClickListener(view1 -> {

            if (binding.edtNewPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility_off);
                binding.edtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility);
                binding.edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


        binding.confirmShowPassBut.setOnClickListener(view1 -> {

            if (binding.edtConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility_off);
                binding.edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view1)).setImageResource(R.drawable.ic_visibility);
                binding.edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        binding.confirmBut.setOnClickListener(view1 -> {

            if (reset_account) {
                if (isValidFormForget()) {
                    UpdatePassword();

                }
            } else {
                if (isValidForm()) ChangePassword();

            }

        });





        return binding.getRoot();

    }


    public void signOut(MemberModel memberModel) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.fail_to_sign_out);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_sign_out);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActivityy(), R.string.no_internet_connection);
            } else {

                if (IsSuccess) {

                    UtilityApp.logOut();
                    GlobalData.Position = 0;
                    Intent intent = new Intent(getActivityy(), SplashScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast(R.string.fail_to_sign_out);
                }
            }

        }).logOut(memberModel);

    }




    public void ChangePassword() {
        MemberModel memberModel;

        final String oldPasswordStr = NumberHandler.arabicToDecimal(binding.edtPassword.getText().toString());
        final String newPasswordStr = NumberHandler.arabicToDecimal(binding.edtNewPassword.getText().toString());
        if (UtilityApp.getUserData() != null) {
            memberModel = UtilityApp.getUserData();

        } else {
            memberModel = new MemberModel();

        }
        memberModel.setPassword(oldPasswordStr);
        memberModel.setNew_password(newPasswordStr);

        GlobalData.progressDialog(getActivityy(), R.string.text_registration_change_password, R.string.please_wait_sending);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            OtpModel result = (OtpModel) obj;

            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_change_password);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActivityy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {
                    if (result.getStatus() == 200) {

                        if (UtilityApp.getUserData() != null) {
                            MemberModel memberModel1 = UtilityApp.getUserData();
                            signOut(memberModel1);
                        } else {

                            Intent intent = new Intent(getActivityy(), SplashScreenActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }


                    } else {

                        String message = getString(R.string.fail_to_change_password);
                        if (result != null && result.getMessage() != null) {
                            message = result.getMessage();
                        }

                        GlobalData.errorDialog(getActivityy(), R.string.reset_pass, message);
                    }


                } else {
                    Toast(R.string.fail_to_change_password);
                }
            }

        }).ChangePasswordHandle(memberModel);
    }

    public void UpdatePassword() {

        final String newPasswordStr = NumberHandler.arabicToDecimal(binding.edtNewPassword.getText().toString());
        MemberModel memberModel = new MemberModel();

        memberModel.setPassword(newPasswordStr);
        memberModel.setNew_password(newPasswordStr);
        memberModel.setMobileNumber(mobileStr);

        GlobalData.progressDialog(getActivityy(), R.string.text_registration_change_password, R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GeneralModel result = (GeneralModel) obj;

            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_change_password);
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActivityy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {


                    if (result.getStatus() == 200) {
                        Toasty.success(getActivityy(), getString(R.string.success_update), Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(getActivityy(), SignInActivity.class);
                        startActivity(intent);

                    } else {

                        String message = getString(R.string.fail_to_change_password);
                        if (result != null && result.getMessage() != null) {
                            message = result.getMessage();
                        }

                        GlobalData.errorDialog(getActivityy(), R.string.reset_pass, message);
                    }


                } else {
                    Toast(R.string.fail_to_change_password);
                }
            }

        }).UpdatePasswordHandle(memberModel);
    }


    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.addField(binding.edtPassword, new NonEmptyRule(R.string.old_pass)).
                addField(binding.edtNewPassword, new NonEmptyRule(R.string.new_pass)).addField(binding.edtConfirmPassword, new NonEmptyRule(R.string.enter_confirm_password)).addField(binding.edtConfirmPassword, new EqualRule(String.valueOf(binding.edtNewPassword.getText()), R.string.password_confirm_not_match)).validate();
    }


    private final boolean isValidFormForget() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.
                addField(binding.edtNewPassword, new NonEmptyRule(R.string.new_pass)).addField(binding.edtConfirmPassword, new NonEmptyRule(R.string.enter_confirm_password)).addField(binding.edtConfirmPassword, new EqualRule(String.valueOf(binding.edtNewPassword.getText()), R.string.password_confirm_not_match)).validate();


    }





}