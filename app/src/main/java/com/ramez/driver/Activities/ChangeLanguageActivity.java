package com.ramez.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.databinding.ActivityChangeLanguageBinding;


public class
ChangeLanguageActivity extends ActivityBase {
    private ActivityChangeLanguageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeLanguageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.chooseLangTv.setOnClickListener(view1 -> {
            binding.langContainer.setVisibility(View.VISIBLE);
            binding.chooseLangTv.setVisibility(View.GONE);

        });

        binding.containerArabic.setOnClickListener(view1 -> {
            binding.imgEnglishTick.setVisibility(View.INVISIBLE);
            binding.imgArabicTick.setVisibility(View.VISIBLE);
            UtilityApp.setLanguage(Constants.Arabic);
            UtilityApp.setAppLanguage();
            ChooseWelcomeActivity();


        });

        binding.containerEnglish.setOnClickListener(view1 -> {
            binding.imgEnglishTick.setVisibility(View.VISIBLE);
            binding.imgArabicTick.setVisibility(View.INVISIBLE);
            UtilityApp.setLanguage(Constants.English);
            UtilityApp.setAppLanguage();
            ChooseWelcomeActivity();



        });

        binding.toolBar.backBtn.setVisibility(View.GONE);

    }

    private void ChooseWelcomeActivity() {
        Intent intent = new Intent(getActiviy(), ChooseCityActivity.class);
        startActivity(intent);
//        finish();

    }


}