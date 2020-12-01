package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.databinding.ActivityChangeLanguageBinding;

public class ChangeLanguageActivity extends ActivityBase {
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
            GoToChooseCity();

        });

        binding.containerEnglish.setOnClickListener(view1 -> {
            binding.imgEnglishTick.setVisibility(View.VISIBLE);
            binding.imgArabicTick.setVisibility(View.INVISIBLE);
            UtilityApp.setLanguage(Constants.English);
            UtilityApp.setAppLanguage();
            GoToChooseCity();

        });

        binding.toolBar.backBtn.setVisibility(View.GONE);

    }

    private void GoToChooseCity() {
        Intent intent = new Intent(getActiviy(), ChooseCityActivity.class);
        startActivity(intent);
        finish();

    }


}