package com.ramez.driver.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ramez.driver.Adapter.AdapterProductsOrder;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.HomeActivity;
import com.ramez.driver.Models.ExtraDM;
import com.ramez.driver.Models.InvoiceInfoModel;
import com.ramez.driver.Models.LocalModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.OrderItemDetail;
import com.ramez.driver.Models.RamezOrderCall;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.Models.Row_Products_My_Order;
import com.ramez.driver.R;
import com.ramez.driver.Utils.ActivityHandler;
import com.ramez.driver.Utils.DateHandler;
import com.ramez.driver.Utils.Helpers;
import com.ramez.driver.Utils.NumberHandler;
import com.ramez.driver.databinding.ActivityChangeCityBranchBinding;
import com.ramez.driver.databinding.ActivityGetMyInvoiceInfoctivityBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class GetMyInvoiceInfoActivity extends ActivityBase {
    MemberModel memberModel;
    String inv_ids = "", type = "";
    ActivityGetMyInvoiceInfoctivityBinding binding;
    String currency = "BHD";
    LocalModel localModel;
    String LangCode;
    String user_mobile = "";
    int is_picked = 0;
    int picker_id = 0;
    double invLat = 0.0, invLng = 0.0;
    int delivery_id = 0;
    private boolean UserType;
    private ArrayList<OrderItemDetail> unPickedArrayList;
    private ArrayList<OrderItemDetail> PickedArrayList;
    private ArrayList<ExtraDM> extraDMS;
    private AdapterProductsOrder adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetMyInvoiceInfoctivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        unPickedArrayList = new ArrayList<>();
        PickedArrayList = new ArrayList<>();
        extraDMS = new ArrayList<>();
        setTitle(getString(R.string.InvoiceDetials));

        memberModel = UtilityApp.getUserData();
        if (UtilityApp.getLanguage() != null) {
            LangCode = UtilityApp.getLanguage();
        } else {
            LangCode = Locale.getDefault().getLanguage();
        }
        if (UtilityApp.getLocalData() != null) {
            localModel = UtilityApp.getLocalData();
            currency = localModel.getCurrencyCode();

        }

        inv_ids = getIntent().getExtras().getString(Constants.id);
        UserType = getIntent().getBooleanExtra(Constants.ramezrequest, false);

        allClick();


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActiviy());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setHasFixedSize(true);


        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActiviy());
        binding.listRequest.setLayoutManager(mLayoutManager1);
        binding.listRequest.setHasFixedSize(true);


        if (memberModel.getRole_id() == 6) {
            type = "Packer";
        } else if (memberModel.getRole_id() == 4) {
            type = "Driver";

        }

        getInvoiceInfo(memberModel.getId(), type, Integer.parseInt(inv_ids), Constants.one);
    }

    private void allClick() {
        binding.pickedBtn.setOnClickListener(view -> {
            binding.pickedIndc.setVisibility(View.VISIBLE);
            binding.unPickedIndc.setVisibility(View.INVISIBLE);
            binding.pickedTxt.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            binding.unPickedTxt.setTextColor(ContextCompat.getColor(this, R.color.white));
            is_picked = 1;
            initAdapter(PickedArrayList, unPickedArrayList, is_picked);
        });


        binding.unPickedBtn.setOnClickListener(view -> {
            binding.unPickedIndc.setVisibility(View.VISIBLE);
            binding.pickedIndc.setVisibility(View.INVISIBLE);
            binding.unPickedTxt.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            binding.pickedTxt.setTextColor(ContextCompat.getColor(this, R.color.white));
            is_picked = 0;
            initAdapter(PickedArrayList, unPickedArrayList, is_picked);

        });

        binding.btnChat.setOnClickListener(view -> {
            Intent intent = new Intent(getActiviy(), NewChatActivity.class);

            if (memberModel.getRole_id() == 6) {
                intent.putExtra(Constants.user_id, String.valueOf(memberModel.getId()));
                intent.putExtra(Constants.order_id, inv_ids);
                intent.putExtra(Constants.provider_id, picker_id + "");
                intent.putExtra(Constants.provider_name, memberModel.getName() + "");
                intent.putExtra(Constants.inv_lat, invLat);
                intent.putExtra(Constants.inv_lng, invLng);
                intent.putExtra(Constants.user_firstname, memberModel.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (memberModel.getRole_id() == 4) {
                intent.putExtra(Constants.user_id, String.valueOf(memberModel.getId()));
                intent.putExtra(Constants.order_id, inv_ids);
                intent.putExtra(Constants.provider_id, delivery_id + "");
                intent.putExtra(Constants.provider_name, memberModel.getName());
                intent.putExtra(Constants.user_firstname, memberModel.getName());
                intent.putExtra(Constants.inv_lat, invLat);
                intent.putExtra(Constants.inv_lng, invLng);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (memberModel.getRole_id() == 2) {
                intent.putExtra(Constants.user_id, String.valueOf(memberModel.getId()));
                intent.putExtra(Constants.order_id, inv_ids);
                intent.putExtra(Constants.provider_id, String.valueOf(memberModel.getId()));
                intent.putExtra(Constants.provider_name, memberModel.getName());
                intent.putExtra(Constants.user_firstname, memberModel.getName());
                intent.putExtra(Constants.inv_lat, invLat);
                intent.putExtra(Constants.inv_lng, invLng);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        binding.dataLY.setOnRefreshListener(() -> {
            binding.dataLY.setRefreshing(false);
            getInvoiceInfo(memberModel.getId(), type, Integer.parseInt(inv_ids), Constants.one);

        });


        binding.send.setOnClickListener(v -> {
            // volley_SetPrescriptionPrice();

        });
        binding.phoneCall.setOnClickListener(v -> callPhoneNumber(user_mobile));


        binding.buttonContactWhatsUp.setOnClickListener(v -> {
            Helpers.openChatByWhatUp(getActiviy(), user_mobile);


        });


        binding.ivOrderLocation.setOnClickListener(v -> {
            if (invLng == 0) {
                Toast.makeText(getActiviy(), getString(R.string.textTrackProviderUnRecognized), Toast.LENGTH_SHORT).show();
            } else {
                showLocationOnMaps(invLat, invLng, "");
            }
        });


        binding.btnAcceptOrder.setOnClickListener(view -> {
            final String confirm_procedure = getResources().getString(R.string.confirm_procedure);
            final String order_Processing = getResources().getString(R.string.order_Processing);
            final String Receiving_the_order = getResources().getString(R.string.Receiving_the_order);
            final String back = getResources().getString(R.string.back);


            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActiviy());
            builder.setTitle(order_Processing);
            builder.setMessage(confirm_procedure);
            builder.setCancelable(false);
            builder.setPositiveButton(Receiving_the_order, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (memberModel.getRole_id() == 6) {
                        //PickOrder(inv_ids + "", 1);


                    }

                    if (memberModel.getRole_id() == 4) {

                        // Volley_AssignDelivery(inv_ids + "");

                    }


                }
            });
            builder.setNegativeButton(back, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        });


    }


    @SuppressLint("SetTextI18n")
    public void getInvoiceInfo(int user_id, String type, int order_id, int mode) {

        RamezOrderCall ramezOrderCall = new RamezOrderCall();
        ramezOrderCall.type = type;
        ramezOrderCall.mode = mode;
        ramezOrderCall.user_id = user_id;
        ramezOrderCall.store_id = 7263;
        ramezOrderCall.order_id = order_id;

        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);


        new DataFeacher(false, (obj, func, IsSuccess) -> {

            ResultAPIModel<InvoiceInfoModel> result = (ResultAPIModel<InvoiceInfoModel>) obj;
            GlobalData.hideProgressDialog();


            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result != null) {
                    message = result.message;
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

            } else if (func.equals(Constants.NO_CONNECTION)) {
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(R.string.no_internet_connection);
                binding.failGetDataLY.noInternetIv.setVisibility(View.VISIBLE);
                binding.dataLY.setVisibility(View.GONE);

            } else {

                if (IsSuccess) {
                    binding.dataLY.setVisibility(View.VISIBLE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

                    String Number_Order = getResources().getString(R.string.Number_Order);
                    String ITEM = getResources().getString(R.string.ITEM);
                    InvoiceInfoModel invoiceInfoModel = result.data;
                    int inv_id_inv = invoiceInfoModel.getOrderId();
                    String userName = invoiceInfoModel.getAdrsName();
                    binding.invId.setText(Number_Order + " # " + inv_id_inv);
                    binding.nameUser.setText(userName);
                    double inv_total = invoiceInfoModel.getTotalAmount();
                    binding.totalPrice.setText(inv_total + " " + currency);
                    String date_choosed = invoiceInfoModel.getDeliveryDate();

//                    String deliveryDayName = (DateHandler.FormatDate4(date_choosed, "yyyy-MM-dd", "EEEE", LangCode));
//                    String deliveryTimeStr = NumberHandler.arabicToDecimal(DateHandler.FormatTime(date_choosed, LangCode));

                    binding.deliveryDateTv.setVisibility(View.GONE);
                    binding.statusTv.setVisibility(View.GONE);
                    binding.ivOrderLocation.setVisibility(View.GONE);
                    binding.line1.setVisibility(View.GONE);
                    binding.deliveryLy.setVisibility(View.GONE);
                    binding.Shipping.setVisibility(View.GONE);

                    user_mobile = "+".concat(localModel.getCurrencyCode())+ invoiceInfoModel.getAdrsMobileNumber();
                    binding.splitLine.setVisibility(View.GONE);
                    unPickedArrayList.clear();
                    PickedArrayList.clear();
                    binding.btnChat.setVisibility(View.GONE);
                    extraDMS = new ArrayList<>();
                    binding.createdAt.setText(date_choosed);


                    for (int i = 0; i < result.data.getOrderItemDetails().size(); i++) {
                        OrderItemDetail orderItemDetail = result.data.getOrderItemDetails().get(i);
                        extraDMS = new ArrayList<>();

                        if (is_picked == 0) {

                            unPickedArrayList.add(new OrderItemDetail(orderItemDetail.getId(), orderItemDetail.getOrderId(), orderItemDetail.getProductId(), orderItemDetail.getProductName(), orderItemDetail.gethProductName(), orderItemDetail.gethUnitName(), orderItemDetail.getProductBarcodeId(), orderItemDetail.getBarcode(), orderItemDetail.getProductPrice(), orderItemDetail.getCartPrice(), orderItemDetail.getQuantity(), orderItemDetail.getStockQty(), orderItemDetail.getImage(), orderItemDetail.getLimitQty(), orderItemDetail.getFromOffer(), orderItemDetail.getEndOffer(),
                                    orderItemDetail.getFromDate(), orderItemDetail.getBrandName(), orderItemDetail.getRemark(),
                                    true, is_picked, extraDMS));
                        } else {
                            PickedArrayList.add(new OrderItemDetail(orderItemDetail.getId(), orderItemDetail.getOrderId(), orderItemDetail.getProductId(),
                                    orderItemDetail.getProductName(), orderItemDetail.gethProductName(), orderItemDetail.gethUnitName(), orderItemDetail.getProductBarcodeId(),
                                    orderItemDetail.getBarcode(), orderItemDetail.getProductPrice(), orderItemDetail.getCartPrice(), orderItemDetail.getQuantity(),
                                    orderItemDetail.getStockQty(), orderItemDetail.getImage(), orderItemDetail.getLimitQty(), orderItemDetail.getFromOffer(),
                                    orderItemDetail.getEndOffer(), orderItemDetail.getFromDate(), orderItemDetail.getBrandName(), orderItemDetail.getRemark(),
                                    true, is_picked, extraDMS));
                        }


                    }


                    binding.items.setText(+unPickedArrayList.size() + " " + ITEM);

                    initAdapter(PickedArrayList, unPickedArrayList, is_picked);
                    adapter.notifyDataSetChanged();


                }
            }

        }).getInvoiceInfo(ramezOrderCall);


    }


    public void initAdapter(ArrayList<OrderItemDetail> pickedList, ArrayList<OrderItemDetail> unPickedList, int is_picked) {
        if (is_picked == 1) {
            adapter = new AdapterProductsOrder(getActiviy(), pickedList);
        } else {

            adapter = new AdapterProductsOrder(getActiviy(), unPickedList);


        }

        binding.recyclerView.setAdapter(adapter);


        if (unPickedArrayList.size() == 0 && binding.unPickedIndc.getVisibility() == View.VISIBLE && binding.pickedIndc.getVisibility() == View.INVISIBLE) {
            binding.noProductsTv.setVisibility(View.VISIBLE);

        } else if (PickedArrayList.size() == 0 && binding.pickedIndc.getVisibility() == View.VISIBLE && binding.unPickedIndc.getVisibility() == View.INVISIBLE) {
            binding.noProductsTv.setVisibility(View.VISIBLE);
        } else {
            binding.noProductsTv.setVisibility(View.GONE);

        }


        adapter.notifyDataSetChanged();


    }


    public void callPhoneNumber(String phone) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActiviy(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+973" + phone));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+973" + phone));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber(user_mobile);
            } else {
                Log.e("TAG", "Permission not Granted");
            }
        }
    }

    void showLocationOnMaps(double lat, double lng, String title) {
        String uriBegin = "geo:" + lat + "," + lng;
        String query = lat + "," + lng + "(" + title + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=15";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.Choose)));
    }

}