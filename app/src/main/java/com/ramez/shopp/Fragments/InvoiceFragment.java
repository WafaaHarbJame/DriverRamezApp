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


        productList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        productList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        productList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        productList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        productList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));


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




