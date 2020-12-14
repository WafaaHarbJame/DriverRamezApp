package com.ramez.shopp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Adapter.InvoiceItemAdapter;
import com.ramez.shopp.Adapter.PaymentAdapter;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Models.PaymentModel;
import com.ramez.shopp.databinding.FragmentInvoiceBinding;

import java.util.ArrayList;

public class InvoiceFragment extends FragmentBase implements PaymentAdapter.OnPaymentClick,InvoiceItemAdapter.OnInvoiceItemClicked {
    private FragmentInvoiceBinding binding;

    private PaymentAdapter paymentAdapter;
    ArrayList<PaymentModel> paymentList;
    LinearLayoutManager payLinearLayoutManager;

    private InvoiceItemAdapter invoiceProductAdapter;
    ArrayList<CartModel> productList;
    LinearLayoutManager linearLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        paymentList=new ArrayList<>();
        productList =new ArrayList<>();

        paymentList.add(new PaymentModel(1,"بطاقة ائتمان","بطاقة ائتمان"));
        paymentList.add(new PaymentModel(1,"بطاقة ائتمان","بطاقة ائتمان"));
        paymentList.add(new PaymentModel(1,"بطاقة ائتمان","بطاقة ائتمان"));





        payLinearLayoutManager=new LinearLayoutManager(getActivityy(), RecyclerView.HORIZONTAL,false);
        binding.paymentRv.setLayoutManager(payLinearLayoutManager);

        linearLayoutManager=new LinearLayoutManager(getActivityy());
        binding.productsRecycler.setLayoutManager(linearLayoutManager);
        binding.productsRecycler.setHasFixedSize(true);


        initAdapter();

        return view;
    }

    public void initAdapter(){

        paymentAdapter = new PaymentAdapter(getActivityy(), paymentList,this);
        binding.paymentRv.setAdapter(paymentAdapter);

        invoiceProductAdapter=new InvoiceItemAdapter(getActivityy(), productList,this);
        binding.productsRecycler.setAdapter(invoiceProductAdapter);
    }

    @Override
    public void onPaymentClicked(int position, PaymentModel paymentModel) {

    }

    @Override
    public void onInvoiceItemClicked(CartModel cartDM) {

    }
}




