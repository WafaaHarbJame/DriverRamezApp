package com.ramez.driver.Activities;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.driver.Adapter.CurrencyAdapter;
import com.ramez.driver.Adapter.LangAdapter;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.SettingModel;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.CountryModel;
import com.ramez.driver.Models.CurrencyModel;
import com.ramez.driver.Models.LanguageModel;
import com.ramez.driver.Models.LocalModel;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.ActivityChangeLangAndCurrencyBinding;

import java.util.ArrayList;

public class ChangeLangCurrencyActivity extends ActivityBase implements CurrencyAdapter.OnCurrencyClick, LangAdapter.OnLangClick {
    ActivityChangeLangAndCurrencyBinding binding;
    ArrayList<LanguageModel> langList;

    private LangAdapter langAdapter;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<CurrencyModel> currencyList;
    private CurrencyAdapter currencyAdapter;
    private LinearLayoutManager currencyLinearLayoutManager;
    ArrayList<CountryModel> countries;
    int selectedLangId,selectedCurrency;
    LocalModel localModel;
    private boolean toggleButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangeLangAndCurrencyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        setTitle(R.string.change_city_currency);


        langList =new ArrayList<>();
        currencyList =new ArrayList<>();
        countries=new ArrayList<>();
        localModel=UtilityApp.getLocalData();

        langList.add(new LanguageModel(1,getString(R.string.text_language_arabic),getString(R.string.ar_lang)));
        langList.add(new LanguageModel(2,getString(R.string.text_langiage_english),getString(R.string.en_lang)));


        if(UtilityApp.getLanguage().equals(Constants.Arabic)){
            selectedLangId=1;
        }
        else {
            selectedLangId=2;
        }

        selectedCurrency=localModel.getCountryId();

        linearLayoutManager=new LinearLayoutManager(getActiviy());
        currencyLinearLayoutManager=new LinearLayoutManager(getActiviy());

        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.recycler.setLayoutManager(currencyLinearLayoutManager);

        binding.chooseLangTv.setOnClickListener(view1 -> {
            binding.langContainer.setVisibility(View.VISIBLE);
            binding.chooseLangTv.setVisibility(View.GONE);

        });



        binding.chooseLangTv.setOnClickListener(view1 -> {

            toggleButton = !toggleButton;

            if (toggleButton) {
                binding.langContainer.setVisibility(View.VISIBLE);
                binding.langLY.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.lang_style));
            } else {
                binding.langContainer.setVisibility(View.GONE);
                binding.langLY.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.spinner_style));
            }

        });





        binding.saveBut.setOnClickListener(view1 -> {
            if(selectedLangId==2){
                UtilityApp.setLanguage(Constants.English);
            }
            else {
                UtilityApp.setLanguage(Constants.Arabic);

            }
            UtilityApp.setAppLanguage();

            Toast(R.string.change_success);
            Intent intent=new Intent(getActiviy(),SplashScreenActivity.class);
            startActivity(intent);
            //getSetting();




        });




//        if(UtilityApp.getCountriesData().size()>0)
//        {
//            countries=UtilityApp.getCountriesData();
//            for (int i = 0; i <countries.size() ; i++) {
//                CountryModel countryModel=countries.get(i);
//                currencyList.add(new CurrencyModel(countryModel.getUserId(),countryModel.getCurrencyCode(),countryModel.getCurrencyCode()));
//            }
//
//        }
//        else {
        countries.add(new CountryModel(4, getString(R.string.Oman), getString(R.string.oman_shotname), 968, "OMR", Constants.three, R.drawable.ic_flag_oman));
        countries.add(new CountryModel(17, getString(R.string.Bahrain), getString(R.string.bahrain_shotname), 973, "BHD",  Constants.three, R.drawable.ic_flag_behrain));
        countries.add(new CountryModel(117, getString(R.string.Kuwait), getString(R.string.Kuwait_shotname), 965, "KWD",  Constants.three, R.drawable.ic_flag_kuwait));
        countries.add(new CountryModel(178, getString(R.string.Qatar), getString(R.string.Qatar_shotname), 974, "QAR",  Constants.two, R.drawable.ic_flag_qatar));
        countries.add(new CountryModel(191, getString(R.string.Saudi_Arabia), getString(R.string.Saudi_Arabia_shortname), 191, "SAR",  Constants.two, R.drawable.ic_flag_saudi_arabia));
        countries.add(new CountryModel(229, getString(R.string.United_Arab_Emirates), getString(R.string.United_Arab_Emirates_shotname), 971, "AED",  Constants.two, R.drawable.ic_flag_uae));

        for (int i = 0; i <countries.size() ; i++) {
                CountryModel countryModel=countries.get(i);
                currencyList.add(new CurrencyModel(countryModel.getUserId(),countryModel.getCurrencyCode(),countryModel.getCurrencyCode()));
            }
       // }


        initAdapter();


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

    }
    public void initAdapter(){

        langAdapter = new LangAdapter(getActiviy(), langList,this,selectedLangId);
        binding.recycler.setAdapter(langAdapter);



    }



    @Override
    public void onLangClicked(int position, LanguageModel languageModel) {
        selectedLangId=languageModel.getUserId();

    }

    @Override
    public void onCurrencyClicked(int position, CurrencyModel currencyModel) {
        selectedCurrency=currencyModel.getUserId();

    }

    public void getSetting() {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<SettingModel> result = (ResultAPIModel<SettingModel>) obj;

            if (func.equals(Constants.ERROR)) {

                // Toasty.error(getActiviy(),R.string.error_in_data, Toast.LENGTH_SHORT, true).show();

            } else if (func.equals(Constants.FAIL)) {

                //Toasty.error(getActiviy(),R.string.fail_to_get_data, Toast.LENGTH_SHORT, true).show();


            } else if (func.equals(Constants.NO_CONNECTION)) {
                //   Toasty.error(getActiviy(),R.string.no_internet_connection, Toast.LENGTH_SHORT, true).show();


            }

            if (IsSuccess) {
                SettingModel settingModel = new SettingModel();
                if (result.data != null) {
                    settingModel.setAbout(result.data.getAbout());
                    settingModel.setConditions(result.data.getConditions());
                    settingModel.setPrivacy(result.data.getPrivacy());
                    UtilityApp.setSetting(settingModel);
                    Intent intent=new Intent(getActiviy(),SplashScreenActivity.class);
                    startActivity(intent);                }

            } else {
                Intent intent=new Intent(getActiviy(),SplashScreenActivity.class);
                startActivity(intent);                }

                // Toasty.error(getActiviy(),R.string.error_in_data, Toast.LENGTH_SHORT, true).show();




        }).getSetting();
    }

}