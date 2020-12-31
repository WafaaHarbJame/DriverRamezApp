package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ramez.shopp.Activities.MyOrderActivity;
import com.ramez.shopp.Adapter.MyOrdersAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.MessageEvent;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.OrderModel;
import com.ramez.shopp.Models.OrderProductModel;
import com.ramez.shopp.Models.OrdersResultModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentCurrentOrderBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class CurrentOrderFragment extends FragmentBase {

    List<OrderProductModel> currentOrdersList;
    private FragmentCurrentOrderBinding binding;
    private MyOrdersAdapter myOrdersAdapter;
    private int user_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCurrentOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        currentOrdersList = new ArrayList<>();

        user_id = UtilityApp.getUserData().getId();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.myOrderRecycler.setLayoutManager(linearLayoutManager);


        getUpcomingOrders(user_id);

        binding.swipe.setOnRefreshListener(() -> {
            getUpcomingOrders(user_id);
            binding.swipe.setRefreshing(false);
        });

        binding.noDataLY.btnBrowseProducts.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        });


        return view;
    }

    public void getUpcomingOrders(int user_id) {

        currentOrdersList.clear();

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

                        currentOrdersList = result.getData();

                        List<OrderModel> list= initOrderList();

                        initOrdersAdapters(list);

                        Log.i("TAG", "Log ordersDMS" + currentOrdersList.size());


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

        }).getUpcomingOrders(user_id);
    }

    private List<OrderModel> initOrderList() {
        List<OrderModel> orderList=new ArrayList<>();

        for (int i = 0; i < currentOrdersList.size(); i++) {
            OrderProductModel currentProduct = currentOrdersList.get(i);

            OrderModel orderModel=new OrderModel();
            orderModel.setCartId( currentProduct.getCartId());
            orderModel.setOrderCode( currentProduct.getOrderCode());
            orderModel.setAddressName( currentProduct.getAddressName());
            orderModel.setFullAddress( currentProduct.getFullAddress());
            orderModel.setDeliveryDate( currentProduct.getDeliveryDate());
            orderModel.setDeliveryStatus( currentProduct.getDeliveryStatus());
            orderModel.setOrderStatus( currentProduct.getOrderStatus());
            orderModel.setFromDate( currentProduct.getFromDate());
            orderModel.setDeliveryTime( currentProduct.getDeliveryTime());
            orderModel.setToDate( currentProduct.getToDate());
            orderModel.setOrderTotal(currentProduct.getOrderTotal());
            orderModel.setTotalWithoutTax(currentProduct.getTotalWithoutTax());
            orderModel.setTotalWithTax(currentProduct.getTotalWithTax());
            List<OrderProductModel> productsList = new ArrayList<>();
            for (int j = 0; j < currentOrdersList.size(); j++) {

                OrderProductModel orderProductModel = currentOrdersList.get(j);
                if (currentProduct.getOrderCode().equals(orderProductModel.getOrderCode())) {
                    productsList.add(orderProductModel);
                    currentOrdersList.remove(j);
                    j--;
                }
            }
            orderModel.setOrderProductsDMS(productsList);
            orderList.add(orderModel);
        }

        Log.i("TAG", "Log currentOrdersList" + orderList.size());

        return orderList;

    }


    private void initOrdersAdapters(List<OrderModel> list) {

        myOrdersAdapter = new MyOrdersAdapter(getActivity(), binding.myOrderRecycler, list, user_id);
        binding.myOrderRecycler.setAdapter(myOrdersAdapter);


    }
}