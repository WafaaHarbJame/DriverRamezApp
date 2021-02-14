package com.ramez.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.OtpModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.NumberHandler;
import com.ramez.driver.databinding.ActivityConformPhoneBinding;

public class ConfirmPhoneActivity extends ActivityBase {
    private ActivityConformPhoneBinding binding;
    String mobileStr;
    boolean reset_account=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConformPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobileStr = getIntent().getStringExtra(Constants.KEY_MOBILE);
            reset_account = getIntent().getBooleanExtra(Constants.reset_account,false);


        }






        binding.confirmBut.setOnClickListener(view1 -> {
            if (isValidForm()) {
                String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());

                    MemberModel memberModel=new MemberModel();
                    ForgetPassword(memberModel);
                }


        });



        setTitle(R.string.forget_pass);



    }

    private void GoToConfirm() {
        String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
        Intent intent = new Intent(getActiviy(), ConfirmActivity.class);
        intent.putExtra(Constants.verify_account, false);
        intent.putExtra(Constants.reset_account, reset_account);
        intent.putExtra(Constants.KEY_MOBILE, mobileStr);
        startActivity(intent);
//        finish();

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


                } else {
                    Toast(R.string.fail_to_sen_otp);
                }
            }

        }).ForgetPasswordHandle(memberModel);
    }


}