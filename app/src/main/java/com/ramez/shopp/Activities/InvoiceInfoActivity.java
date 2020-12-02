package com.ramez.shopp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.CountriesAdapter;
import com.ramez.shopp.Adapter.OrderProductsAdapter;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.Models.OrderProductsModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityChooseCityBinding;
import com.ramez.shopp.databinding.ActivityContactSupportBinding;
import com.ramez.shopp.databinding.ActivityInvoiceInfoBinding;

import java.util.ArrayList;

public class InvoiceInfoActivity extends ActivityBase {
    ActivityInvoiceInfoBinding binding;

    private OrderProductsAdapter orderProductsAdapter;
    ArrayList<OrderProductsModel> list;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityInvoiceInfoBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.invoice_details));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        list=new ArrayList<>();

        list.add(new OrderProductsModel(1,972,"15",
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        list.add(new OrderProductsModel(1,972,"20",
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        list.add(new OrderProductsModel(1,972,"20",
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        list.add(new OrderProductsModel(1,972,"20",
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        list.add(new OrderProductsModel(1,972,getString(R.string.text_registration_country),
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        list.add(new OrderProductsModel(1,972,"10",
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        list.add(new OrderProductsModel(1,972,"10",
                "شيبس ليز الملح 25 جرام","شيبس ليز الملح 25 جرام","12.45","149.40","https://images-na.ssl-images-amazon.com/images/I/91TKOKJe2zL._SL1500_.jpg",0,12));

        initAdapter();

    }

    public void initAdapter(){
        linearLayoutManager=new LinearLayoutManager(getActiviy());
        binding.productsRecycler.setLayoutManager(linearLayoutManager);

        orderProductsAdapter = new OrderProductsAdapter(getActiviy(), list);
        binding.productsRecycler.setAdapter(orderProductsAdapter);
    }
}