package com.ramez.shopp.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.ramez.shopp.Activities.AddCardActivity;
import com.ramez.shopp.Activities.ProductDetailsActivity;
import com.ramez.shopp.Adapter.CartAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.CallBack.DataCallback;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.MessageEvent;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.CheckLoginDialog;
import com.ramez.shopp.Dialogs.EmptyCartDialog;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.CartResultModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.Utils.SharedPManger;
import com.ramez.shopp.databinding.FragmentCartBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CartFragment extends FragmentBase implements CartAdapter.OnCartItemClicked {
    ArrayList<CartModel> cartList;
    LinearLayoutManager linearLayoutManager;
    String currency = "BHD";
    int fraction = 2;
    int storeId, userId;
    MemberModel user;
    LocalModel localModel;
    boolean isLogin = false;
    int productsSize;
    String total;
    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private EmptyCartDialog emptyCartDialog;
    private CheckLoginDialog checkLoginDialog;
    private int minimum_order_amount;
    private int delivery_charges=0;
    private CartResultModel cartResultModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        cartList = new ArrayList<>();
        isLogin = UtilityApp.isLogin();

        localModel = UtilityApp.getLocalData();
        currency = localModel.getCurrencyCode();
        fraction = localModel.getFractional();


        user = UtilityApp.getUserData();
        if (!isLogin) {
            binding.dataLY.setVisibility(View.GONE);
            binding.contBut.setVisibility(View.GONE);
            showLoginDialog();
        } else {
            storeId = Integer.parseInt(localModel.getCityId());
            userId = user.getId();

            linearLayoutManager = new LinearLayoutManager(getActivityy());
            binding.cartRecycler.setLayoutManager(linearLayoutManager);
            binding.cartRecycler.setHasFixedSize(true);

            binding.contBut.setOnClickListener(view1 -> {

                if(cartAdapter.calculateSubTotalPrice()<minimum_order_amount){
                    Snackbar snackbar = Snackbar.make(view1, getString(R.string.minimum_order_amount) + minimum_order_amount, Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
                else {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_invoice));
                    FragmentManager fragmentManager = getParentFragmentManager();
                    InvoiceFragment invoiceFragment = new InvoiceFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.CART_PRODUCT_COUNT, productsSize);
                    bundle.putString(Constants.CART_SUM, total);
                    bundle.putSerializable(Constants.CART_MODEL, cartResultModel);
                    invoiceFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.mainContainer, invoiceFragment, "InvoiceFragment").commit();

                }



            });


            getCarts(storeId, userId);

            if (cartAdapter != null) {

                cartAdapter.notifyDataSetChanged();
            }


        }

        return view;

    }

    private void initAdapter() {
        cartAdapter = new CartAdapter(getActivityy(), cartList, this, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {

                double numProducts= (double) obj;

                if(numProducts == 0.0){
                   getCarts(storeId,userId);

                }
                else {

                    total = NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction);
                    binding.totalTv.setText(total.concat(" " + currency));
                    binding.productCostTv.setText(NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction).concat(" " + currency));
                }


            }
        });
        binding.cartRecycler.setAdapter(cartAdapter);
        productsSize = cartList.size();
        binding.productsSizeTv.setText(String.valueOf(cartAdapter.getItemCount()));
        total = NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction);
        binding.totalTv.setText(total.concat(" " + currency));
        binding.productCostTv.setText(NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction).concat(" " + currency));
        cartAdapter.notifyDataSetChanged();


    }

    @Override
    public void onCartItemClicked(CartModel cartDM) {
        Intent intent = new Intent(getActivityy(), ProductDetailsActivity.class);
        ProductModel productModel = new ProductModel();
        productModel.setId(cartDM.getProductId());
        productModel.setName(cartDM.getName());
        productModel.setHName(cartDM.getHProductName());
        intent.putExtra(Constants.DB_productModel, productModel);
        startActivity(intent);
    }

    private void startAddCardActivity() {
        Intent intent = new Intent(getActivityy(), AddCardActivity.class);
        startActivity(intent);
    }

    public void getCarts(int storeId, int userId) {

        cartList.clear();

        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
        binding.contBut.setVisibility(View.GONE);


        new DataFeacher(false, (obj, func, IsSuccess) -> {
            cartResultModel = (CartResultModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (cartResultModel != null) {
                    message = cartResultModel.getMessage();
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
                    if (cartResultModel.getData().getCartData() != null && cartResultModel.getData().getCartData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        cartList = cartResultModel.getData().getCartData();
                        binding.contBut.setVisibility(View.VISIBLE);
                        minimum_order_amount=cartResultModel.getMinimumOrderAmount();
                        localModel.setMinimum_order_amount(minimum_order_amount);
                        UtilityApp.setLocalData(localModel);

                        delivery_charges=cartResultModel.getDeliveryCharges();
                        Log.i(TAG, "Log cart" + cartResultModel.getData().getCartData().size());
                        initAdapter();
                        cartAdapter.notifyDataSetChanged();


                    } else {
                        binding.contBut.setVisibility(View.GONE);
                        binding.dataLY.setVisibility(View.GONE);
                        showEmptyCartDialog();
                    }


                } else {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);


                }
            }

        }).GetCarts(storeId, userId);
    }

    private void showEmptyCartDialog() {
        EmptyCartDialog.Click okClick = new EmptyCartDialog.Click() {
            @Override
            public void click() {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_main));

            }
        };


        EmptyCartDialog.Click cancelClick = new EmptyCartDialog.Click() {
            @Override
            public void click() {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_main));

            }
        };

        emptyCartDialog = new EmptyCartDialog(getActivityy(), R.string.please_login, R.string.text_login_login, R.string.register, okClick, cancelClick);
        emptyCartDialog.show();
    }

    private void showLoginDialog() {
        CheckLoginDialog checkLoginDialog = new CheckLoginDialog(getActivityy(), R.string.please_login, R.string.account_data, R.string.ok, R.string.cancel,null,null);
        checkLoginDialog.show();
    }


}