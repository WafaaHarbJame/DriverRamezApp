package com.ramez.shopp.Activities;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.Adapter.MyOrdersAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Fragments.CurrentOrderFragment;
import com.ramez.shopp.Fragments.PastOrderFragment;
import com.ramez.shopp.Models.FavouriteResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.OrdersHeaderModel;
import com.ramez.shopp.Models.OrdersModel;
import com.ramez.shopp.Models.OrdersResultModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityMyOrderBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static android.content.ContentValues.TAG;

public class MyOrderActivity extends ActivityBase {
    ActivityMyOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.toolBar.mainTitleTxt.setText(getString(R.string.my_order));



        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });


        binding.viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.tabs.setTabTextColors(ContextCompat.getColor(getActiviy(), R.color.black),
                ContextCompat.getColor(getActiviy(), R.color.white));


    }





    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    Fragment newFragment = new CurrentOrderFragment();
                    return newFragment;
                case 1:
                    Fragment oldFragment = new PastOrderFragment();
                    return oldFragment;

                default:
                    return CurrentOrderFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return getString(R.string.current_orders);
            else if (position == 1) return getString(R.string.complete_request);
            else return "";
        }
    }





}