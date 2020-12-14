package com.ramez.shopp.Adapter;

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
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowSuggestedProductItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SuggestedProductAdapter extends RecyclerView.Adapter<SuggestedProductAdapter.Holder> {
    private Context context;
    private  OnItemClick onItemClick;
    private ArrayList<ProductModel> productModels;
    private double discount = 0.0;
    private String currency="BHD";
    private  int limit = 2;



    public SuggestedProductAdapter(Context context, OnItemClick onItemClick, ArrayList<ProductModel> productModels, int limit) {
        this.context = context;
        this.onItemClick = onItemClick;
        this.productModels = productModels;
        this.limit=limit;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowSuggestedProductItemBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_suggested_product_item, parent, false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ProductModel productModel = productModels.get(position);
        currency=UtilityApp.getLocalData().getCurrencyCode();
        if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
            if(UtilityApp.getLanguage().equals(Constants.Arabic))
                holder.binding.productNameTv.setText(productModel.getHName().trim());
            else
                holder.binding.productNameTv.setText(productModel.getName().trim());

        }

        if(productModel.getFavourite()!=null&& productModel.getFavourite()){
            holder.binding.favBut.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.favorite_icon));
        }
        else {
            holder.binding.favBut.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.empty_fav));

        }



        if ( productModel.getProductBarcodes().get(0).getIsSpecial()) {
            holder.binding.productPriceBeforeTv.setPaintFlags(holder.binding.productPriceBeforeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (productModel.getProductBarcodes().get(0).getSpecialPrice() != null) {
                holder.binding.productPriceBeforeTv.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice())),UtilityApp.getLocalData().getFractional()) + " " + currency);
                holder.binding.productPriceTv.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getSpecialPrice())),UtilityApp.getLocalData().getFractional()) + " " + currency);
                discount = (Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice())) - Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getSpecialPrice()))) / (Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice()))) * 100;
                DecimalFormat df = new DecimalFormat("#");
                String newDiscount_str = df.format(discount);
                holder.binding.discountTv.setText(NumberHandler.arabicToDecimal(newDiscount_str) + " % "+"OFF");
            }


        } else {
            if (productModel.getProductBarcodes().get(0).getPrice() != null) {
                holder.binding.productPriceTv.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice())) + " " + currency + "",UtilityApp.getLocalData().getFractional()));
                holder.binding.productPriceBeforeTv.setVisibility(View.INVISIBLE);
                holder.binding.discountTv.setVisibility(View.INVISIBLE);

            }
        }


        Glide.with(context).asBitmap()
                .load(productModel.getImages().get(0)).placeholder(R.drawable.image_product).placeholder(R.drawable.image_product)
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

        RowSuggestedProductItemBinding binding;

        Holder(RowSuggestedProductItemBinding view) {
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
