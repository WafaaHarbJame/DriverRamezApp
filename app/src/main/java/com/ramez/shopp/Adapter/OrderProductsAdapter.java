package com.ramez.shopp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ramez.shopp.Models.OrderProductsModel;
import com.ramez.shopp.R;

import java.util.List;



public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.Holder> {

    private Context context;
    private List<OrderProductsModel> orderProductsDMS;
    private String currency="AED";
    public OrderProductsAdapter(Context context, List<OrderProductsModel> orderProductsDMS) {
        this.context = context;
        this.orderProductsDMS = orderProductsDMS;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_products_invoive_details, parent, false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        OrderProductsModel orderProductsDM = orderProductsDMS.get(position);

            holder.textItemName.setText(orderProductsDM.getName());

        holder.textQTY.setText(orderProductsDM.getPro_qua() + " * " + orderProductsDM.getPro_price() + " " + currency);
        holder.textItemPrice.setText(orderProductsDM.getPro_price() + " " + currency);

        Glide.with(context).load(orderProductsDM.getPro_image()).placeholder(R.drawable.holder_image).thumbnail(0.05f).into(holder.productImage);


    }


    @Override
    public int getItemCount() {
        return orderProductsDMS.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textQTY, textItemName, textItemPrice;
        RecyclerView listExtras;
        ImageView productImage;
        Button reOrderProductBtn;


        public Holder(View view) {
            super(view);
            textQTY = view.findViewById(R.id.qty_text);
            textItemName = view.findViewById(R.id.item_text);
            textItemPrice = view.findViewById(R.id.price_text);
            productImage = view.findViewById(R.id.imageView1);
            reOrderProductBtn = view.findViewById(R.id.reOrderProductBtn);

            reOrderProductBtn.setOnClickListener(v -> {


            });









        }
    }


}



