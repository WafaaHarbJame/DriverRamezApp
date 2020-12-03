package com.ramez.shopp.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Adapter.PaymentAdapter;
import com.ramez.shopp.Models.PaymentModel;
import com.ramez.shopp.databinding.FragmentInvoiceBinding;

import java.util.ArrayList;

public class InvoiceFragment extends FragmentBase implements PaymentAdapter.OnPaymentClick {
    private FragmentInvoiceBinding binding;

    private PaymentAdapter paymentAdapter;
    ArrayList<PaymentModel> paymentList;
    LinearLayoutManager payLinearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        paymentList=new ArrayList<>();
        paymentList.add(new PaymentModel(1,"بطاقة ائتمان","بطاقة ائتمان"));
        paymentList.add(new PaymentModel(1,"بطاقة ائتمان","بطاقة ائتمان"));
        paymentList.add(new PaymentModel(1,"بطاقة ائتمان","بطاقة ائتمان"));

        payLinearLayoutManager=new LinearLayoutManager(getActivityy(), RecyclerView.HORIZONTAL,false);
        binding.paymentRv.setLayoutManager(payLinearLayoutManager);




        initAdapter();

        return view;
    }

    public void initAdapter(){

        paymentAdapter = new PaymentAdapter(getActivityy(), paymentList,this);
        binding.paymentRv.setAdapter(paymentAdapter);
    }

    @Override
    public void onPaymentClicked(int position, PaymentModel paymentModel) {

    }
}




