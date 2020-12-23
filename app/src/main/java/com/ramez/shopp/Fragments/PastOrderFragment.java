package com.ramez.shopp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ramez.shopp.Adapter.MyOrdersAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.OrdersHeaderModel;
import com.ramez.shopp.Models.OrdersModel;
import com.ramez.shopp.Models.OrdersResultModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentCurrentOrderBinding;
import com.ramez.shopp.databinding.FragmentPastOrderBinding;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class PastOrderFragment extends FragmentBase {
    ArrayList<OrdersModel> completedOrdersList;
    LinearLayoutManager linearLayoutManager;
    boolean showLoading = true;
    private FragmentPastOrderBinding binding;
    private MyOrdersAdapter myOrdersAdapter;
    private MemberModel user;
    private int user_id;
    private List ordersDMS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPastOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        completedOrdersList = new ArrayList<>();
        ordersDMS = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.myOrderRecycler.setLayoutManager(linearLayoutManager);

        user = UtilityApp.getUserData();
        user_id = UtilityApp.getUserData().getId();

        getPastOrder(user_id);

        binding.swipe.setOnRefreshListener(() -> {
            getPastOrder(user_id);
            binding.swipe.setRefreshing(false);
        });


        return view;
    }

    public void getPastOrder(int user_id) {

        completedOrdersList.clear();

        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            OrdersResultModel result = (OrdersResultModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result.getMessage() != null) {
                    message = result.getMessage();
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


            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        removeDuplicate(result.getData());

                        Log.i(TAG, "Log ordersDMS" + ordersDMS.size());


                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

                    }


                } else {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);


                }
            }

        }).getPastOrders(user_id);
    }



    private void initOrdersAdapters() {

        myOrdersAdapter = new MyOrdersAdapter(getActivity(), binding.myOrderRecycler, completedOrdersList, user_id);
        binding.myOrderRecycler.setAdapter(myOrdersAdapter);


    }

    private void removeDuplicate(ArrayList<OrdersModel> data) {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                if (data.get(i).getOrderCode().equals(data.get(j).getOrderCode())) {
                    data.remove(j);
                    j--;
                }
            }
        }

        if (data.size() > 0) {
            completedOrdersList = data;

        }
        initOrdersAdapters();
    }

}