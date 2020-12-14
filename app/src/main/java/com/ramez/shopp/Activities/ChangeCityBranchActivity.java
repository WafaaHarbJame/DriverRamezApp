package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.CountriesAdapter;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityChangeCityBranchBinding;

import java.util.ArrayList;

public class ChangeCityBranchActivity extends ActivityBase implements  CountriesAdapter.OnItemClick {
    ActivityChangeCityBranchBinding binding;
    ArrayList<CountryModel> countriesList;
    private CountriesAdapter countriesAdapter;
    private LinearLayoutManager linearLayoutManager;

    ArrayList<CountryModel> branchesList;
    private CountriesAdapter branchAdapter;
    private LinearLayoutManager branchLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangeCityBranchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.change_city_branch));

        countriesList =new ArrayList<>();
        branchesList =new ArrayList<>();

        binding.chooseCityTv.setOnClickListener(view1 -> {
            binding.cityContainer.setVisibility(View.VISIBLE);
            binding.chooseCityTv.setVisibility(View.GONE);

        });


        binding.chooseBranchTv.setOnClickListener(view1 -> {
            binding.branchContainer.setVisibility(View.VISIBLE);
            binding.chooseBranchTv.setVisibility(View.GONE);

        });
//        countriesList.add(new CountryModel(1,972,getString(R.string.text_registration_country),
//                "","","","",getString(R.string.text_registration_country),""));
//
//        countriesList.add(new CountryModel(1,972,getString(R.string.text_registration_country),
//                "","","","",getString(R.string.text_registration_country),""));
//        countriesList.add(new CountryModel(1,972,getString(R.string.text_registration_country),
//                "","","","",getString(R.string.text_registration_country),""));
//        countriesList.add(new CountryModel(1,972,getString(R.string.text_registration_country),
//                "","","","",getString(R.string.text_registration_country),""));
//        countriesList.add(new CountryModel(1,972,getString(R.string.text_registration_country),
//                "","","",getString(R.string.text_registration_country),getString(R.string.text_registration_country),""));

        branchesList=countriesList;

        linearLayoutManager=new LinearLayoutManager(getActiviy());
        branchLinearLayoutManager=new LinearLayoutManager(getActiviy());

        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.branchRecycler.setLayoutManager(branchLinearLayoutManager);

        initAdapter();


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

    }
    public void initAdapter(){

        countriesAdapter = new CountriesAdapter(getActiviy(), this, countriesList);
        branchAdapter = new CountriesAdapter(getActiviy(), this, branchesList);

        binding.recycler.setAdapter(countriesAdapter);
        binding.branchRecycler.setAdapter(branchAdapter);



    }

    @Override
    public void onItemClicked(int position, CountryModel countryModel) {

    }
}