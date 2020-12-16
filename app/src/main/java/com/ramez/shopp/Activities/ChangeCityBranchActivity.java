package com.ramez.shopp.Activities;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.Adapter.CityAdapter;
import com.ramez.shopp.Adapter.CountriesAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CityModelResult;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.CityModel;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityChangeCityBranchBinding;

import java.util.ArrayList;

public class ChangeCityBranchActivity extends ActivityBase implements CityAdapter.OnCityClick, CountriesAdapter.OnCountryClick {
    ActivityChangeCityBranchBinding binding;
    ArrayList<CityModel> cityModelArrayList;
    ArrayList<CountryModel> countries;

    int city_id = 0;
    int countryId;
    LocalModel localModel;
    private LinearLayoutManager linearLayoutManager;
    private CityAdapter cityAdapter;
    private CountriesAdapter countriesAdapter;

    private boolean toggleButton = false;
    private boolean toggleLangButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeCityBranchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.change_city_branch));

        cityModelArrayList = new ArrayList<>();
        countries = new ArrayList<>();
        localModel = UtilityApp.getLocalData();
        city_id = Integer.parseInt(localModel.getCityId());

        Log.i("tag", "Log cityId" + localModel.getCityId());


        binding.chooseBranchTv.setOnClickListener(view1 -> {

            toggleButton = !toggleButton;

            if (toggleButton) {
                binding.branchContainer.setVisibility(View.VISIBLE);
                binding.branchLY.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.lang_style));
            } else {
                binding.branchContainer.setVisibility(View.GONE);
                binding.branchLY.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.spinner_style));
            }

        });

        binding.chooseCountryTv.setOnClickListener(view1 -> {

            toggleLangButton= !toggleLangButton;

            if (toggleLangButton) {
                binding.countryContainer.setVisibility(View.VISIBLE);
                binding.countryLY.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.lang_style));
            } else {
                binding.countryContainer.setVisibility(View.GONE);
                binding.countryLY.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.spinner_style));
            }

        });





        linearLayoutManager = new LinearLayoutManager(getActiviy());
        binding.branchRecycler.setLayoutManager(linearLayoutManager);

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

         countryId = localModel.getCountryId();

        getCityList(countryId);

        binding.saveBut.setOnClickListener(view1 -> {
            localModel.setCityId(String.valueOf(city_id));
            UtilityApp.setLocalData(localModel);
            Toast(R.string.change_success);
            Intent intent = new Intent(getActiviy(), SplashScreenActivity.class);
            startActivity(intent);

        });

        if (UtilityApp.getCountriesData().size() > 0) {
            countries = UtilityApp.getCountriesData();
            initAdapter();
        } else {
            countries.add(new CountryModel(4, getString(R.string.Oman), getString(R.string.oman_shotname), 968, "OMR", 3, R.drawable.ic_flag_oman));
            countries.add(new CountryModel(17, getString(R.string.Bahrain), getString(R.string.bahrain_shotname), 973, "BHD", 3, R.drawable.ic_flag_behrain));
            countries.add(new CountryModel(117, getString(R.string.Kuwait), getString(R.string.Kuwait_shotname), 965, "KWD", 2, R.drawable.ic_flag_kuwait));
            countries.add(new CountryModel(178, getString(R.string.Qatar), getString(R.string.Qatar_shotname), 974, "QAR", 2, R.drawable.ic_flag_qatar));
            countries.add(new CountryModel(191, getString(R.string.Saudi_Arabia), getString(R.string.Saudi_Arabia_shortname), 191, "SAR", 2, R.drawable.ic_flag_saudi_arabia));
            countries.add(new CountryModel(229, getString(R.string.United_Arab_Emirates), getString(R.string.United_Arab_Emirates_shotname), 971, "AED", 2, R.drawable.ic_flag_uae));
            initAdapter();
        }

        binding.saveBut.setOnClickListener(view1 -> {
           localModel.setCountryId(countryId);
           localModel.setCityId(String.valueOf(city_id));
           UtilityApp.setLocalData(localModel);
            Toast(R.string.change_success);
            Intent intent=new Intent(getActiviy(),SplashScreenActivity.class);
            startActivity(intent);


        });


    }

    public void initAdapter() {

        countriesAdapter = new CountriesAdapter(getActiviy(), this, countries,countryId);
        binding.countryRecycler.setAdapter(countriesAdapter);
    }


    public void initCityAdapter() {

        cityAdapter = new CityAdapter(getActiviy(), cityModelArrayList, this, city_id);
        binding.branchRecycler.setAdapter(cityAdapter);


    }
    @Override
    public void onCityClicked(int position, CityModel cityModel) {
        city_id = cityModel.getId();

    }

    private void getCityList(int country_id) {

        cityModelArrayList.clear();

        Log.i("TAG", "Log country_id" + country_id);
        new DataFeacher(getActiviy(), (obj, func, IsSuccess) -> {

            CityModelResult result = (CityModelResult) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_to_get_data);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }


            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {
                        Log.i("TAG", "Log CityList size " + result.getData().size());
                        cityModelArrayList = result.getData();
                        initCityAdapter();
                        cityAdapter.notifyDataSetChanged();


                    }
                }
            }


        }).CityHandle(country_id);

    }

    @Override
    public void onCountryClicked(int position, CountryModel countryModel) {
        countryId=countryModel.getId();
        localModel.setShortname(countryModel.getShortname());
        UtilityApp.setLocalData(localModel);
        Log.i("tag","Log click countryId"+countryId);
        Log.i("tag","Log click ShortName"+countryModel.getShortname());
        getCityList(countryId);

    }
}