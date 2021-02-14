package com.ramez.driver.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends FragmentBase {

    String user_id = "0";
    MemberModel memberModel;
    private FragmentHomeBinding binding;
    private int country_id, city_id;
    private boolean login = false, register = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        if (UtilityApp.isLogin()) {

            if (UtilityApp.getUserData() != null) {
                memberModel = UtilityApp.getUserData();
                user_id = String.valueOf(memberModel.getId());

            }


        }

        binding.failGetDataLY.refreshBtn.setOnClickListener(view1 -> {


        });


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager(binding.viewPager);
        binding.tabs.setupWithViewPager(binding.viewPager);

        if(memberModel!=null){
            if (memberModel.getRole_id() == 6) {
               binding.tabs.getTabAt(1).setText(getString(R.string.picking));


            } else {
            binding.tabs.getTabAt(1).setText(getString(R.string.order_on_way));

            }

        }



    }





    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    @Override
    public void onPause() {
        super.onPause();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

//        Bundle validShipmentsBundel = new Bundle();
//        validShipmentsBundel.putString(Constants.KEY_TYPE, Constants.CURRENT);
        Fragment onWayFragment = new OnWayFragment();
//        validShipmentsFragment.setArguments(validShipmentsBundel);
        onWayFragment.setRetainInstance(true);

        Fragment deliveryFragment = new WaitFragment();
//        validShipmentsFragment.setArguments(validShipmentsBundel);
        deliveryFragment.setRetainInstance(true);

//        Bundle finishedShipmentsBundel = new Bundle();
//        finishedShipmentsBundel.putString(Constants.KEY_TYPE, Constants.FINISHED);
        Fragment finishedFragment = new FinishFragment();
//        finishedShipmentFragment.setArguments(finishedShipmentsBundel);
        finishedFragment.setRetainInstance(true);


        adapter.addFragment(onWayFragment, getString(R.string.Delivery_orders));
        adapter.addFragment(deliveryFragment, getString(R.string.picking));
        adapter.addFragment(finishedFragment, getString(R.string.finshed_order));

        viewPager.setAdapter(adapter);

    }

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}