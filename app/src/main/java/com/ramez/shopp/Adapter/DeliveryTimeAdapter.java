package com.ramez.shopp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.CallBack.DataCallback;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.DeliveryTime;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowDeliveyTimeBinding;

import java.util.List;


public class DeliveryTimeAdapter extends RecyclerView.Adapter<DeliveryTimeAdapter.ViewHolder> {

    private static final String TAG = "DeliveryTimeAdapter";
    public int lastIndex = 0;
    public String currency = "BHD";
    Context context;
    DataCallback dataCallback;
    private List<DeliveryTime> deliveryTimesList;


    public DeliveryTimeAdapter(Context context, List<DeliveryTime> deliveryTimesList, DataCallback dataCallback) {
        this.deliveryTimesList = deliveryTimesList;
        this.context = context;
        this.dataCallback = dataCallback;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        RowDeliveyTimeBinding itemView = RowDeliveyTimeBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        DeliveryTime deliveryTimes = deliveryTimesList.get(position);
        viewHolder.binding.deliveryTime.setText(deliveryTimes.getTime());
        viewHolder.binding.deliveryPrice.setText("1".concat(UtilityApp.getLocalData().getCurrencyCode()));

        viewHolder.binding.deliveryPrice.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(1.0)), UtilityApp.getLocalData().getFractional()) + " " + currency);


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

        RowDeliveyTimeBinding binding;

        ViewHolder(RowDeliveyTimeBinding view) {
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
