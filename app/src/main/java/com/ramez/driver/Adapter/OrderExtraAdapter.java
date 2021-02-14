package com.ramez.driver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.ExtraDM;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;

import java.util.List;

public class OrderExtraAdapter extends RecyclerView.Adapter<OrderExtraAdapter.MyViewHolder> {

    private Context context;
    private List<ExtraDM> extraDMS;
    MemberModel userInfo;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textQuantity, textName, textPrice;


        public MyViewHolder(View view) {
            super(view);
            userInfo = UtilityApp.getUserData();
            textQuantity = view.findViewById(R.id.qty_text);
            textName = view.findViewById(R.id.item_text);
            textPrice = view.findViewById(R.id.price_text);

        }
    }

    public OrderExtraAdapter(Context context, List<ExtraDM> extraDMS) {
        this.context = context;
        this.extraDMS = extraDMS;
    }

    @Override
    public OrderExtraAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_products_my_order, parent, false);

        return new OrderExtraAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final OrderExtraAdapter.MyViewHolder holder, int position) {
        String EGP = context.getResources().getString(R.string.EGP);
        holder.textQuantity.setText("X" + " " + extraDMS.get(position).getExtraQuantity());
        holder.textName.setText(extraDMS.get(position).getExtraName());
        holder.textPrice.setText(extraDMS.get(position).getExtraTotalPrice() + " " + EGP);
    }

    @Override
    public int getItemCount() {
        return extraDMS.size();
    }
}
