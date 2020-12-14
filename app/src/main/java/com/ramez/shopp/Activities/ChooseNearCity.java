package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.Adapter.CityAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CityModelResult;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.CityModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.SharedPManger;
import com.ramez.shopp.databinding.ActivityChooseNearstCityBinding;

import java.util.ArrayList;

public class ChooseNearCity extends ActivityBase implements CityAdapter.OnCityClick {
    ActivityChooseNearstCityBinding binding;
    ArrayList<CityModel> countries;
    SharedPManger sharedPManger;
    int city_id = 0;
    private CityAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseNearstCityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        countries = new ArrayList<>();
        sharedPManger = new SharedPManger(this);
        binding.chooseCityTv.setOnClickListener(view1 -> {
            binding.cityContainer.setVisibility(View.VISIBLE);
            binding.chooseCityTv.setVisibility(View.GONE);

        });
        getExtraIntent();
        binding.toolBar.backBtn.setVisibility(View.GONE);

    }

    private void getExtraIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int country_id = getIntent().getIntExtra(Constants.COUNTRY_ID, 0);
            Log.i("TAG", "Log Shortname" + UtilityApp.getLocalData().getShortname());
            Log.i("TAG", "Log country_id" + UtilityApp.getLocalData().getCountryId());
            Log.i("TAG", "Log country_id Intent" + country_id);

            getCityList(country_id);

        }


    }

    public void initAdapter() {
        countriesAdapter = new CityAdapter(getActiviy(), countries, this);
        binding.recycler.setAdapter(countriesAdapter);
    }


    private void getCityList(int country_id) {
        countries.clear();
        Log.i("TAG", "Log country_id" + country_id);

        GlobalData.progressDialog(getActiviy(), R.string.upload_date, R.string.please_wait_upload);
        new DataFeacher(getActiviy(), (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            CityModelResult result = (CityModelResult) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_to_get_data);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActiviy(), R.string.fail_to_get_data, message);
            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {
                        Log.i("TAG", "Log country size " + result.getData().size());
                        countries = result.getData();
                        city_id = countries.get(0).getId();
                        initAdapter();

                    }


                } else {
                    Toast(getString(R.string.fail_to_get_data));

                }
            }


        }).CityHandle(country_id);

    }

    @Override
    public void onCityClicked(int position, CityModel cityModel) {
        city_id = cityModel.getId();
        Toast("+city_id " + city_id);
        LocalModel localModel = UtilityApp.getLocalData();
        localModel.setCityId(String.valueOf(city_id));
        UtilityApp.setLocalData(localModel);
        Toast("+city_id model  " + localModel.getCityId());
        Intent intent = new Intent(getActiviy(), RegisterLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}