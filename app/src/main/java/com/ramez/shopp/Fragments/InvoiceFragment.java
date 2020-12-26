package com.ramez.shopp.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajithvgiri.searchdialog.SearchListItem;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.ramez.shopp.Activities.AddressActivity;
import com.ramez.shopp.Adapter.InvoiceItemAdapter;
import com.ramez.shopp.Adapter.PaymentAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.CallBack.DataCallback;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.DeliveryResultModel;
import com.ramez.shopp.Models.DeliveryTime;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.LoginResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.OrderCall;
import com.ramez.shopp.Models.OrdersResultModel;
import com.ramez.shopp.Models.PaymentModel;
import com.ramez.shopp.Models.PaymentResultModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.FragmentInvoiceBinding;
import com.ramez.shopp.searchdialog.SearchableDialog;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class InvoiceFragment extends FragmentBase implements InvoiceItemAdapter.OnInvoiceItemClicked {
    private static final int ADDRESS_CODE = 100;
    public Integer userId;
    public String paymentMethod="COD";
    public String couponCodeId = "0";
    public Integer deliveryDateId = 0;
    public Boolean expressDelivery = false;
    ArrayList<PaymentModel> paymentList;
    ArrayList<DeliveryTime> deliveryTimesList;
    ArrayList<CartModel> productList;
    LinearLayoutManager payLinearLayoutManager;
    LinearLayoutManager linearLayoutManager;
    LocalModel localModel;
    int storeId, productsSize;
    String total, currency;
    List<SearchListItem> deliverTimeList;
    List<SearchListItem> deliverDayList;
    com.ramez.shopp.searchdialog.SearchableDialog deliverTimeDialog, deliverDayDialog;
    int fraction = 2;
    private FragmentInvoiceBinding binding;
    private PaymentAdapter paymentAdapter;
    private InvoiceItemAdapter invoiceProductAdapter;
    private int addressId = 0;
    private String addressTitle;
    private double deliveryFees = 0.0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        paymentList = new ArrayList<>();
        productList = new ArrayList<>();
        deliveryTimesList = new ArrayList<>();

        deliverDayList = new ArrayList<>();
        deliverTimeList = new ArrayList<>();

        localModel = UtilityApp.getLocalData();
        currency = localModel.getCurrencyCode();
        fraction = localModel.getFractional();


        storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
        currency = localModel.getCurrencyCode();

        userId = UtilityApp.getUserData().getId();

        payLinearLayoutManager = new LinearLayoutManager(getActivityy(), RecyclerView.HORIZONTAL, false);
        binding.paymentRv.setLayoutManager(payLinearLayoutManager);

        linearLayoutManager = new LinearLayoutManager(getActivityy());
        binding.productsRecycler.setLayoutManager(linearLayoutManager);
        binding.productsRecycler.setHasFixedSize(true);

        getExtraIntent();

        getDeliveryTimeList(storeId);

        getPaymentMethod(storeId);


        binding.TvDeliveryTime.setOnClickListener(view1 -> {
            deliverTimeDialog.show();
        });


        binding.DeliveryDateTv.setOnClickListener(view1 -> {
            deliverDayDialog.show();
        });

        binding.DeliverLY.setOnClickListener(v -> {
            Intent intent = new Intent(getActivityy(), AddressActivity.class);
            intent.putExtra(Constants.delivery_choose, true);
            startActivityForResult(intent, ADDRESS_CODE);

        });

        binding.sendOrder.setOnClickListener(view1 -> {

            OrderCall orderCall = new OrderCall();
            orderCall.user_id = userId;
            orderCall.store_ID = storeId;
            orderCall.address_id = addressId;
            orderCall.payment_method = paymentMethod;
            orderCall.coupon_code_id = couponCodeId;
            orderCall.delivery_date_id = deliveryDateId;
            orderCall.expressDelivery = expressDelivery;

            Log.i(TAG, "Log makeOrder");
            Log.i(TAG, "Log user_id " + userId);
            Log.i(TAG, "Log store_ID " + storeId);
            Log.i(TAG, "Log addressId " + addressId);
            Log.i(TAG, "Log payment_method " + paymentMethod);
            Log.i(TAG, "Log coupon_code_id " + couponCodeId);
            Log.i(TAG, "Log delivery_date_id " + deliveryDateId);
            Log.i(TAG, "Log expressDelivery " + expressDelivery);


            if (addressId == 0) {
                if(paymentMethod.equals("CC")){
                    binding.DeliverLY.setVisibility(View.GONE);
                }
                else {
                    Toast(R.string.choose_address);
                    binding.deliveryAddressTv.setError(getString(R.string.choose_address));
                    binding.deliveryAddressTv.requestFocus();
                }



            } else {
                sendOrder(orderCall);
            }

        });


        binding.deliveryFees.setText(NumberHandler.formatDouble(deliveryFees, localModel.getFractional()).concat("" + currency));


        return view;
    }

    public void initAdapter() {

        paymentAdapter = new PaymentAdapter(getActivityy(), paymentList, (obj, func, IsSuccess) -> {
            PaymentModel paymentModel = (PaymentModel) obj;
            if (paymentMethod != null) {
                paymentMethod = paymentModel.getShortname();

                if(paymentMethod.equals("CC")){
                    binding.DeliverLY.setVisibility(View.GONE);
                }
                else {
                    binding.DeliverLY.setVisibility(View.VISIBLE);

                }

            }
        });
        binding.paymentRv.setAdapter(paymentAdapter);


    }



    @Override
    public void onInvoiceItemClicked(CartModel cartDM) {

    }


    public void getPaymentMethod(int user_id) {

        paymentList.clear();
        binding.loadingLYPay.setVisibility(View.VISIBLE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            binding.loadingLYPay.setVisibility(View.GONE);
            PaymentResultModel result = (PaymentResultModel) obj;

            if (IsSuccess) {
                if (result.getData() != null && result.getData().size() > 0) {
                    paymentList = result.getData();
                    paymentMethod = result.getData().get(0).getShortname();
                    initAdapter();
                    Log.i(TAG, "Log getPaymentMethod" + result.getData().size());


                }


            }


        }).getPaymentMethod(user_id);
    }


    public void getDeliveryTimeList(int user_id) {

        deliveryTimesList.clear();
        binding.loadingDelivery.setVisibility(View.VISIBLE);
        binding.deliveryLy.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            binding.loadingDelivery.setVisibility(View.GONE);
            DeliveryResultModel result = (DeliveryResultModel) obj;

            if (IsSuccess) {
                if (result.getData() != null && result.getData().size() > 0) {
                    deliveryTimesList = result.getData();
                    binding.deliveryLy.setVisibility(View.VISIBLE);
                    binding.DeliveryDateTv.setText(deliveryTimesList.get(0).getDate());
                    binding.TvDeliveryTime.setText(deliveryTimesList.get(0).getTime());
                    deliveryDateId = deliveryTimesList.get(0).getId();

                    Log.i(TAG, "Log deliveryTimesList" + result.getData().size());


                    for (int i = 0; i < deliveryTimesList.size(); i++) {
                        SearchListItem searchListItem = new SearchListItem(i, deliveryTimesList.get(i).getDate());
                        SearchListItem searchListItem1 = new SearchListItem(i, deliveryTimesList.get(i).getTime());
                        deliverDayList.add(searchListItem);
                        deliverTimeList.add(searchListItem1);
                    }


                    deliverTimeDialog = new com.ramez.shopp.searchdialog.SearchableDialog(getActivity(), deliverTimeList, getString(R.string.choose_time));

                    deliverDayDialog = new SearchableDialog(getActivity(), deliverDayList, getString(R.string.choose_day));

                    deliverTimeDialog.setOnItemSelected((i, deliverTimeList) -> {
                        binding.TvDeliveryTime.setText(deliverTimeList.getTitle());
                        deliveryDateId = deliveryTimesList.get(i).getId();


                    });


                    deliverDayDialog.setOnItemSelected((i, deliverDayList) -> {
                        binding.DeliveryDateTv.setText(deliverDayList.getTitle());
                        deliveryDateId = deliveryTimesList.get(i).getId();


                    });


                }


            }


        }).getDeliveryTimeList(user_id);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == ADDRESS_CODE) {
                Bundle bundle = data.getExtras();
                addressId = bundle.getInt(Constants.ADDRESS_ID);
                addressTitle = bundle.getString(Constants.ADDRESS_TITLE);
                binding.deliveryAddressTv.setText(addressTitle);
            }


        }

    }

    private void getExtraIntent() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            total = bundle.getString(Constants.CART_SUM);
            productsSize = bundle.getInt(Constants.CART_PRODUCT_COUNT, 0);
            productList = (ArrayList<CartModel>) bundle.getSerializable(Constants.CART_LIST);
            binding.productsSizeTv.setText(String.valueOf(productsSize));
            binding.totalTv.setText(total.concat(" " + currency));
            initProductAdapter();
        }


    }

    private void initProductAdapter() {

        invoiceProductAdapter = new InvoiceItemAdapter(getActivityy(), productList, this, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {

                total = NumberHandler.formatDouble(invoiceProductAdapter.calculateSubTotalPrice(), fraction);
                binding.totalTv.setText(total.concat(" " + currency));

            }
        });
        binding.productsRecycler.setAdapter(invoiceProductAdapter);


    }


    private void sendOrder(OrderCall orderCall) {

        GlobalData.progressDialog(getActivityy(), R.string.make_order, R.string.please_wait_sending);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            OrdersResultModel result = (OrdersResultModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_to_send_order);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActivityy(), R.string.fail_to_send_order, message);
            } else {
                if (IsSuccess) {


                    AwesomeSuccessDialog successDialog = new AwesomeSuccessDialog(getActivityy());
                    successDialog.setTitle(R.string.make_order).setMessage(R.string.sending_order).setColoredCircle(R.color.dialogSuccessBackgroundColor).setDialogIconAndColor(R.drawable.ic_check, R.color.white).show().setOnDismissListener(dialogInterface -> {
                        startMain();
                    });
                    successDialog.show();


                } else {
                    Toast(getString(R.string.fail_to_send_order));

                }
            }


        }).makeOrder(orderCall);

    }

    public void startMain() {
        Intent intent = new Intent(getActivityy(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}






