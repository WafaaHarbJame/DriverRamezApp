package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajithvgiri.searchdialog.SearchListItem;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.google.android.material.snackbar.Snackbar;
import com.ramez.shopp.Activities.AddressActivity;
import com.ramez.shopp.Adapter.InvoiceItemAdapter;
import com.ramez.shopp.Adapter.PaymentAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.CallBack.DataCallback;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.MessageEvent;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.CartProcessModel;
import com.ramez.shopp.Models.CartResultModel;
import com.ramez.shopp.Models.DeliveryResultModel;
import com.ramez.shopp.Models.DeliveryTime;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.OrderCall;
import com.ramez.shopp.Models.OrdersResultModel;
import com.ramez.shopp.Models.PaymentModel;
import com.ramez.shopp.Models.PaymentResultModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.FragmentInvoiceBinding;
import com.ramez.shopp.searchdialog.SearchableDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class InvoiceFragment extends FragmentBase implements InvoiceItemAdapter.OnInvoiceItemClicked {
    private static final int ADDRESS_CODE = 100;
    public Integer userId;
    public String paymentMethod = "COD";
    public String couponCodeId = "0";
    public Integer deliveryDateId = 0;
    public Boolean expressDelivery = false;
    ArrayList<PaymentModel> paymentList;
    List<DeliveryTime> deliveryTimesList;
    //    ArrayList<DeliveryTime> noRepeat;
    ArrayList<CartModel> productList;
    LinearLayoutManager payLinearLayoutManager;
    LinearLayoutManager linearLayoutManager;
    LocalModel localModel;
    int storeId, productsSize;
    String total, currency;
    List<SearchListItem> searchDayList;
    List<SearchListItem> searchTimeList;
    SearchableDialog deliverTimeDialog, deliverDayDialog;
    int fraction = 2;
    private FragmentInvoiceBinding binding;
    private PaymentAdapter paymentAdapter;
    private InvoiceItemAdapter invoiceProductAdapter;
    private int addressId = 0;
    private String addressTitle;
    private double deliveryFees = 0.0;
    private CartResultModel cartResultModel;
    private int minimum_order_amount = 0;

    private LinkedHashMap<String, List<DeliveryTime>> datesMap = new LinkedHashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        paymentList = new ArrayList<>();
        productList = new ArrayList<>();
//        deliveryTimesList = new ArrayList<>();
//        noRepeat = new ArrayList<>();

        searchDayList = new ArrayList<>();
        searchTimeList = new ArrayList<>();

        localModel = UtilityApp.getLocalData();
        currency = localModel.getCurrencyCode();
        fraction = localModel.getFractional();
        minimum_order_amount = localModel.getMinimum_order_amount();


        storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
        currency = localModel.getCurrencyCode();

        userId = UtilityApp.getUserData().getId();

        payLinearLayoutManager = new LinearLayoutManager(getActivityy(), RecyclerView.HORIZONTAL, false);
        binding.paymentRv.setLayoutManager(payLinearLayoutManager);

        linearLayoutManager = new LinearLayoutManager(getActivityy());
        binding.productsRecycler.setLayoutManager(linearLayoutManager);
        binding.productsRecycler.setHasFixedSize(false);
        binding.productsRecycler.setAnimation(null);

        getExtraIntent();

        getDeliveryTimeList(storeId);

        getPaymentMethod(storeId);

        binding.DeliveryDateTv.setOnClickListener(view1 -> {

            int dateId = 0;
            searchDayList = new ArrayList<>();
            for (String s : datesMap.keySet()) {
                SearchListItem searchListItem = new SearchListItem(dateId, s);
                searchDayList.add(searchListItem);
                dateId++;
            }

            deliverDayDialog = new SearchableDialog(getActivity(), searchDayList, getString(R.string.choose_day));
            deliverDayDialog.setOnItemSelected((i, deliverDayList) -> {
                binding.DeliveryDateTv.setText(deliverDayList.getTitle());
                deliveryTimesList = datesMap.get(deliverDayList.getTitle());

                initSearchTimesList();

            });
            deliverDayDialog.show();
        });

        binding.TvDeliveryTime.setOnClickListener(view1 -> {

            deliverTimeDialog = new SearchableDialog(getActivity(), searchTimeList, getString(R.string.choose_time));
            deliverTimeDialog.setOnItemSelected((i, deliverTimeList) -> {
                binding.TvDeliveryTime.setText(deliverTimeList.getTitle());
                deliveryDateId = deliveryTimesList.get(i).getId();
            });
            deliverTimeDialog.show();

        });


        binding.DeliverLY.setOnClickListener(v -> {
            Intent intent = new Intent(getActivityy(), AddressActivity.class);
            intent.putExtra(Constants.delivery_choose, true);
            startActivityForResult(intent, ADDRESS_CODE);

        });


        binding.saveBut.setOnClickListener(view1 -> {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_cart));
        });

        binding.sendOrder.setOnClickListener(view1 -> {

            if (NumberHandler.getDouble(total) < minimum_order_amount) {

                Toasty.warning(getActivityy(), getString(R.string.minimum_order_amount) + minimum_order_amount, Toast.LENGTH_SHORT, true).show();

            } else {
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


                if (deliveryTimesList.size() == 0) {

                    GlobalData.errorDialog(getActivityy(), R.string.make_order, getString(R.string.fail_to_send_order_no_times));

                } else {

                    if (paymentMethod.equals("CC")) {
                        binding.DeliverLY.setVisibility(View.GONE);

                        sendOrder(orderCall);


                    } else {
                        if (addressId == 0) {

                            Toasty.error(getActivityy(), R.string.choose_address, Toast.LENGTH_SHORT, true).show();
                            binding.deliveryAddressTv.setError(getString(R.string.choose_address));
                            binding.deliveryAddressTv.requestFocus();
                        } else {

                            sendOrder(orderCall);
                        }

                    }

                }


            }


        });


        if (deliveryFees == 0) {
            binding.deliveryFees.setText(getString(R.string.free));

        } else {
            binding.deliveryFees.setText(NumberHandler.formatDouble(deliveryFees, localModel.getFractional()).concat("" + currency));

        }


        return view;
    }

    private void initSearchTimesList() {

        int timeId = 0;
        searchTimeList = new ArrayList<>();
        for (DeliveryTime timeModel : deliveryTimesList) {
            SearchListItem searchListItem = new SearchListItem(timeId, timeModel.getTime());
            searchTimeList.add(searchListItem);
            timeId++;
        }
    }

    public void initAdapter() {

        paymentAdapter = new PaymentAdapter(getActivityy(), paymentList, (obj, func, IsSuccess) -> {
            PaymentModel paymentModel = (PaymentModel) obj;
            if (paymentMethod != null) {
                paymentMethod = paymentModel.getShortname();

                if (paymentMethod.equals("CC")) {
                    binding.DeliverLY.setVisibility(View.GONE);
                    expressDelivery = true;

                } else {
                    binding.DeliverLY.setVisibility(View.VISIBLE);
                    expressDelivery = false;

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
            String message = getString(R.string.fail_to_get_data);
            binding.loadingLYPay.setVisibility(View.GONE);
            PaymentResultModel result = (PaymentResultModel) obj;
            if (func.equals(Constants.ERROR)) {

                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.Toast(getActivityy(), message);


            } else if (func.equals(Constants.FAIL)) {
                GlobalData.Toast(getActivityy(), message);

            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActivityy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {
                        paymentList = result.getData();
                        paymentMethod = result.getData().get(0).getShortname();
                        initAdapter();
                        Log.i(TAG, "Log getPaymentMethod" + result.getData().size());


                    }


                }
            }


        }).getPaymentMethod(user_id);
    }


    public void getDeliveryTimeList(int user_id) {

        datesMap.clear();
//        deliveryTimesList.clear();

        binding.loadingDelivery.setVisibility(View.VISIBLE);
        binding.deliveryLy.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            binding.loadingDelivery.setVisibility(View.GONE);
            String message = getString(R.string.fail_to_get_data);
            DeliveryResultModel result = (DeliveryResultModel) obj;


            if (func.equals(Constants.ERROR)) {

                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.Toast(getActivityy(), message);


            } else if (func.equals(Constants.FAIL)) {
                GlobalData.Toast(getActivityy(), message);

            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(getActivityy(), R.string.no_internet_connection);
            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {
                        binding.deliveryLy.setVisibility(View.VISIBLE);
                        List<DeliveryTime> datesList = result.getData();

                        DeliveryTime firstTime = datesList.get(0);
                        binding.DeliveryDateTv.setText(firstTime.getDate());
                        binding.TvDeliveryTime.setText(firstTime.getTime());
                        deliveryDateId = firstTime.getId();

                        Log.i(TAG, "Log deliveryTimesList" + result.getData().size());

                        String currentDate = firstTime.getDate();
                        List<DeliveryTime> timesList = new ArrayList<>();
                        for (int i = 0; i < datesList.size(); i++) {
                            DeliveryTime deliveryTime = datesList.get(i);

                            if (deliveryTime.getDate().equals(currentDate)) {
                                timesList.add(deliveryTime);
                            } else if (!deliveryTime.getDate().equals(currentDate) || i == datesList.size() - 1) {
                                datesMap.put(currentDate, timesList);
                                currentDate = deliveryTime.getDate();
                                timesList= new ArrayList<>();
                                timesList.add(deliveryTime);
                            }

                        }

                        deliveryTimesList = datesMap.get(firstTime.getDate());
                        initSearchTimesList();

//                        for (int i = 0; i < noRepeat.size(); i++) {
//                            SearchListItem searchListItem = new SearchListItem(i, noRepeat.get(i).getDate());
//                            SearchListItem searchListItem1 = new SearchListItem(i, noRepeat.get(i).getTime());
//
//                            deliverDayList.add(searchListItem);
//                            deliverTimeList.add(searchListItem1);
//                        }


                    } else {
                        binding.noDeliveryTv.setVisibility(View.VISIBLE);
                    }

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
            cartResultModel = (CartResultModel) bundle.getSerializable(Constants.CART_MODEL);
            deliveryFees = cartResultModel.getDeliveryCharges();
            productList = cartResultModel.getData().getCartData();
            binding.productsSizeTv.setText(String.valueOf(productsSize));
            binding.totalTv.setText(total.concat(" " + currency));
            initProductAdapter();
        }


    }

    private void initProductAdapter() {

        invoiceProductAdapter = new InvoiceItemAdapter(getActivityy(), productList, this, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {

                CartProcessModel cartProcessModel = (CartProcessModel) obj;
                total = NumberHandler.formatDouble(cartProcessModel.getTotal(), fraction);
                binding.totalTv.setText(total.concat(" " + currency));
                binding.productsSizeTv.setText(String.valueOf(cartProcessModel.getCartCount()));
                if (cartProcessModel.getCartCount() == 0) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    CartFragment cartFragment = new CartFragment();
                    fragmentManager.beginTransaction().replace(R.id.mainContainer, cartFragment, "cartFragment").commit();

                }


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
                GlobalData.errorDialog(getActivityy(), R.string.make_order, message);
            } else {
                if (IsSuccess) {

                    UtilityApp.setCartCount(0);
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






