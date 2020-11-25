package com.ramez.ramez.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.Adapter.CountriesAdapter;
import com.ramez.ramez.Models.CountryModel;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityChooseNearstCityBinding;

import java.util.ArrayList;

public class ChooseNearCity extends ActivityBase implements CountriesAdapter.OnItemClick {
   ActivityChooseNearstCityBinding binding;
    private CountriesAdapter countriesAdapter;
    ArrayList<CountryModel> countries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseNearstCityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        countries=new ArrayList<>();
        binding.chooseCityTv.setOnClickListener(view1 -> {
            binding.cityContainer.setVisibility(View.VISIBLE);
            binding.chooseCityTv.setVisibility(View.GONE);

        });

        countries.add(new CountryModel(1,972,getString(R.string.text_registration_country),
                "","","","",getString(R.string.text_registration_country),""));

        countries.add(new CountryModel(1,972,getString(R.string.text_registration_country),
                "","","","",getString(R.string.text_registration_country),""));
        countries.add(new CountryModel(1,972,getString(R.string.text_registration_country),
                "","","","",getString(R.string.text_registration_country),""));
        countries.add(new CountryModel(1,972,getString(R.string.text_registration_country),
                "","","","",getString(R.string.text_registration_country),""));
        countries.add(new CountryModel(1,972,getString(R.string.text_registration_country),
                "","","",getString(R.string.text_registration_country),getString(R.string.text_registration_country),""));

        initAdapter();


        binding.toolBar.backBtn.setVisibility(View.GONE);

    }

    private void GoToChooseCity() {
        Intent intent = new Intent(getActiviy(), ChooseCityActivity.class);
        startActivity(intent);
        finish();

    }
    public void initAdapter(){
        countriesAdapter = new CountriesAdapter(getActiviy(), this, countries);
        binding.recycler.setAdapter(countriesAdapter);
    }


    @Override
    public void onItemClicked(int position, CountryModel countryModel) {

    }
}