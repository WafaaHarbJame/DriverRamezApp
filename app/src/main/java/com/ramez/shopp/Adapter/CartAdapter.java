package com.ramez.shopp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.ramez.shopp.Activities.ProductDetailsActivity;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.CallBack.DataCallback;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.AddCommentDialog;
import com.ramez.shopp.Dialogs.CheckLoginDialog;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowCartItemBinding;

import java.util.List;


public class CartAdapter extends RecyclerSwipeAdapter<CartAdapter.Holder> {

    private static final String TAG = "Log CartAdapter";
    public int count;
    public String currency = "BHD";
    DataCallback dataCallback;
    AddCommentDialog addCommentDialog;
    private Context context;
    private List<CartModel> cartDMS;
    private OnCartItemClicked onCartItemClicked;


    public CartAdapter(Context context, List<CartModel> cartDMS, OnCartItemClicked onCartItemClicked, DataCallback dataCallback) {
        this.context = context;
        this.cartDMS = cartDMS;
        this.onCartItemClicked = onCartItemClicked;
        this.dataCallback = dataCallback;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowCartItemBinding itemView = RowCartItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        CartModel cartDM = cartDMS.get(position);
        currency = UtilityApp.getLocalData().getCurrencyCode();

        int quantity = cartDM.getQuantity();
        holder.binding.weightUnitTv.setText(cartDM.getWeightUnit());
        if (quantity > 0) {
            holder.binding.productCartQTY.setText(String.valueOf(quantity));

            if (quantity == 1) {
                holder.binding.deleteCartBtn.setVisibility(View.VISIBLE);
                holder.binding.minusCartBtn.setVisibility(View.GONE);
            } else {
                holder.binding.minusCartBtn.setVisibility(View.VISIBLE);
                holder.binding.deleteCartBtn.setVisibility(View.GONE);
            }

        }


        holder.binding.productMarkTv.setText(cartDM.getRemark());

        holder.binding.tvName.setText(cartDM.getName());


        if (cartDM.getProductPrice() > 0) {
            double subTotal = 0;
            if (cartDM.getSpecialPrice() == 0) {
                holder.binding.priceTv.setText(cartDM.getProductPrice().toString());
                subTotal = cartDM.getProductPrice() * cartDM.getQuantity();


            } else {
                holder.binding.priceTv.setText(cartDM.getSpecialPrice().toString());
                subTotal = cartDM.getSpecialPrice() * cartDM.getQuantity();

            }

            holder.binding.totalTv.setVisibility(View.VISIBLE);
            holder.binding.priceTv.setVisibility(View.VISIBLE);
            holder.binding.totalTv.setText(NumberHandler.formatDouble(subTotal, UtilityApp.getLocalData().getFractional()) + " " + currency);
            Glide.with(context).load(cartDM.getImage()).placeholder(R.drawable.holder_image).into(holder.binding.imageView1);


        } else {
            double subTotal = cartDM.getProductPrice() * cartDM.getQuantity();
            holder.binding.totalTv.setText(NumberHandler.formatDouble(subTotal, UtilityApp.getLocalData().getFractional()) + " " + currency);
            Glide.with(context).load(cartDM.getImage()).placeholder(R.drawable.holder_image).into(holder.binding.imageView1);

        }

        calculateSubTotalPrice();

        if (Integer.parseInt(holder.binding.productCartQTY.getText().toString()) == 1) {

            holder.binding.deleteCartBtn.setVisibility(View.VISIBLE);
            holder.binding.minusCartBtn.setVisibility(View.GONE);
        } else {
            holder.binding.minusCartBtn.setVisibility(View.VISIBLE);
            holder.binding.deleteCartBtn.setVisibility(View.GONE);

        }


        holder.binding.swipe.setShowMode(SwipeLayout.ShowMode.LayDown);

        holder.binding.swipe.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


    }

    public double calculateSubTotalPrice() {
        double subTotal = 0;
        for (int i = 0; i < cartDMS.size(); i++) {
            if (cartDMS.get(i).getProductPrice() > 0) {
                subTotal += cartDMS.get(i).getProductPrice() * cartDMS.get(i).getQuantity();
            }
        }
        Log.i(TAG, "Log subTotal result" + subTotal);


        return subTotal;
    }


    @Override
    public int getItemCount() {
        return cartDMS.size();
    }


    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    private void initSnackBar(String message, View viewBar) {
        Snackbar snackbar = Snackbar.make(viewBar, message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView snackBarMessage = view.findViewById(R.id.snackbar_text);
        snackBarMessage.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void updateMark(View v, int position, int cart_id, String remark) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            if (IsSuccess) {

                addCommentDialog.dismiss();
                notifyDataSetChanged();
                notifyItemChanged(position);
                initSnackBar(context.getString(R.string.success_to_update_cart), v);
                cartDMS.get(position).setRemark(remark);


            } else {
                addCommentDialog.dismiss();

                initSnackBar(context.getString(R.string.fail_to_update_cart), v);
            }


        }).updateRemarkCartHandle(cart_id, remark);
    }

    public interface OnCartItemClicked {
        void onCartItemClicked(CartModel cartDM);
    }

    class Holder extends RecyclerView.ViewHolder {

        RowCartItemBinding binding;

        @SuppressLint("SetTextI18n")
        Holder(RowCartItemBinding view) {
            super(view.getRoot());
            binding = view;

            binding.cardView.setOnClickListener(v -> {

                CartModel cartDM = cartDMS.get(getAdapterPosition());
                onCartItemClicked.onCartItemClicked(cartDM);
            });


            binding.editBut.setOnClickListener(view1 -> {

                int position = getAdapterPosition();
                CartModel cartModel = cartDMS.get(position);

                AddCommentDialog.Click okBut = new AddCommentDialog.Click() {
                    @Override
                    public void click() {
                        String remark = ((EditText) addCommentDialog.findViewById(R.id.remarkET)).getText().toString();

                        updateMark(view1, position, cartModel.getId(), remark);


                    }
                };

                AddCommentDialog.Click cancelBut = new AddCommentDialog.Click() {
                    @Override
                    public void click() {
                        addCommentDialog.dismiss();


                    }
                };

                addCommentDialog = new AddCommentDialog(context,cartModel.getRemark() , R.string.add_comment, R.string.add_comment, okBut, cancelBut);

                addCommentDialog.show();


            });
            binding.deleteBut.setOnClickListener(view1 -> {

                CartModel productModel = cartDMS.get(getAdapterPosition());
                int count = productModel.getQuantity();
                int product_barcode_id = productModel.getProductBarcodeId();
                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getProductId();
                int cart_id = 0;

                deleteCart(view1, position, productId, product_barcode_id, cart_id, userId, storeId);


            });

            binding.menuBut.setOnClickListener(view1 -> {

                CartModel cartModel = cartDMS.get(getAdapterPosition());
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                ProductModel productModel = new ProductModel();
                productModel.setId(cartModel.getProductId());
                productModel.setHName(cartModel.getHProductName());
                productModel.setName(cartModel.getHProductName());
                intent.putExtra(Constants.DB_productModel, productModel);
                context.startActivity(intent);

            });

            binding.plusCartBtn.setOnClickListener(v -> {

                CartModel productModel = cartDMS.get(getAdapterPosition());
                int count = productModel.getQuantity();
                int stock = productModel.getProductQuantity();
                int product_barcode_id = productModel.getProductBarcodeId();

                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getProductId();
                if (count + 1 < stock) {
                    updateCart(v, position, productId, product_barcode_id, count + 1, userId, storeId, 0, "quantity");

                } else {
                    initSnackBar(context.getString(R.string.stock_empty), v);
                }


            });

            binding.minusCartBtn.setOnClickListener(v -> {

                CartModel productModel = cartDMS.get(getAdapterPosition());
                int count = productModel.getQuantity();
                int product_barcode_id = productModel.getProductBarcodeId();

                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getProductId();

                updateCart(v, position, productId, product_barcode_id, count - 1, userId, storeId, 0, "quantity");


            });

            binding.deleteCartBtn.setOnClickListener(v -> {

                CartModel productModel = cartDMS.get(getAdapterPosition());
                int count = productModel.getQuantity();
                int product_barcode_id = productModel.getProductBarcodeId();
                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getProductId();
                int cart_id = 0;

                deleteCart(v, position, productId, product_barcode_id, cart_id, userId, storeId);

            });

        }


        private void updateCart(View v, int position, int productId, int product_barcode_id, int quantity, int userId, int storeId, int cart_id, String update_quantity) {
            new DataFeacher(false, (obj, func, IsSuccess) -> {
                if (IsSuccess) {

                    calculateSubTotalPrice();

                    if (dataCallback != null) {
                        if (calculateSubTotalPrice() > 0)
                            dataCallback.dataResult(calculateSubTotalPrice(), "success", true);
                    }
                    initSnackBar(context.getString(R.string.success_to_update_cart), v);
                    cartDMS.get(position).setQuantity(quantity);
                    notifyItemChanged(position);


                } else {

                    initSnackBar(context.getString(R.string.fail_to_update_cart), v);

                }

            }).updateCartHandle(productId, product_barcode_id, quantity, userId, storeId, cart_id, update_quantity);
        }

        private void deleteCart(View v, int position, int productId, int product_barcode_id, int cart_id, int userId, int storeId) {
            new DataFeacher(false, (obj, func, IsSuccess) -> {

                if (IsSuccess) {

                    calculateSubTotalPrice();

                    if (dataCallback != null) {
                        if (calculateSubTotalPrice() > 0)
                            dataCallback.dataResult(calculateSubTotalPrice(), "success", true);
                    }
                    cartDMS.remove(position);
                    notifyItemRemoved(position);


                    initSnackBar(context.getString(R.string.success_delete_from_cart), v);


                } else {

                    initSnackBar(context.getString(R.string.fail_to_delete_cart), v);
                }


            }).deleteCartHandle(productId, product_barcode_id, cart_id, userId, storeId);
        }


    }


}
