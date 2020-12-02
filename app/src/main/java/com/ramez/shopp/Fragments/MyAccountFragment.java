package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.ramez.shopp.Activities.TermsActivity;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.ActivityHandler;
import com.ramez.shopp.databinding.FragmentMyAccountBinding;

public class MyAccountFragment extends FragmentBase {
    private FragmentMyAccountBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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
            startRateAppActivity();
        });


        binding.shareBtn.setOnClickListener(view1 -> {
            ActivityHandler.shareTextUrl(getActivityy(),getString(R.string.app_name),"","");

        });

        binding.changePassBtn.setOnClickListener(view1 -> {
            startChangeActivity();

        });

        binding.favProductBut.setOnClickListener(view1 -> {
            startFavProductActivity();

        });

        binding.myOrderBut.setOnClickListener(view1 -> {
            startOrderActivity();

        });

        binding.editProfileBu.setOnClickListener(view1 -> {
            startEditProfileActivity();

        });

        binding.addressBtn.setOnClickListener(view1 -> {
            startAddressActivity();

        });
        binding.changeCityBtn.setOnClickListener(view1 -> {
            startChangeBranch();
        });

        binding.changeLangBtn.setOnClickListener(view1 -> {
            startChangeLang();
        });

        binding.SupportBtn.setOnClickListener(view1 -> {
            startSupport();
        });
        return view;
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
}