package com.ramez.ramez.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ramez.ramez.Classes.Constants;
import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.Models.ProductModel;
import com.ramez.ramez.R;
import com.ramez.ramez.Utils.NumberHandler;
import com.ramez.ramez.databinding.RowCartItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    private Context context;
    private  OnItemClick onItemClick;
    private ArrayList<ProductModel> productModels;
    private double discount = 0.0;
    private String currency="AED";
    private  int limit = 2;



    public ProductAdapter(Context context, OnItemClick onItemClick, ArrayList<ProductModel> productModels,int limit) {
        this.context = context;
        this.onItemClick = onItemClick;
        this.productModels = productModels;
        this.limit=limit;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowCartItemBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_cart_item, parent, false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ProductModel productModel = productModels.get(position);
        if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
            if(UtilityApp.getLanguage().equals(Constants.Arabic))
            holder.binding.productNameTv.setText(productModel.getPro_name_ar().trim());
            else
                holder.binding.productNameTv.setText(productModel.getPro_name_en().trim());

        }

        if(productModel.getIsFavorite()==1){
            holder.binding.favBut.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.favorite_icon));
        }
        else {
            holder.binding.favBut.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.empty_fav));

        }


        if (productModel.getIs_special() == 1) {
            holder.binding.productPriceBeforeTv.setPaintFlags(holder.binding.productPriceBeforeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (productModel.getPro_price() != null) {
                holder.binding.productPriceBeforeTv.setText(NumberHandler.formatDouble(Double.parseDouble(productModel.getPro_price())) + " " + currency);
                holder.binding.productPriceTv.setText(NumberHandler.formatDouble(Double.parseDouble(productModel.getPro_special_price())) + " " + currency);
                discount = (Double.parseDouble(productModel.getPro_price()) - Double.parseDouble(productModel.getPro_special_price())) / (Double.parseDouble(productModel.getPro_price())) * 100;
                DecimalFormat df = new DecimalFormat("#");
                String newDiscount_str = df.format(discount);
                holder.binding.discountTv.setText(NumberHandler.arabicToDecimal(newDiscount_str) + " % "+"OFF");
            }


        } else {
            if (productModel.getPro_price() != null) {
                holder.binding.productPriceTv.setText(NumberHandler.formatDouble(Double.parseDouble(productModel.getPro_price())) + " " + currency + "");
                holder.binding.productPriceBeforeTv.setVisibility(View.INVISIBLE);
                holder.binding.discountTv.setVisibility(View.INVISIBLE);

            }
        }


        Glide.with(context).asBitmap()
                .load("http" + productModel.getPro_img()).placeholder(R.drawable.image_product).placeholder(R.drawable.image_product)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.binding.productImg);



    }

    @Override
    public int getItemCount() {
        if(limit==2)
            return Math.min(productModels.size(), limit);
        else
            return productModels.size();



    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RowCartItemBinding binding;

        Holder(RowCartItemBinding view) {
            super(view.getRoot());
            binding = view;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (onItemClick != null) {
                onItemClick.onItemClicked(getAdapterPosition(), productModels.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClick {
        void onItemClicked(int position,ProductModel productModel);
    }
}
