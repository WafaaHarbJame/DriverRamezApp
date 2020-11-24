package com.ramez.ramez.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.databinding.ActivityChangeLanguageBinding;

public class ChangeLanguageActivity extends ActivityBase {
    private ActivityChangeLanguageBinding binding;
    private boolean toggleShowLang = false;


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
            GoToChooseCity();

        });

        binding.containerEnglish.setOnClickListener(view1 -> {
            binding.imgEnglishTick.setVisibility(View.VISIBLE);
            binding.imgArabicTick.setVisibility(View.INVISIBLE);
            GoToChooseCity();

        });

        binding.toolBar.homeBtn.setVisibility(View.GONE);

    }

    private void GoToChooseCity() {
        Intent intent = new Intent(getActiviy(), ChooseCityActivity.class);
        startActivity(intent);
        finish();

    }


}