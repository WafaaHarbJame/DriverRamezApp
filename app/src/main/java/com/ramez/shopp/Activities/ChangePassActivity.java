package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.dhaval2404.form_validation.rule.EqualRule;
import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.ActivityChangePassBinding;

public class ChangePassActivity extends ActivityBase {
    ActivityChangePassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePassBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.text_edit_profile_change_password));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.confirmBut.setOnClickListener(view1 -> {
            if(isValidForm()){
                ChangePassword();
            }


        });





    }



    public void ChangePassword() {

        final String oldPasswordStr = NumberHandler.arabicToDecimal(binding.edtPassword.getText().toString());
        final String newPasswordStr = NumberHandler.arabicToDecimal(binding.edtNewPassword.getText().toString());
        MemberModel memberModel = UtilityApp.getUserData();
        memberModel.setPassword(oldPasswordStr);
        memberModel.setNew_password(newPasswordStr);

        GlobalData.progressDialog(
                getActiviy(),
                R.string.text_registration_change_password,
                R.string.please_wait_sending);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast(R.string.error_in_data);
            } else if (func.equals(Constants.FAIL)) {
                Toast(R.string.fail_to_change_password);
            } else {
                if (IsSuccess) {
                    Intent intent=new Intent(this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast(R.string.fail_to_change_password);
                }
            }

        }).ChangePasswordHandle(memberModel);
    }

    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.addField(binding.edtPassword,
                new NonEmptyRule(R.string.enter_password)).
                addField(binding.edtNewPassword,
                new NonEmptyRule(R.string.enter_new_password))
                .addField(binding.edtConfirmPassword,
                new NonEmptyRule(R.string.enter_confirm_password)).addField(binding.edtConfirmPassword,
                new EqualRule(String.valueOf(binding.edtNewPassword.getText()),
                        R.string.password_confirm_not_match)).validate();
    }

}