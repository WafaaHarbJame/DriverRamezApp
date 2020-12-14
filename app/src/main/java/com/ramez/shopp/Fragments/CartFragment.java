package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
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
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.MessageEvent;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.CartResultModel;
import com.ramez.shopp.Models.CategoryResultModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.FragmentCartBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CartFragment extends FragmentBase  implements CartAdapter.OnCartItemClicked{
    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    ArrayList<CartModel> cartList;
    LinearLayoutManager linearLayoutManager;
    String currency="BHD";
    int fraction=2;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        cartList =new ArrayList<>();

        currency=UtilityApp.getLocalData().getCurrencyCode();
        fraction=UtilityApp.getLocalData().getFractional();


        linearLayoutManager=new LinearLayoutManager(getActivityy());
        binding.cartRecycler.setLayoutManager(linearLayoutManager);
        binding.cartRecycler.setHasFixedSize(true);

        binding.contBut.setOnClickListener(view1 -> {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_invoice));
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainContainer, new InvoiceFragment(), "InvoiceFragment").commit();

        });


        getCarts(7263,14);





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

    public void getCarts(int storeId,int userId) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(getActivity(), (obj, func, IsSuccess) -> {
            CartResultModel result = (CartResultModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result != null) {
                    message = result.getMessage();
                }
                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.GONE);
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(message);

            } else if (func.equals(Constants.FAIL)) {

                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.GONE);
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(message);


            } else {
                if (IsSuccess) {
                    if (result.getData().getCartData() != null && result.getData().getCartData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        cartList = result.getData().getCartData();

                        Log.i(TAG, "Log cart" + result.getData().getCartData().size());
                        initAdapter();

                        if(cartList.size()>0){
                            binding.productsSizeTv.setText(String.valueOf(cartList.size()));
                            binding.totalTv.setText(NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction).concat(" "+currency));
                            binding.productCostTv.setText(NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction).concat(" "+currency));
                        }


                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

                    }


                } else {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);


                }
            }

        }).GetCarts(storeId,userId);
    }
}