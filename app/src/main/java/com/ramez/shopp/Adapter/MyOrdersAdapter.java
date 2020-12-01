package com.ramez.shopp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.ramez.shopp.Models.OrdersHeaderModel;
import com.ramez.shopp.Models.OrdersModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowCurrentMyOrderItemBinding;
import com.ramez.shopp.databinding.RowLoadingBinding;
import com.ramez.shopp.databinding.RowOrderHeaderItemBinding;

import java.util.List;


public class MyOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_HEADER = 2;
    public static final int VIEW_TYPE_LOADING = 3;
    private static final String TAG = "MyOrdersAdapter";
    public boolean isLoading;
    public int nextPage = 1;
    public boolean show_loading = true;
    public int visibleThreshold = 5;
    private Context context;
    private List objectsList;
    private int lastVisibleItem;
    private int totalItemCount;
    private int userId;
    private OnLoadMoreListener mOnLoadMoreListener;

    public MyOrdersAdapter(Context context, RecyclerView rv, List ordersDMS, int userId) {
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


                if (show_loading) {
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                            isLoading = true;
                        }
                    }
                }
                setOnloadListener();

            }

        });

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_TYPE_ITEM) {
            RowCurrentMyOrderItemBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_current_my_order_item, parent, false);
            vh = new ItemHolder(itemView);
        } else if (viewType == VIEW_TYPE_HEADER) {
            RowOrderHeaderItemBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_order_header_item, parent, false);
            vh = new HeaderViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_LOADING) {
            RowLoadingBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_loading, parent, false);
            vh = new LoadingViewHolder(itemView);

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemHolder) {
            ItemHolder holder = (ItemHolder) viewHolder;
            final OrdersModel ordersDM = (OrdersModel) objectsList.get(position);

            if (ordersDM.getOrderStatus().equals("waiting") ||
                    ordersDM.getOrderStatus().equals("onway") ||
                    ordersDM.getOrderStatus().equals("processing")) {
                holder.binding.completeOrderLy.setVisibility(View.GONE);
                holder.binding.currentLY.setVisibility(View.VISIBLE);
                holder.binding.currentLY1.setVisibility(View.VISIBLE);

            } else {
                holder.binding.completeOrderLy.setVisibility(View.VISIBLE);
                holder.binding.currentLY.setVisibility(View.GONE);
                holder.binding.currentLY1.setVisibility(View.GONE);

            }

            holder.binding.noteCTv.setText(ordersDM.getHistory_en_comments());
            holder.binding.TvDeliveryDay.setText(ordersDM.getDeliveryDay());
            holder.binding.TvDeliveryTime.setText(ordersDM.getDeliveryTime());


            if (ordersDM.getPlaced_is_step_finished() == 1) {
                holder.binding.doneImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
            }
            if (ordersDM.getProcessing_is_step_finished() == 1) {
                holder.binding.processImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
            }
            if (ordersDM.getShipped_is_step_finished() == 1) {
                holder.binding.deliveryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));

            }
            if (ordersDM.getCompleted_is_step_finished() == 1) {
                holder.binding.doneDeliveryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.request_choose));
            }


            holder.binding.rateOrderBtn.setOnClickListener(v -> {
                Intent intent = new Intent(context, RatingActivity.class);
                intent.putExtra(Constants.inv_id, ordersDM.getInvID() + "");
                Log.d(TAG, "inv_id" + ordersDM.getInvID() + "");
                context.startActivity(intent);
            });

            holder.binding.container.setOnClickListener(v -> {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra(Constants.inv_id, ordersDM.getInvID() + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            });

            holder.binding.orderDetailsBut.setOnClickListener(view -> {
                Intent intent = new Intent(context, InvoiceInfoActivity.class);
                intent.putExtra(Constants.inv_id, ordersDM.getInvID() + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });


        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;

            OrdersHeaderModel ordersHeader = (OrdersHeaderModel) objectsList.get(position);

            holder.binding.titleTxt.setText(ordersHeader.getTitle());
            holder.binding.countTxt.setText(NumberHandler.arabicToDecimal("(" + ordersHeader.getCount() + ")"));


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
        else if (objectsList.get(position) instanceof OrdersHeaderModel) return VIEW_TYPE_HEADER;
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

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        RowOrderHeaderItemBinding binding;

        HeaderViewHolder(RowOrderHeaderItemBinding view) {
            super(view.getRoot());
            binding = view;

        }

    }


}