package com.ramez.driver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.driver.CallBack.DataCallback;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.DeliveryTime;
import com.ramez.driver.R;
import com.ramez.driver.Utils.NumberHandler;
import com.ramez.driver.databinding.RowDeliveryTimesBinding;

import java.util.List;


public class DeliveryTimeAdapter extends RecyclerView.Adapter<DeliveryTimeAdapter.ViewHolder> {

    private static final String TAG = "DeliveryTimeAdapter";
    public int lastIndex = 0;
    public String currency = "BHD";
    Context context;
    DataCallback dataCallback;
    private List<DeliveryTime> deliveryTimesList;
    private  Double deliveryFees;


    public DeliveryTimeAdapter(Context context, List<DeliveryTime> deliveryTimesList,Double deliveryFees, DataCallback dataCallback) {
        this.deliveryTimesList = deliveryTimesList;
        this.context = context;
        this.dataCallback = dataCallback;
        this.deliveryFees=deliveryFees;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        RowDeliveryTimesBinding itemView = RowDeliveryTimesBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        DeliveryTime deliveryTimes = deliveryTimesList.get(position);
        viewHolder.binding.deliveryTime.setText(deliveryTimes.getTime());

        currency=UtilityApp.getLocalData().getCurrencyCode();

        if(deliveryFees==0){
            viewHolder.binding.deliveryPrice.setText(context.getString(R.string.free));

        }
        else {
            viewHolder.binding.deliveryPrice.setText(NumberHandler
                    .formatDouble(deliveryFees,
                            UtilityApp.getLocalData().getFractional()) + " " + currency);

        }



        if (lastIndex == position) {
            viewHolder.binding.selectTxt.setText(context.getString(R.string.fa_circle));
            viewHolder.binding.selectTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        } else {
            viewHolder.binding.selectTxt.setText(context.getString(R.string.fa_circle_o));
            viewHolder.binding.selectTxt.setTextColor(ContextCompat.getColor(context, R.color.header3));

        }


    }

    @Override
    public int getItemCount() {
        return deliveryTimesList.size();
    }

    public interface onTimeSelected {
        void onTimeSelected(DeliveryTime deliveryTime, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RowDeliveryTimesBinding binding;

        ViewHolder(RowDeliveryTimesBinding view) {
            super(view.getRoot());
            binding = view;
            itemView.setOnClickListener(view1 -> {
                DeliveryTime deliveryTime = deliveryTimesList.get(getAdapterPosition());
                lastIndex = getAdapterPosition();
                notifyDataSetChanged();

                if (dataCallback != null) {
                    dataCallback.dataResult(deliveryTime, "result", true);
                }
            });


        }
    }


}
