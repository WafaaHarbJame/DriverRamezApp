package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.Adapter.AddressAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CityModelResult;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.OtpModel;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Fragments.InvoiceFragment;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.AddressModel;
import com.ramez.shopp.Models.AddressResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityAddressBinding;

import java.util.ArrayList;

public class AddressActivity extends ActivityBase implements AddressAdapter.OnRadioAddressSelect,AddressAdapter.OnContainerSelect,AddressAdapter.OnDeleteClicked {
    ActivityAddressBinding binding;
    private AddressAdapter addressAdapter;
    ArrayList<AddressModel> addressList;
    private LinearLayoutManager linearLayoutManager;
    private int defaultAddressId;
    private MemberModel user;
    boolean deliveryChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = UtilityApp.getUserData();

        binding.toolBar.mainTitleTxt.setText(R.string.address);

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        addressList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActiviy());
        binding.addressRecycler.setLayoutManager(linearLayoutManager);


        if (UtilityApp.isLogin()) {
            GetUserAddress(user.getId());
        }

        getIntentExtra();


        binding.addNewAddressBut.setOnClickListener(view1 -> {
            addNewAddress();
        });


        binding.acceptBtu.setOnClickListener(view1 -> {
            user.setLastSelectedAddress(defaultAddressId);
            UtilityApp.setUserData(user);
            addressAdapter.notifyDataSetChanged();
            Toast(R.string.address_default);


        });




    }



    @Override
    public void onAddressSelected(AddressModel addressesDM) {
        defaultAddressId=addressesDM.getId();

        if(deliveryChoose) {
            Intent intent = new Intent(AddressActivity.this, InvoiceFragment.class);
            intent.putExtra(Constants.ADDRESS_ID,addressesDM.getId());
            intent.putExtra(Constants.ADDRESS_TITLE,addressesDM.getFullAddress());
            setResult(Activity.RESULT_OK, intent);
            finish();

        }


    }

    @Override
    public void onDeleteClicked(AddressModel addressModel, boolean isChecked) {

    }
    public void initAdapter(){

        addressAdapter=new AddressAdapter(getActiviy(),addressList,this,this,this);
        binding.addressRecycler.setAdapter(addressAdapter);
    }
    private void addNewAddress() {
        Intent intent=new Intent(getActiviy(),AddNewAddressActivity.class);
        startActivity(intent);
    }

    public void GetUserAddress(int user_id) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            AddressResultModel result = (AddressResultModel) obj;

            if (func.equals(Constants.ERROR)) {
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(R.string.error_in_data);
                binding.dataLY.setVisibility(View.GONE);

            } else if (func.equals(Constants.FAIL)) {
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(R.string.error_in_data);
                binding.dataLY.setVisibility(View.GONE);
            } else {
                if (IsSuccess) {
                    binding.dataLY.setVisibility(View.VISIBLE);
                    if (result.getData() != null && result.getData().size() > 0) {
                        addressList= result.getData();
                        initAdapter();

                    }
                    else {
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                        binding.failGetDataLY.failTxt.setText(R.string.no_address);
                        binding.dataLY.setVisibility(View.GONE);

                    }




                } else {
                    Toast(R.string.fail_to_get_data);

                }
            }

        }).GetAddressHandle(user_id);
    }

    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            deliveryChoose=bundle.getBoolean(Constants.delivery_choose,false);


        }
    }

    @Override
    public void onContainerSelectSelected(AddressModel addressesDM) {
        if(deliveryChoose) {
            Intent intent = new Intent(AddressActivity.this, InvoiceFragment.class);
            intent.putExtra(Constants.ADDRESS_ID,addressesDM.getId());
            intent.putExtra(Constants.ADDRESS_TITLE,addressesDM.getFullAddress());
            setResult(Activity.RESULT_OK, intent);
            finish();

        }

    }
}