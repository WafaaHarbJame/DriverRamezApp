package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ramez.shopp.Activities.AboutActivity;
import com.ramez.shopp.Activities.AddressActivity;
import com.ramez.shopp.Activities.ChangeCityBranchActivity;
import com.ramez.shopp.Activities.ChangeLangCurrencyActivity;
import com.ramez.shopp.Activities.ChangePassActivity;
import com.ramez.shopp.Activities.ConditionActivity;
import com.ramez.shopp.Activities.ContactSupportActivity;
import com.ramez.shopp.Activities.EditProfileActivity;
import com.ramez.shopp.Activities.FavoriteActivity;
import com.ramez.shopp.Activities.MyOrderActivity;
import com.ramez.shopp.Activities.RatingActivity;
import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.Activities.SplashScreenActivity;
import com.ramez.shopp.Activities.TermsActivity;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.CheckLoginDialog;
import com.ramez.shopp.Dialogs.ConfirmDialog;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.ActivityHandler;
import com.ramez.shopp.databinding.DialogCheckLoginBinding;
import com.ramez.shopp.databinding.FragmentMyAccountBinding;

public class MyAccountFragment extends FragmentBase {
    private FragmentMyAccountBinding binding;
    boolean isLogin=false;
    private CheckLoginDialog checkLoginDialog;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        isLogin=UtilityApp.isLogin();


        if(UtilityApp.isLogin()){
            binding.logoutText.setText(R.string.logout);
        }
        else {
            binding.logoutText.setText(R.string.text_login_login);

        }


        if(UtilityApp.isLogin()){
            binding.editProfileBu.setVisibility(View.VISIBLE);
            MemberModel memberModel=UtilityApp.getUserData();
            binding.usernameTV.setText(memberModel.getName());
            binding.emailTv.setText(memberModel.getEmail());


        }
        else {
            binding.editProfileBu.setVisibility(View.GONE);


        }

        binding.termsBtn.setOnClickListener(view1 -> {
            startTermsActivity();
        });


        binding.conditionsBtn.setOnClickListener(view1 -> {
            startConditionActivity();

        });

        binding.aboutUsBtn.setOnClickListener(view1 -> {
            startAboutActivity();

        });
        binding.rateBtn.setOnClickListener(view1 -> {

            if(isLogin){
                startRateAppActivity();
            }
            else {
                showDialog();
            }
        });


        binding.shareBtn.setOnClickListener(view1 -> {
            ActivityHandler.shareTextUrl(getActivityy(),getString(R.string.app_name),"","");

        });

        binding.changePassBtn.setOnClickListener(view1 -> {
            if(isLogin){
                startChangeActivity();
            }
            else {
                showDialog();
            }



        });

        binding.favProductBut.setOnClickListener(view1 -> {
            if(isLogin){
                startFavProductActivity();
            }
            else {
                 showDialog();
            }


        });

        binding.myOrderBut.setOnClickListener(view1 -> {

            if(isLogin){
                startOrderActivity();
            }
            else {
                showDialog();
            }


        });

        binding.editProfileBu.setOnClickListener(view1 -> {
            startEditProfileActivity();

        });

        binding.addressBtn.setOnClickListener(view1 -> {
            if(isLogin){
                startAddressActivity();
            }
            else {
                showDialog();
            }



        });
        binding.changeCityBtn.setOnClickListener(view1 -> {
            if(isLogin){
                startChangeBranch();
            }
            else {
                showDialog();
            }


        });

        binding.changeLangBtn.setOnClickListener(view1 -> {
            startChangeLang();
        });

        binding.SupportBtn.setOnClickListener(view1 -> {
            if(isLogin){
                startSupport();
            }
            else {
                showDialog();
            }


        });

        binding.logoutBtn.setOnClickListener(view1 -> {

            if(UtilityApp.isLogin()){
                MemberModel memberModel=UtilityApp.getUserData();
                signOut(memberModel);
            }
            else {
                startLogin();

            }

        });
        return view;
    }

    private void showDialog() {
        checkLoginDialog=new CheckLoginDialog(getActivityy(),R.string.please_login,R.string.text_login_login,R.string.register,null,null);
        checkLoginDialog.show();
    }

    private void startLogin() {
        Intent intent = new Intent(getActivityy(), RegisterLoginActivity.class);
        intent.putExtra(Constants.LOGIN,true);
        startActivity(intent);
    }

    private void startSupport() {
        Intent intent=new Intent(getActivityy(), ContactSupportActivity.class);
        startActivity(intent);
    }

    private void startChangeLang() {
        Intent intent=new Intent(getActivityy(), ChangeLangCurrencyActivity.class);
        startActivity(intent);
    }

    private void startChangeBranch() {
        Intent intent=new Intent(getActivityy(), ChangeCityBranchActivity.class);
        startActivity(intent);
    }

    private void startAddressActivity() {
        Intent intent=new Intent(getActivityy(), AddressActivity.class);
        startActivity(intent);
    }

    private void startOrderActivity() {
        Intent intent=new Intent(getActivityy(), MyOrderActivity.class);
        startActivity(intent);
    }
    private void startEditProfileActivity() {
        Intent intent=new Intent(getActivityy(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void startTermsActivity(){
        Intent intent=new Intent(getActivityy(), TermsActivity.class);
        startActivity(intent);
    }

    private void startConditionActivity(){
        Intent intent=new Intent(getActivityy(), ConditionActivity.class);
        startActivity(intent);
    }
    private void startAboutActivity(){
        Intent intent=new Intent(getActivityy(), AboutActivity.class);
        startActivity(intent);
    }

    private void startRateAppActivity(){
        Intent intent=new Intent(getActivityy(), RatingActivity.class);
        startActivity(intent);
    }
    private void startChangeActivity(){
        Intent intent=new Intent(getActivityy(), ChangePassActivity.class);
        startActivity(intent);
    }
    private void startFavProductActivity(){
        Intent intent=new Intent(getActivityy(), FavoriteActivity.class);
        startActivity(intent);
    }
    public void signOut(MemberModel memberModel) {
        ConfirmDialog.Click click = new ConfirmDialog.Click() {
            @Override
            public void click() {
                new DataFeacher(getActivity(), (obj, func, IsSuccess) -> {
                    if (func.equals(Constants.ERROR)) {
                        Toast(R.string.error_in_data);
                    } else if (func.equals(Constants.FAIL)) {
                        Toast(R.string.fail_to_get_data);
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
        };

        new ConfirmDialog(getActivityy(), R.string.want_to_signout, R.string.ok, R.string.cancel_label, click, null);

    }

}