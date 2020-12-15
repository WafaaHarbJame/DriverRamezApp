package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.CountriesAdapter;
import com.ramez.shopp.Adapter.CurrencyAdapter;
import com.ramez.shopp.Adapter.LangAdapter;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.Models.CurrencyModel;
import com.ramez.shopp.Models.LanguageModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityChangeLangAndCurrencyBinding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangeLangAndCurrencyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.change_city_currency));

        langList =new ArrayList<>();
        currencyList =new ArrayList<>();
        countries=new ArrayList<>();

        langList.add(new LanguageModel(1,getString(R.string.text_language_arabic),getString(R.string.ar_lang)));
        langList.add(new LanguageModel(2,getString(R.string.text_langiage_english),getString(R.string.en_lang)));


        if(UtilityApp.getLanguage().equals(Constants.Arabic)){
            selectedLangId=1;
        }
        else {
            selectedLangId=2;
        }

        selectedCurrency=UtilityApp.getLocalData().getCountryId();

        linearLayoutManager=new LinearLayoutManager(getActiviy());
        currencyLinearLayoutManager=new LinearLayoutManager(getActiviy());

        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.branchRecycler.setLayoutManager(currencyLinearLayoutManager);

        binding.chooseLangTv.setOnClickListener(view1 -> {
            binding.langContainer.setVisibility(View.VISIBLE);
            binding.chooseLangTv.setVisibility(View.GONE);

        });


        binding.chooseCurrencyTv.setOnClickListener(view1 -> {
            binding.containerCurrency.setVisibility(View.VISIBLE);
            binding.chooseCurrencyTv.setVisibility(View.GONE);

        });

        binding.saveBut.setOnClickListener(view1 -> {


        });




        if(UtilityApp.getCountriesData().size()>0)
        {
            countries=UtilityApp.getCountriesData();
            for (int i = 0; i <countries.size() ; i++) {
                CountryModel countryModel=countries.get(i);
                currencyList.add(new CurrencyModel(countryModel.getId(),countryModel.getCurrencyCode(),countryModel.getCurrencyCode()));
            }

        }
        else {
            countries.add(new CountryModel(4,getString(R.string.Oman),getString(R.string.oman_shotname),968,"OMR",3,R.drawable.ic_flag_oman));
            countries.add(new CountryModel(17,getString(R.string.Bahrain),getString(R.string.bahrain_shotname),973,"BHD",3,R.drawable.ic_flag_behrain));
            countries.add(new CountryModel(117,getString(R.string.Kuwait),getString(R.string.Kuwait_shotname),965,"KWD",2,R.drawable.ic_flag_kuwait));
            countries.add(new CountryModel(178,getString(R.string.Qatar),getString(R.string.Qatar_shotname),974,"QAR",2,R.drawable.ic_flag_qatar));
            countries.add(new CountryModel(191,getString(R.string.Saudi_Arabia),getString(R.string.Saudi_Arabia_shortname),191,"SAR",2,R.drawable.ic_flag_saudi_arabia));
            countries.add(new CountryModel(229,getString(R.string.United_Arab_Emirates),getString(R.string.United_Arab_Emirates_shotname),971,"AED",
                    2,R.drawable.ic_flag_uae));
            for (int i = 0; i <countries.size() ; i++) {
                CountryModel countryModel=countries.get(i);
                currencyList.add(new CurrencyModel(countryModel.getId(),countryModel.getCurrencyCode(),countryModel.getCurrencyCode()));
            }
        }


        initAdapter();


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

    }
    public void initAdapter(){

        langAdapter = new LangAdapter(getActiviy(), langList,this,selectedLangId);
        currencyAdapter = new CurrencyAdapter(getActiviy(), currencyList,this,selectedCurrency);

        binding.recycler.setAdapter(langAdapter);
        binding.branchRecycler.setAdapter(currencyAdapter);



    }



    @Override
    public void onLangClicked(int position, LanguageModel languageModel) {
        if(languageModel.getId()==2){
            UtilityApp.setLanguage(Constants.English);
            UtilityApp.setAppLanguage();
        }
        else {
            UtilityApp.setLanguage(Constants.Arabic);
            UtilityApp.setAppLanguage();

        }

        Intent intent=new Intent(getActiviy(),SplashScreenActivity.class);
        startActivity(intent);

    }

    @Override
    public void onCurrencyClicked(int position, CurrencyModel currencyModel) {

    }
}