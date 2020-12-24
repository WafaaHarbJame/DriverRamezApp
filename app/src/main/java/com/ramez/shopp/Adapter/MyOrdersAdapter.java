package com.ramez.shopp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Activities.InvoiceInfoActivity;
import com.ramez.shopp.Activities.OrderDetailsActivity;
import com.ramez.shopp.Activities.RatingActivity;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.OnLoadMoreListener;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.OrdersHeaderModel;
import com.ramez.shopp.Models.OrdersModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.DateHandler;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowCurrentMyOrderItemBinding;
import com.ramez.shopp.databinding.RowLoadingBinding;
import com.ramez.shopp.databinding.RowOrderHeaderItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_LOADING = 3;
    private static final String TAG = "MyOrdersAdapter";
    public boolean isLoading;
    public int nextPage = 1;
    public boolean show_loading = true;
    public int visibleThreshold = 5;
    String lang;
    private Context context;
    private ArrayList<OrdersModel> objectsList;
    private int lastVisibleItem;
    private int totalItemCount;
    private int userId;
    private OnLoadMoreListener mOnLoadMoreListener;

    public MyOrdersAdapter(Context context, RecyclerView rv, ArrayList<OrdersModel> ordersDMS, int userId) {
        this.context = context;
        this.objectsList = ordersDMS;
        this.userId = userId;


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        rv.setLayoutManager(linearLayoutManager);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

//
//                if (show_loading) {
//                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                        if (mOnLoadMoreListener != null) {
//                            mOnLoadMoreListener.onLoadMore();
//                            isLoading = true;
//                        }
//                    }
//                }
//                setOnloadListener();

            }

        });

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_TYPE_ITEM) {
            RowCurrentMyOrderItemBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_current_my_order_item, parent, false);
            vh = new ItemHolder(itemView);
        } else if (viewType == VIEW_TYPE_LOADING) {
            RowLoadingBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_loading, parent, false);
            vh = new LoadingViewHolder(itemView);

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        lang = UtilityApp.getLanguage();
        if (viewHolder instanceof ItemHolder) {
            ItemHolder holder = (ItemHolder) viewHolder;
            final OrdersModel ordersDM = objectsList.get(position);

            holder.binding.tvInvID.setText(ordersDM.getOrderCode());
            holder.binding.tvShopName.setText(R.string.app_name);

            //delivery_status = PN:Pending || RC:Received || IP:Processing || CA:CheckoutArea ||
            // PP:PendingPayment || OH:OnHold || OP:Open  || CM:Complete  || CL:Canceled  || DV:Delivered || Not Defined || CheckoutArea


            if (ordersDM.getDeliveryStatus().equals("Processing") || ordersDM.getDeliveryStatus().equals("Received") || ordersDM.getDeliveryStatus().equals("Pending") || ordersDM.getDeliveryStatus().equals("Open") || ordersDM.getDeliveryStatus().equals("CheckoutArea") || ordersDM.getDeliveryStatus().equals("Not Defined")) {

                holder.binding.completeOrderLy.setVisibility(View.GONE);
                holder.binding.currentLY.setVisibility(View.VISIBLE);
                holder.binding.currentLY1.setVisibility(View.VISIBLE);

            } else {
                holder.binding.completeOrderLy.setVisibility(View.VISIBLE);
                holder.binding.currentLY.setVisibility(View.GONE);
                holder.binding.currentLY1.setVisibility(View.GONE);

            }

            holder.binding.noteCTv.setText(ordersDM.getDeliveryStatus());

            Date OrderDate = null;
            try {
                OrderDate = new SimpleDateFormat("yyyy-MM-dd").parse(ordersDM.getDeliveryDate());
                String orderDayTime = NumberHandler.arabicToDecimal(DateHandler.GetTimeString(OrderDate, lang));

                String OrderDayName = (DateHandler.FormatDate4(ordersDM.getDeliveryDate(), "yyyy-MM-dd", "EEEE", lang));
                String deliveryDayName = (DateHandler.FormatDate4(ordersDM.getDeliveryDate(), "yyyy-MM-dd", "EEEE", lang));

                holder.binding.TvDeliveryDay.setText(deliveryDayName);
                holder.binding.tvOrderDay.setText(OrderDayName);


            } catch (ParseException e) {
                e.printStackTrace();
            }


            holder.binding.TvDeliveryTime.setText(ordersDM.getDeliveryTime());

            //This order status that reading from Api :  PN:PENDING , RC RECEIVED  ,   CA CHECK OUT , PP PENDING PAYMENT , OH ON HOLD, OP:OPEN ,
            // CM: COMPETE  CL CANCELED < DV DELIVERED done , cancelled


            if (ordersDM.getOrderStatus().equals("PN")) {
                holder.binding.doneImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
            } else if (ordersDM.getOrderStatus().equals("IP")) {
                holder.binding.processImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
                holder.binding.doneImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));

            } else if (ordersDM.getOrderStatus().equals("DV")) {
                holder.binding.deliveryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
                holder.binding.processImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
                holder.binding.doneImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));


            } else if (ordersDM.getOrderStatus().equals("CM")) {
                holder.binding.doneDeliveryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
                holder.binding.deliveryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
                holder.binding.processImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
                holder.binding.doneImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));


            }


            holder.binding.rateOrderBtn.setOnClickListener(v -> {
                Intent intent = new Intent(context, RatingActivity.class);
                intent.putExtra(Constants.inv_id, ordersDM.getOrderCode() + "");
                Log.d(TAG, "inv_id" + ordersDM.getOrderCode() + "");
                context.startActivity(intent);
            });

            holder.binding.container.setOnClickListener(v -> {

                Log.i(TAG, "inv_id " + ordersDM.getOrderCode() + "");
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra(Constants.inv_id, ordersDM.getOrderCode() + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            });

            holder.binding.orderDetailsBut.setOnClickListener(view -> {
                Intent intent = new Intent(context, InvoiceInfoActivity.class);
                intent.putExtra(Constants.inv_id, ordersDM.getOrderCode() + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });


        } else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
            loadingViewHolder.rowLoadingBinding.progressBar1.setIndeterminate(true);
        }


    }

    @Override
    public int getItemCount() {
        return objectsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectsList.get(position) == null) return VIEW_TYPE_LOADING;
        else return VIEW_TYPE_ITEM;

    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    private void setOnloadListener() {

        setOnLoadMoreListener(() -> {
            System.out.println("Log add loading item");
            if (!objectsList.contains(null)) {
                objectsList.add(null);
                notifyItemInserted(objectsList.size() - 1);
                LoadData();
            }

        });

    }

    public void LoadData() {
        Log.d(TAG, "Log LoadData " + "LoadData");

    }


    public static class ItemHolder extends RecyclerView.ViewHolder {
        RowCurrentMyOrderItemBinding binding;

        public ItemHolder(RowCurrentMyOrderItemBinding view) {
            super(view.getRoot());
            binding = view;

        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        RowLoadingBinding rowLoadingBinding;

        LoadingViewHolder(RowLoadingBinding view) {
            super(view.getRoot());
            rowLoadingBinding = view;

        }

    }


}