package com.ramez.driver.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.driver.Activities.SignInActivity;
import com.ramez.driver.Activities.SplashScreenActivity;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.HomeActivity;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentLogoutBinding;


public class LogoutFragment extends FragmentBase {
    private FragmentLogoutBinding binding;
    MemberModel memberModel;
    int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        if (UtilityApp.isLogin()) {
            if (UtilityApp.getUserData() != null) {
                memberModel = UtilityApp.getUserData();
            }
        }

        memberModel = UtilityApp.getUserData();
        userId = memberModel.getId();
        binding.okBtn.setOnClickListener(view -> {
            if (UtilityApp.isLogin()) {
                MemberModel memberModel = UtilityApp.getUserData();
                signOut(memberModel);

            }
        });

        binding.cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivityy(), HomeActivity.class);
            startActivity(intent);
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
                    Intent intent = new Intent(getActivityy(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast(R.string.fail_to_sign_out);
                }
            }

        }).logOut(memberModel);

    }






}