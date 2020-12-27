package com.ramez.shopp;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.ramez.shopp.Activities.ActivityBase;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.MessageEvent;
import com.ramez.shopp.Fragments.CartFragment;
import com.ramez.shopp.Fragments.CategoryFragment;
import com.ramez.shopp.Fragments.HomeFragment;
import com.ramez.shopp.Fragments.MyAccountFragment;
import com.ramez.shopp.Fragments.OfferFragment;
import com.ramez.shopp.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends ActivityBase {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commitAllowingStateLoss();
        binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
        binding.toolBar.backBtn.setVisibility(View.GONE);

        binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));

        getIntentExtra();

        binding.homeButn.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commitAllowingStateLoss();

        });

        binding.categoryBut.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.category));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_click));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CategoryFragment(), "CategoryFragment").commitAllowingStateLoss();

        });

        binding.cartBut.setOnClickListener(view1 -> {

            binding.toolBar.mainTitleTxt.setText(getString(R.string.cart));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_bottom));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CartFragment(), "CartFragment").commitAllowingStateLoss();

        });

        binding.offerBut.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.offer_text));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_clicked));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new OfferFragment(), "OfferFragment").commitAllowingStateLoss();

        });

        binding.myAccountBut.setOnClickListener(view1 -> {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.myaccount));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.my_account_clciked));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MyAccountFragment(), "MyAccountFragment").commitAllowingStateLoss();

        });




    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(@NotNull MessageEvent event) {
        if (event.type.equals(MessageEvent.TYPE_invoice)) {
            binding.toolBar.mainTitleTxt.setText(R.string.invoice_details);
            binding.toolBar.backBtn.setVisibility(View.VISIBLE);
            binding.toolBar.backBtn.setOnClickListener(view -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CartFragment(), "MyAccountFragment").commit();
                binding.toolBar.mainTitleTxt.setText(R.string.cart);

            });

        }

        if (event.type.equals(MessageEvent.TYPE_main)) {
            binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();


        }

        else {
            binding.toolBar.backBtn.setVisibility(View.GONE);

        }

    }

    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            boolean TO_CART=getIntent().getBooleanExtra(Constants.CART,false);

            if (TO_CART) {
                binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
                binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));
                binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
                binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
                binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
                binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();


            }


        }
    }



}