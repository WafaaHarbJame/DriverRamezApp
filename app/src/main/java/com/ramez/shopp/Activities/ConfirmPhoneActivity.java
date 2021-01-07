package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.OtpModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.ActivityConformPhoneBinding;

public class ConfirmPhoneActivity extends ActivityBase {
    private ActivityConformPhoneBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConformPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.confirmBut.setOnClickListener(view1 -> {
            if (isValidForm()) {
                String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
                MemberModel memberModel=new MemberModel();
                memberModel.setUserType(Constants.user_type);
                memberModel.setMobileNumber(mobileStr);
                ForgetPassword(memberModel);

            }


        });



        setTitle(R.string.forget_pass);



    }

    private void GoToConfirm() {
        Intent intent = new Intent(getActiviy(), ConfirmActivity.class);
        startActivity(intent);
        finish();

    }

    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();
        return formValidator.addField(binding.edtPhoneNumber, new NonEmptyRule(getString(R.string.enter_phone_number))).validate();
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
                    GoToConfirm();


                } else {
                    Toast(R.string.fail_to_sen_otp);
                }
            }

        }).sendOpt(mobile);
    }

    public void ForgetPassword(MemberModel memberModel) {
        GlobalData.progressDialog(
                getActiviy(),
                R.string.forget_pass,
                R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_sen_otp);
            }

            else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActiviy(), R.string.no_internet_connection);
            }
            else {
                if (IsSuccess) {
                    SendOtp(memberModel.getMobileNumber());
                    OtpModel otpModel = (OtpModel) obj;
                    Log.i("TAG", "Log otp " + otpModel.getData());


                } else {
                    Toast(R.string.fail_to_sen_otp);
                }
            }

        }).ForgetPasswordHandle(memberModel);
    }


}