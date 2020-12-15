package com.ramez.shopp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowProductsItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    //    public int count = 1;
    private Context context;
    private OnItemClick onItemClick;
    private ArrayList<ProductModel> productModels;
    private double discount = 0.0;
    private String currency = "BHD";
    private int limit = 2;

    public ProductAdapter(Context context, ArrayList<ProductModel> productModels, OnItemClick onItemClick, int limit) {
        this.context = context;
        this.onItemClick = onItemClick;
        this.productModels = productModels;
        this.limit = limit;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowProductsItemBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_products_item, parent, false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ProductModel productModel = productModels.get(position);

        currency = UtilityApp.getLocalData().getCurrencyCode();

//        if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
//            if (UtilityApp.getLanguage().equals(Constants.Arabic))
//                holder.binding.productNameTv.setText(productModel.getHName().trim());
//            else holder.binding.productNameTv.setText(productModel.getName().trim());
//
//        }
        holder.binding.productNameTv.setText(productModel.getName().trim());

        if (productModel.getFavourite() != null && productModel.getFavourite()) {
            holder.binding.favBut.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favorite_icon));
        } else {
            holder.binding.favBut.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.empty_fav));

        }

        int quantity = productModel.getProductBarcodes().get(0).getCartQuantity();
        if (quantity > 0) {
            holder.binding.productCartQTY.setText(String.valueOf(quantity));
            holder.binding.CartLy.setVisibility(View.VISIBLE);
            holder.binding.cartBut.setVisibility(View.GONE);

            if (quantity == 1) {
                holder.binding.deleteCartBtn.setVisibility(View.VISIBLE);
                holder.binding.minusCartBtn.setVisibility(View.GONE);
            } else {
                holder.binding.minusCartBtn.setVisibility(View.VISIBLE);
                holder.binding.deleteCartBtn.setVisibility(View.GONE);
            }

        } else {
            holder.binding.CartLy.setVisibility(View.GONE);
            holder.binding.cartBut.setVisibility(View.VISIBLE);
        }


        if (productModel.getProductBarcodes().get(0).getIsSpecial()) {
            holder.binding.productPriceBeforeTv.setPaintFlags(holder.binding.productPriceBeforeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (productModel.getProductBarcodes().get(0).getSpecialPrice() != null) {
                holder.binding.productPriceBeforeTv.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice())), UtilityApp.getLocalData().getFractional()) + " " + currency);
                holder.binding.productPriceTv.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getSpecialPrice())), UtilityApp.getLocalData().getFractional()) + " " + currency);
                discount = (Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice())) - Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getSpecialPrice()))) / (Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice()))) * 100;
                DecimalFormat df = new DecimalFormat("#");
                String newDiscount_str = df.format(discount);
                holder.binding.discountTv.setText(NumberHandler.arabicToDecimal(newDiscount_str) + " % " + "OFF");
            }


        } else {
            if (productModel.getProductBarcodes().get(0).getPrice() != null) {
                holder.binding.productPriceTv.setText(NumberHandler.formatDouble(Double.parseDouble(String.valueOf(productModel.getProductBarcodes().get(0).getPrice())), UtilityApp.getLocalData().getFractional()) + " " + currency + "");
                holder.binding.productPriceBeforeTv.setVisibility(View.INVISIBLE);
                holder.binding.discountTv.setVisibility(View.INVISIBLE);

            }
        }


        Glide.with(context).asBitmap().load(productModel.getImages().get(0)).placeholder(R.drawable.image_product).placeholder(R.drawable.image_product).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(holder.binding.productImg);


    }

    @Override
    public int getItemCount() {
        if (limit == 2) return Math.min(productModels.size(), limit);
        else return productModels.size();


    }

    private void addToFavorite(View v,int position, int productId, int userId, int storeId) {
        new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_add_favorite), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_add_favorite), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    Toast.makeText(context, "" + context.getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    productModels.get(position).setFavourite(true);
                    notifyItemChanged(position);
                    notifyDataSetChanged();

                } else {
                    Toast.makeText(context, "" + context.getString(R.string.fail_to_add_favorite), Toast.LENGTH_SHORT).show();
                }
            }

        }).addToFavoriteHandle(userId, storeId, productId);
    }


    private void removeFromFavorite(View view,int position, int productId, int userId, int storeId) {
        new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_remove_favorite), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_remove_favorite), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    productModels.get(position).setFavourite(false);
                    Toast.makeText(context, "" + context.getString(R.string.success_delete), Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position);
                    notifyDataSetChanged();


                } else {

                    Toast.makeText(context, "" + context.getString(R.string.fail_to_remove_favorite), Toast.LENGTH_SHORT).show();
                }
            }

        }).deleteFromFavoriteHandle(userId, storeId, productId);
    }


    private void loginFirst() {
        Toast.makeText(context, context.getString(R.string.textLoginFirst), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, RegisterLoginActivity.class);
        intent.putExtra(Constants.LOGIN, true);
        context.startActivity(intent);

    }

    public interface OnItemClick {
        void onItemClicked(int position, ProductModel productModel);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RowProductsItemBinding binding;

        Holder(RowProductsItemBinding view) {
            super(view.getRoot());
            binding = view;
            itemView.setOnClickListener(this);

            binding.favBut.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModels.get(position).getId();
                boolean isFavorite = productModels.get(position).getFavourite();
                if (isFavorite) {
                    removeFromFavorite(view1,position, productId, userId, storeId);

                } else {
                    addToFavorite(view1,position, productId, userId, storeId);

                }

            });

            binding.cartBut.setOnClickListener(view1 -> {

                if (!UtilityApp.isLogin()) {
                    loginFirst();
                } else {

                    ProductModel productModel = productModels.get(getAdapterPosition());
                    int count = productModel.getProductBarcodes().get(0).getCartQuantity();

                    int position = getAdapterPosition();
                    int userId = UtilityApp.getUserData().getId();
                    int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                    int productId = productModel.getId();
                    int product_barcode_id = productModel.getProductBarcodes().get(0).getId();

                    addToCart(view1,position, productId, product_barcode_id, count + 1, userId, storeId);


                }

            });

            binding.plusCartBtn.setOnClickListener(v -> {

                ProductModel productModel = productModels.get(getAdapterPosition());
                int count = productModel.getProductBarcodes().get(0).getCartQuantity();

                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getId();
                int product_barcode_id = productModel.getProductBarcodes().get(0).getId();

                updateCart(v,position, productId, product_barcode_id, count + 1, userId, storeId, 0, "quantity");

            });

            binding.minusCartBtn.setOnClickListener(v -> {

                ProductModel productModel = productModels.get(getAdapterPosition());
                int count = productModel.getProductBarcodes().get(0).getCartQuantity();
                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getId();
                int product_barcode_id = productModel.getProductBarcodes().get(0).getId();

                updateCart(v,position, productId, product_barcode_id, count - 1, userId, storeId, 0, "quantity");


            });

            binding.deleteCartBtn.setOnClickListener(v -> {

                ProductModel productModel = productModels.get(getAdapterPosition());
                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getId();
                int product_barcode_id = productModel.getProductBarcodes().get(0).getId();
                int cart_id = 0;

                deleteCart(v,position, productId, product_barcode_id, cart_id, userId, storeId);

            });


        }


        @Override
        public void onClick(View v) {
            if (onItemClick != null) {
                onItemClick.onItemClicked(getAdapterPosition(), productModels.get(getAdapterPosition()));
            }
        }


        private void addToCart(View v,int position, int productId, int product_barcode_id, int quantity, int userId, int storeId) {
            new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {

                if (IsSuccess) {

                    initSnackBar(context.getString(R.string.success_added_to_cart),v);
                    productModels.get(position).getProductBarcodes().get(0).setCartQuantity(quantity);
                    notifyItemChanged(position);

                } else {

                    initSnackBar(context.getString(R.string.fail_to_add_cart),v);
                }


            }).addCartHandle(productId, product_barcode_id, quantity, userId, storeId);
        }


        private void updateCart(View v,int position, int productId, int product_barcode_id, int quantity, int userId, int storeId, int cart_id, String update_quantity) {
            new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {
                if (IsSuccess) {

                    initSnackBar(context.getString(R.string.success_to_update_cart),v);
                    productModels.get(position).getProductBarcodes().get(0).setCartQuantity(quantity);
                    notifyItemChanged(position);

                } else {

                    initSnackBar(context.getString(R.string.fail_to_update_cart),v);

                }

            }).updateCartHandle(productId, product_barcode_id, quantity, userId, storeId, cart_id, update_quantity);
        }


        private void deleteCart(View v,int position, int productId, int product_barcode_id, int cart_id, int userId, int storeId) {
            new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {

                if (IsSuccess) {
                    productModels.get(position).getProductBarcodes().get(0).setCartQuantity(0);
                    notifyItemChanged(position);
                    initSnackBar(context.getString(R.string.success_delete_from_cart),v);


                } else {

                    initSnackBar(context.getString(R.string.fail_to_delete_cart),v);
                }


            }).deleteCartHandle(productId, product_barcode_id, cart_id, userId, storeId);
        }
    }


    private void initSnackBar(String message, View viewBar) {
        Snackbar snackbar = Snackbar.make(viewBar, message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView snackBarMessage = view.findViewById(R.id.snackbar_text);
        snackBarMessage.setTextColor(Color.WHITE);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.green));
        snackbar.setAction(context.getResources().getString(R.string.show_cart), v -> {

        });
        snackbar.show();
    }

}
