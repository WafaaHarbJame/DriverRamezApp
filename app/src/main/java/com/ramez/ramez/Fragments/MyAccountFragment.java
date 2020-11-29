package com.ramez.ramez.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.ramez.Activities.AboutActivity;
import com.ramez.ramez.Activities.ChangePassActivity;
import com.ramez.ramez.Activities.ConditionActivity;
import com.ramez.ramez.Activities.FavoriteActivity;
import com.ramez.ramez.Activities.MyOrderActivity;
import com.ramez.ramez.Activities.RatingActivity;
import com.ramez.ramez.Activities.SplashScreenActivity;
import com.ramez.ramez.Activities.TermsActivity;
import com.ramez.ramez.Activities.WelcomeActivity;
import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.R;
import com.ramez.ramez.Utils.ActivityHandler;
import com.ramez.ramez.databinding.FragmentCategoryBinding;
import com.ramez.ramez.databinding.FragmentMyAccountBinding;

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
        return view;
    }

    private void startOrderActivity() {
        Intent intent=new Intent(getActivityy(), MyOrderActivity.class);
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