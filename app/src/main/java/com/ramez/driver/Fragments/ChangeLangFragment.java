package com.ramez.driver.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ramez.driver.Activities.SplashScreenActivity;
import com.ramez.driver.Adapter.LangAdapter;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.CountryModel;
import com.ramez.driver.Models.LanguageModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentChangeLangBinding;

import java.util.ArrayList;


public class ChangeLangFragment extends FragmentBase implements LangAdapter.OnLangClick {
    FragmentChangeLangBinding binding;
    ArrayList<LanguageModel> langList;
    ArrayList<CountryModel> countries;
    int selectedLangId;
    private LangAdapter langAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean toggleButton = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChangeLangBinding.inflate(inflater, container, false);

        langList = new ArrayList<>();
        countries = new ArrayList<>();

        langList.add(new LanguageModel(1, getString(R.string.text_language_arabic), getString(R.string.ar_lang)));
        langList.add(new LanguageModel(2, getString(R.string.text_langiage_english), getString(R.string.en_lang)));


        if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
            selectedLangId = 1;
        } else {
            selectedLangId = 2;
        }


        linearLayoutManager = new LinearLayoutManager(getActivityy());
        binding.recycler.setLayoutManager(linearLayoutManager);

        binding.chooseLangTv.setOnClickListener(view1 -> {
            binding.langContainer.setVisibility(View.VISIBLE);
            binding.chooseLangTv.setVisibility(View.GONE);

        });


        binding.chooseLangTv.setOnClickListener(view1 -> {

            toggleButton = !toggleButton;

            if (toggleButton) {
                binding.langContainer.setVisibility(View.VISIBLE);
                binding.langLY.setBackground(ContextCompat.getDrawable(getActivityy(), R.drawable.lang_style));
            } else {
                binding.langContainer.setVisibility(View.GONE);
                binding.langLY.setBackground(ContextCompat.getDrawable(getActivityy(), R.drawable.spinner_style));
            }

        });


        countries.add(new CountryModel(4, getString(R.string.Oman), getString(R.string.oman_shotname), 968, "OMR", Constants.three, R.drawable.ic_flag_oman));
        countries.add(new CountryModel(17, getString(R.string.Bahrain), getString(R.string.bahrain_shotname), 973, "BHD", Constants.three, R.drawable.ic_flag_behrain));
        countries.add(new CountryModel(117, getString(R.string.Kuwait), getString(R.string.Kuwait_shotname), 965, "KWD", Constants.three, R.drawable.ic_flag_kuwait));
        countries.add(new CountryModel(178, getString(R.string.Qatar), getString(R.string.Qatar_shotname), 974, "QAR", Constants.two, R.drawable.ic_flag_qatar));
        countries.add(new CountryModel(191, getString(R.string.Saudi_Arabia), getString(R.string.Saudi_Arabia_shortname), 191, "SAR", Constants.two, R.drawable.ic_flag_saudi_arabia));
        countries.add(new CountryModel(229, getString(R.string.United_Arab_Emirates), getString(R.string.United_Arab_Emirates_shotname), 971, "AED", Constants.two, R.drawable.ic_flag_uae));
        initAdapter();

        binding.saveBut.setOnClickListener(view1 -> {
            if (selectedLangId == 2) {
                UtilityApp.setLanguage(Constants.English);
            } else {
                UtilityApp.setLanguage(Constants.Arabic);

            }
            UtilityApp.setAppLanguage();

            Toast(R.string.change_success);
            Intent intent = new Intent(getActivityy(), SplashScreenActivity.class);
            startActivity(intent);


        });


        return binding.getRoot();

    }

    public void initAdapter() {

        langAdapter = new LangAdapter(getActivityy(), langList, this, selectedLangId);
        binding.recycler.setAdapter(langAdapter);


    }


    @Override
    public void onLangClicked(int position, LanguageModel languageModel) {
        selectedLangId = languageModel.getUserId();

    }


}