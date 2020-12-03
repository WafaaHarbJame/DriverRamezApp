package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.ramez.shopp.Activities.AddCardActivity;
import com.ramez.shopp.Activities.TermsActivity;
import com.ramez.shopp.Adapter.CartAdapter;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends FragmentBase  implements CartAdapter.OnCartItemClicked{
    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    ArrayList<CartModel> cartList;
    LinearLayoutManager linearLayoutManager;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        cartList =new ArrayList<>();

        linearLayoutManager=new LinearLayoutManager(getActivityy());
        binding.cartRecycler.setLayoutManager(linearLayoutManager);
        binding.cartRecycler.setHasFixedSize(true);
        cartList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        cartList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        cartList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        cartList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        cartList.add(new CartModel(1,2,1,2,0,10,"شيبس ليز الملح 25 جرام","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg",
                "10","",0,"20",100));
        initAdapter();

        binding.contBut.setOnClickListener(view1 -> {
            //startAddCardActivity();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainContainer, new InvoiceFragment(), "InvoiceFragment").commit();

        });


        return view;
    }

    private void initAdapter() {
        cartAdapter=new CartAdapter(getActivityy(), cartList,this);
        binding.cartRecycler.setAdapter(cartAdapter);

    }

    @Override
    public void onCartItemClicked(CartModel cartDM) {

    }

    private void startAddCardActivity(){
        Intent intent=new Intent(getActivityy(), AddCardActivity.class);
        startActivity(intent);
    }
}