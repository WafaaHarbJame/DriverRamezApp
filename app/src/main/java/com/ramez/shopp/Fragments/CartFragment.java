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

import com.ramez.shopp.Activities.AddCardActivity;
import com.ramez.shopp.Adapter.CartAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
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
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
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
            binding.cartContainer.setVisibility(View.GONE);
            binding.contBut.setVisibility(View.GONE);
            showLoginDialog();
        } else {
            storeId = Integer.parseInt(localModel.getCityId());
            userId = user.getId();

            linearLayoutManager = new LinearLayoutManager(getActivityy());
            binding.cartRecycler.setLayoutManager(linearLayoutManager);
            binding.cartRecycler.setHasFixedSize(true);

            binding.contBut.setOnClickListener(view1 -> {

                EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_invoice));
                FragmentManager fragmentManager = getParentFragmentManager();

                InvoiceFragment invoiceFragment = new InvoiceFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.CART_PRODUCT_COUNT, productsSize);
                bundle.putString(Constants.CART_SUM, total);
                bundle.putSerializable(Constants.CART_LIST, cartList);
                invoiceFragment.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.mainContainer, invoiceFragment, "InvoiceFragment").commit();

            });


            getCarts(storeId, userId);
        }

        return view;

    }

    private void initAdapter() {
        cartAdapter = new CartAdapter(getActivityy(), cartList, this);
        binding.cartRecycler.setAdapter(cartAdapter);

    }

    @Override
    public void onCartItemClicked(CartModel cartDM) {

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

        new DataFeacher(false, (obj, func, IsSuccess) -> {
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

                        if (cartList.size() > 0) {
                            productsSize = cartList.size();
                            binding.productsSizeTv.setText(String.valueOf(productsSize));
                            total = NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction);
                            binding.totalTv.setText(total.concat(" " + currency));
                            binding.productCostTv.setText(NumberHandler.formatDouble(cartAdapter.calculateSubTotalPrice(), fraction).concat(" " + currency));
                        }


                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.contBut.setVisibility(View.GONE);
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

                Intent intent = new Intent(getActivityy(), MainActivity.class);
                intent.putExtra(Constants.CART, true);
                startActivity(intent);
            }
        };
        emptyCartDialog = new EmptyCartDialog(getActivityy(), R.string.please_login, R.string.text_login_login, R.string.register, okClick, null);
        emptyCartDialog.show();
    }

    private void showLoginDialog() {
        checkLoginDialog = new CheckLoginDialog(getActivityy(), R.string.please_login, R.string.text_login_login, R.string.register, null, null);
        checkLoginDialog.show();
    }


}