package com.ramez.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ramez.driver.Adapter.CountriesAdapter;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.CountryModel;
import com.ramez.driver.Models.CountryModelResult;
import com.ramez.driver.Models.LocalModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.SharedPManger;
import com.ramez.driver.databinding.ActivityChooseCityBinding;

import java.util.ArrayList;

public class ChooseCityActivity extends ActivityBase implements CountriesAdapter.OnCountryClick {
    ArrayList<CountryModel> countries;
    MemberModel memberModel;
    SharedPManger sharedPManger;
    LinearLayoutManager linearLayoutManager;
    private ActivityChooseCityBinding binding;
    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseCityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        countries = new ArrayList<>();
        sharedPManger = new SharedPManger(this);

        linearLayoutManager = new LinearLayoutManager(getActiviy());
        binding.recycler.setLayoutManager(linearLayoutManager);

        binding.chooseCityTv.setOnClickListener(view1 -> {
            binding.cityContainer.setVisibility(View.VISIBLE);
            binding.chooseCityTv.setVisibility(View.GONE);

        });

        countries.add(new CountryModel(4, getString(R.string.Oman), getString(R.string.oman_shotname), 968, "OMR", Constants.three, R.drawable.ic_flag_oman));
        countries.add(new CountryModel(17, getString(R.string.Bahrain), getString(R.string.bahrain_shotname), 973, "BHD",  Constants.three, R.drawable.ic_flag_behrain));
        countries.add(new CountryModel(117, getString(R.string.Kuwait), getString(R.string.Kuwait_shotname), 965, "KWD",  Constants.three, R.drawable.ic_flag_kuwait));
        countries.add(new CountryModel(178, getString(R.string.Qatar), getString(R.string.Qatar_shotname), 974, "QAR",  Constants.two, R.drawable.ic_flag_qatar));
        countries.add(new CountryModel(191, getString(R.string.Saudi_Arabia), getString(R.string.Saudi_Arabia_shortname), 191, "SAR",  Constants.two, R.drawable.ic_flag_saudi_arabia));
        countries.add(new CountryModel(229, getString(R.string.United_Arab_Emirates), getString(R.string.United_Arab_Emirates_shotname), 971, "AED",  Constants.two, R.drawable.ic_flag_uae));

        UtilityApp.setCountriesData(countries);

        initAdapter();

        binding.toolBar.backBtn.setVisibility(View.GONE);


    }

    private void GoToChooseNearCity(CountryModel countryModel) {
        LocalModel localModel = new LocalModel();
        localModel.setCountryId(countryModel.getUserId());
        localModel.setShortname(countryModel.getShortname());
        localModel.setPhonecode(countryModel.getPhonecode());
        localModel.setCountryName(countryModel.getUserName());
        localModel.setCurrencyCode(countryModel.getCurrencyCode());
        localModel.setFractional(countryModel.getFractional());
        UtilityApp.setLocalData(localModel);
        Intent intent = new Intent(getActiviy(), ChooseNearCity.class);
        intent.putExtra(Constants.COUNTRY_ID, countryModel.getUserId());
        startActivity(intent);


    }

    public void initAdapter() {

        countriesAdapter = new CountriesAdapter(getActiviy(), this, countries, 0);
        binding.recycler.setAdapter(countriesAdapter);
    }


    @Override
    public void onCountryClicked(int position, CountryModel countryModel) {
        sharedPManger.SetData(Constants.CURRENCY, countryModel.getCurrencyCode());
        sharedPManger.SetData(Constants.Fractional, countryModel.getFractional());
        GoToChooseNearCity(countryModel);
    }

    private void getCountryList() {

        GlobalData.progressDialog(getActiviy(), R.string.upload_date, R.string.please_wait_upload);
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            CountryModelResult result = (CountryModelResult) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_to_get_data);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActiviy(), R.string.fail_to_get_data, message);
            }


            else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActiviy(), R.string.no_internet_connection);
            }


            else {
                if (IsSuccess) {
                    countries = result.getData();
                    initAdapter();

                } else {
                    Toast(getString(R.string.fail_to_get_data));

                }
            }


        }).CountryHandle();

    }


    private void getExtraIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            memberModel = (MemberModel) getIntent().getExtras().getSerializable(Constants.KEY_MEMBER);


        }


    }

}