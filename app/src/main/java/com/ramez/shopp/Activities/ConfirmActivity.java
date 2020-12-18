package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.OtpModel;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.ActivityConfirmBinding;

public class ConfirmActivity extends ActivityBase {
    ActivityConfirmBinding binding;
    String mobileStr;

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
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mobileStr=getIntent().getStringExtra(Constants.KEY_MOBILE);

        }
        binding.confirmBut.setOnClickListener(view1 -> {
            //String mobileStr=UtilityApp.getUserData().getMobileNumber();
            String  codeStr= NumberHandler.arabicToDecimal(binding.codeTxt.getText().toString());

            VerifyOtp(mobileStr,codeStr);


        });


    }

    public void VerifyOtp(String mobile,String otp) {
        GlobalData.progressDialog(
                getActiviy(),
                R.string.confirm_code,
                R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_sen_otp);
            } else {
                if (IsSuccess) {
                    OtpModel otpModel = (OtpModel) obj;
                    Log.i("TAG", "Log otp " + otpModel.getMessage());
                    Intent intent = new Intent(getActiviy(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                } else {
                    Toast(R.string.fail_to_sen_otp);

                }
            }

        }).VerifyOtpHandle(mobile,otp);
    }

}