package com.ramez.shopp;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.ramez.shopp.Activities.ActivityBase;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.MessageEvent;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.ConfirmDialog;
import com.ramez.shopp.Fragments.CartFragment;
import com.ramez.shopp.Fragments.CategoryFragment;
import com.ramez.shopp.Fragments.HomeFragment;
import com.ramez.shopp.Fragments.MyAccountFragment;
import com.ramez.shopp.Fragments.OfferFragment;
import com.ramez.shopp.Models.CartResultModel;
import com.ramez.shopp.Models.GeneralModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Utils.ActivityHandler;
import com.ramez.shopp.Utils.BottomNavigationViewHelper;
import com.ramez.shopp.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import ru.nikartm.support.BadgePosition;

import static android.content.ContentValues.TAG;

public class MainActivity extends ActivityBase {
   int  cartCount=0;
    int storeId;
    LocalModel localModel;
    private ActivityMainBinding binding;

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();
        binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
        binding.toolBar.backBtn.setVisibility(View.GONE);

        binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));


        // getValidation();

        getIntentExtra();

        localModel = UtilityApp.getLocalData();

        storeId = Integer.parseInt(localModel.getCityId());

        if(UtilityApp.isLogin()){
            getCartsCount();
        }

        binding.homeButn.setOnClickListener(view1 -> {
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();
            if(UtilityApp.isLogin()){
                getCartsCount();
            }

        });

        binding.categoryBut.setOnClickListener(view1 -> {
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.category));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_click));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CategoryFragment(), "CategoryFragment").commit();
            if(UtilityApp.isLogin()){
                getCartsCount();
            }
        });

        binding.cartBut.setOnClickListener(view1 -> {

            binding.cartBut.clearBadge();
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.cart));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_bottom));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CartFragment(), "CartFragment").commit();

        });

        binding.offerBut.setOnClickListener(view1 -> {
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.offer_text));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_clicked));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new OfferFragment(), "OfferFragment").commit();
            if(UtilityApp.isLogin()){
                getCartsCount();
            }
        });

        binding.myAccountBut.setOnClickListener(view1 -> {
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.myaccount));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.my_account_clciked));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MyAccountFragment(), "MyAccountFragment").commit();
            if(UtilityApp.isLogin()){
                getCartsCount();
            }

        });


    }


    private void getCartsCount() {
        cartCount=UtilityApp.getCartCount();
        putBadge(cartCount);
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

            binding.toolBar.mainTitleTxt.setText(R.string.checkout);

            binding.toolBar.backBtn.setVisibility(View.VISIBLE);

            binding.toolBar.backBtn.setOnClickListener(view -> {

                binding.toolBar.mainTitleTxt.setText(R.string.cart);

                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CartFragment(), "CartFragment").commit();
                binding.toolBar.backBtn.setVisibility(View.GONE);


            });

        } else if (event.type.equals(MessageEvent.TYPE_main)) {
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.string_menu_home));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_clicked));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_before));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment(), "HomeFragment").commit();


        } else if (event.type.equals(MessageEvent.TYPE_cart)) {
            binding.toolBar.backBtn.setVisibility(View.GONE);
            binding.toolBar.mainTitleTxt.setText(getString(R.string.cart));
            binding.homeButn.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.home_icon));
            binding.categoryBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.category_icon));
            binding.cartBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.cart_icon_bottom));
            binding.offerBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.offer_icon));
            binding.myAccountBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.myaccount_icon));
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CartFragment(), "CartFragment").commitAllowingStateLoss();

        }
        else if (event.type.equals(MessageEvent.TYPE_UPDATE_CART)) {
            getCartsCount();

        }

        else {
            binding.toolBar.backBtn.setVisibility(View.GONE);

        }


    }

    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            boolean TO_CART = getIntent().getBooleanExtra(Constants.CART, false);

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


    public void getValidation() {
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            if (IsSuccess) {

                GeneralModel result = (GeneralModel) obj;

                if (result.getMessage() != null) {
                    if (result.getStatus().equals(Constants.OK_STATUS)) {
                        Log.i(TAG, "Log getValidation" + result.getMessage());

                    } else {

                        ConfirmDialog.Click click = new ConfirmDialog.Click() {
                            @Override
                            public void click() {
                                ActivityHandler.OpenGooglePlay(getActiviy());
                                //finish();


                            }
                        };

                        ConfirmDialog.Click cancel = new ConfirmDialog.Click() {
                            @Override
                            public void click() {
                                finish();


                            }
                        };

                        new ConfirmDialog(getActiviy(), R.string.updateMessage, R.string.ok, R.string.cancel_label, click, cancel);

                    }
                    Log.i(TAG, "Log getValidation" + result.getMessage());

                }
            }

        }).getValidate(Constants.deviceType, UtilityApp.getAppVersionStr(), BuildConfig.VERSION_CODE);
    }


    public void putBadge(int cartCount) {

        Typeface typeface = Typeface.createFromAsset(getActiviy().getAssets(), Constants.NORMAL_FONT);
        binding.cartBut.setBadgeValue(cartCount)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16)
                .setBadgeBackground(ContextCompat.getDrawable(getActiviy(),R.drawable.badge))
                .setMaxBadgeValue(999)
                .setBadgeTextFont(typeface).setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL).setShowCounter(true).setBadgePadding(4);

    }


    @Override
    protected void onResume() {
        super.onResume();

        if(UtilityApp.isLogin()){
            getCartsCount();

        }

    }
}