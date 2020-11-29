package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.Adapter.MyOrdersAdapter;
import com.ramez.ramez.Models.OrdersHeaderModel;
import com.ramez.ramez.Models.OrdersModel;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityChangePassBinding;
import com.ramez.ramez.databinding.ActivityMyOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends ActivityBase {
    ActivityMyOrderBinding binding;

    List<OrdersModel> currentOrdersList;
    List<OrdersModel> completedOrdersList;
    private List ordersDMS;
    private MyOrdersAdapter myOrdersAdapter;
    LinearLayoutManager linearLayoutManager;

    boolean isCurrentLoading = false, isFinishedLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ordersDMS = new ArrayList<>();
        currentOrdersList=new ArrayList<>();
        completedOrdersList=new ArrayList<>();

        binding.toolBar.mainTitleTxt.setText(getString(R.string.my_order));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        linearLayoutManager =new LinearLayoutManager(getActiviy());

        binding.myOrderRecycler.setLayoutManager(linearLayoutManager);

        initOrdersList();



    }


    private void initOrdersAdapters() {

        myOrdersAdapter = new MyOrdersAdapter(getActiviy(), binding.myOrderRecycler, ordersDMS, 2);
        binding.myOrderRecycler.setAdapter(myOrdersAdapter);


    }
    private void initOrdersList() {
        ordersDMS.clear();
        currentOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "wait","45.555","49,00","market"));

        currentOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "wait","45.555","49,00","market"));

        currentOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "waiting","45.555","49,00","market"));

        currentOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "waiting","45.555","49,00","market"));


        completedOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "complete","45.555","49,00","market"));

        completedOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "complete","45.555","49,00","market"));

        completedOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "complete","45.555","49,00","market"));

        completedOrdersList.add(new OrdersModel(1,getString(R.string.krof),"","2020-11-29 22:27:14","122",
                "complete","45.555","49,00","market"));

        ordersDMS.add(new OrdersHeaderModel(getString(R.string.current_orders), currentOrdersList.size()));
        ordersDMS.addAll(currentOrdersList);

        ordersDMS.add(new OrdersHeaderModel(getString(R.string.complete_request), completedOrdersList.size()));
        ordersDMS.addAll(completedOrdersList);

        initOrdersAdapters();

        myOrdersAdapter.notifyDataSetChanged();

    }
}