package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.AddressAdapter;
import com.ramez.shopp.Models.AddressModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityAddressBinding;

import java.util.ArrayList;

public class AddressActivity extends ActivityBase implements AddressAdapter.OnRadioAddressSelect,AddressAdapter.OnDeleteClicked {
    ActivityAddressBinding binding;
    private AddressAdapter addressAdapter;
    ArrayList<AddressModel> addressList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddressBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(R.string.address);

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        addressList=new ArrayList<>();
        addressList.add(new AddressModel(1,2,2,"45.5666","45.5000",
                "","960,21,2923,,,,, ثاني باب على اليمين","Ph : 33310488","Talal",
                "hh","hgg","",1,
                "","","",""));
        addressList.add(new AddressModel(1,2,1,"45.5666","45.5000",
                "","960,21,2923,,,,, ثاني باب على اليمين","Ph : 33310488","علي ",
                "hh","hgg","",1,
                "","","",""));

        addressList.add(new AddressModel(1,2,3,"45.5666","45.5000",
                "","960,21,2923,,,,, ثاني باب على اليمين","Ph : 33310488","Talal",
                "hh","hgg","",1,
                "","","",""));
        addressList.add(new AddressModel(1,2,4,"45.5666","45.5000",
                "","960,21,2923,,,,, ثاني باب على اليمين","Ph : 33310488","Talal",
                "hh","hgg","",1,
                "","","",""));
        addressList.add(new AddressModel(1,2,5,"45.5666","45.5000",
                "","960,21,2923,,,,, ثاني باب على اليمين","Ph : 33310488","Talal",
                "hh","hgg","",1,
                "","","",""));
        addressList.add(new AddressModel(1,2,6,"45.5666","45.5000",
                "","960,21,2923,,,,, ثاني باب على اليمين","Ph : 33310488","Talal",
                "hh","hgg","",1,
                "","","",""));
        linearLayoutManager=new LinearLayoutManager(getActiviy());
        binding.addressRecycler.setLayoutManager(linearLayoutManager);
        initAdapter();

        binding.addNewAddressBut.setOnClickListener(view1 -> {
            addNewAddress();
        });


    }

    @Override
    public void onAddressSelected(AddressModel addressesDM) {

    }

    @Override
    public void onDeleteClicked(AddressModel addressModel, boolean isChecked) {

    }
    public void initAdapter(){

        addressAdapter=new AddressAdapter(getActiviy(),addressList,this,this);
        binding.addressRecycler.setAdapter(addressAdapter);
    }
    private void addNewAddress() {
        Intent intent=new Intent(getActiviy(),AddNewAddressActivity.class);
        startActivity(intent);
    }
}