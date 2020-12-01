package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.CountriesAdapter;
import com.ramez.shopp.Adapter.CurrencyAdapter;
import com.ramez.shopp.Adapter.LangAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangeLangAndCurrencyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.change_city_branch));

        langList =new ArrayList<>();
        currencyList =new ArrayList<>();

        binding.chooseLangTv.setOnClickListener(view1 -> {
            binding.langContainer.setVisibility(View.VISIBLE);
            binding.chooseLangTv.setVisibility(View.GONE);

        });


        binding.chooseCurrencyTv.setOnClickListener(view1 -> {
            binding.containerCurrency.setVisibility(View.VISIBLE);
            binding.chooseCurrencyTv.setVisibility(View.GONE);

        });


        langList.add(new LanguageModel(1,getString(R.string.text_language_arabic),getString(R.string.english_lang)));
        langList.add(new LanguageModel(2,getString(R.string.text_langiage_english),getString(R.string.english_lang)));

        currencyList.add(new CurrencyModel(1,getString(R.string.dollar_ar),getString(R.string.dollar)));
        currencyList.add(new CurrencyModel(2,getString(R.string.euro_ar),getString(R.string.euro)));
        currencyList.add(new CurrencyModel(2,getString(R.string.pound_ar),getString(R.string.pound)));



        linearLayoutManager=new LinearLayoutManager(getActiviy());
        currencyLinearLayoutManager=new LinearLayoutManager(getActiviy());

        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.branchRecycler.setLayoutManager(currencyLinearLayoutManager);

        initAdapter();


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

    }
    public void initAdapter(){

        langAdapter = new LangAdapter(getActiviy(), langList,this);
        currencyAdapter = new CurrencyAdapter(getActiviy(), currencyList,this);

        binding.recycler.setAdapter(langAdapter);
        binding.branchRecycler.setAdapter(currencyAdapter);



    }



    @Override
    public void onLangClicked(int position, LanguageModel languageModel) {

    }

    @Override
    public void onCurrencyClicked(int position, CurrencyModel currencyModel) {

    }
}