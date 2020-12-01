package com.ramez.shopp;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ramez.shopp.Activities.ActivityBase;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Fragments.CartFragment;
import com.ramez.shopp.Fragments.CategoryFragment;
import com.ramez.shopp.Fragments.HomeFragment;
import com.ramez.shopp.Fragments.MyAccountFragment;
import com.ramez.shopp.Fragments.OfferFragment;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.databinding.ActivityMainBinding;

public class MainActivity extends ActivityBase {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));


        binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
        binding.homeButn.setOnClickListener(view1 -> {
          //  binding.toolBar.background.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();

        });

        binding.categoryBut.setOnClickListener(view1 -> {
         //   binding.toolBar.background.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.category));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_click));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CategoryFragment(), "CategoryFragment").commit();

        });

        binding.cartBut.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.cart));
            //binding.toolBar.background.setVisibility(View.VISIBLE);
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_bottom));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CartFragment(), "CartFragment").commit();

        });

        binding.offerBut.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.offer_text));
          //  binding.toolBar.background.setVisibility(View.GONE);
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_clicked));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new OfferFragment(), "OfferFragment").commit();

        });

        binding.myAccountBut.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.myaccount));
           // binding.toolBar.background.setVisibility(View.GONE);
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.my_account_clciked));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MyAccountFragment(), "MyAccountFragment").commit();

        });
        MemberModel memberModel=new MemberModel();
        memberModel.setLastSelectedAddress(1);

        UtilityApp.setUserData(memberModel);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment fragmentCurrent = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (fragmentCurrent instanceof HomeFragment) {
           // binding.toolBar.background.setVisibility(View.GONE);
        }


    }

}