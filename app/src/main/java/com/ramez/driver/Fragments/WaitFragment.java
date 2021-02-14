package com.ramez.driver.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.driver.Adapter.Adapter_Ramez_Order;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.RamesOrders;
import com.ramez.driver.Models.RamezOrderCall;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentHomeBinding;
import com.ramez.driver.databinding.FragmentWaitBinding;

import java.util.ArrayList;


public class WaitFragment extends FragmentBase {
    private FragmentWaitBinding binding;
    ArrayList<RamesOrders> list;
    String user_id = " ";
    private int category_id = 0, country_id, city_id;
    MemberModel memberModel;
    String type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWaitBinding.inflate(inflater, container, false);
        list = new ArrayList<>();

        if (UtilityApp.isLogin()) {
            memberModel = UtilityApp.getUserData();
            user_id = String.valueOf(memberModel.getId());

        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityy());
        binding.recycler.setLayoutManager(linearLayoutManager);

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setItemAnimator(null);

        if (memberModel.getRole_id() == 6) {
            type = Constants.Packer;
            if (memberModel.getRole_id() == 0) {

                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

            } else {
                getList(type, memberModel.getId());

            }

        } else if (memberModel.getRole_id() == 4) {
            type = Constants.Driver;

                getList(type, memberModel.getId());



        }


        return binding.getRoot();
    }



    public void initAdapter() {

        Adapter_Ramez_Order adapter = new Adapter_Ramez_Order(getActivity(), list, 0);
        binding.recycler.setAdapter(adapter);
    }


    public void getList(String type, int ramezId) {
        RamezOrderCall ramezOrderCall=new RamezOrderCall();
        ramezOrderCall.type=type;
        ramezOrderCall.mode=Constants.one;
        ramezOrderCall.user_id=ramezId;
        ramezOrderCall.store_id= Integer.parseInt(UtilityApp.getLocalData().getCityId());

        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (isVisible()){

                ResultAPIModel<ArrayList<RamesOrders>> result= (ResultAPIModel<ArrayList<RamesOrders>>) obj;
                String message = getString(R.string.fail_to_get_data);

                binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

                if (func.equals(Constants.ERROR)) {

                    if (result != null) {
                        message = result.message;
                    }
                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);

                } else if (func.equals(Constants.FAIL)) {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);

                } else if (func.equals(Constants.NO_CONNECTION)) {
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(R.string.no_internet_connection);
                    binding.failGetDataLY.noInternetIv.setVisibility(View.VISIBLE);
                    binding.dataLY.setVisibility(View.GONE);

                } else {

                    if (IsSuccess) {
                        if (result != null && result.data.size() > 0) {
                            binding.dataLY.setVisibility(View.VISIBLE);
                            binding.noDataLY.noDataLY.setVisibility(View.GONE);
                            binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                            list = result.data;
                            initAdapter();

                        } else {

                            binding.dataLY.setVisibility(View.GONE);
                            binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);
                            binding.noDataLY.noDataTxt.setText(R.string.textEmptyOrders);

                        }


                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                        binding.failGetDataLY.failTxt.setText(message);


                    }
                }
            }


        }).getRamezOrder(ramezOrderCall);
    }
}