package com.ramez.shopp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowCartItemBinding;

import java.io.IOException;
import java.util.List;


public class CartAdapter extends RecyclerSwipeAdapter<CartAdapter.Holder> {

    private static final String TAG = "Log CartAdapter";
    public int count;
    public String currency = "BHD";
    private Context context;
    private List<CartModel> cartDMS;
    private OnCartItemClicked onCartItemClicked;


    public CartAdapter(Context context, List<CartModel> cartDMS, OnCartItemClicked onCartItemClicked) {
        this.context = context;
        this.cartDMS = cartDMS;
        this.onCartItemClicked = onCartItemClicked;

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

        holder.binding.productCartQTY.setText(cartDM.getProductQuantity() + "");

        holder.binding.quantityTv.setText(cartDM.getProductQuantity() + "");

        holder.binding.tvName.setText(cartDM.getProductName());

        holder.binding.cardViewOuter.setOnClickListener(v -> {
            Log.d(TAG, "name p" + cartDM.getProductName());

            onCartItemClicked.onCartItemClicked(cartDM);
        });

        holder.binding.imageView1.setOnClickListener(v -> {
            Log.d(TAG, "name p" + cartDM.getProductName());

            onCartItemClicked.onCartItemClicked(cartDM);
        });


        if (cartDM.getProductPrice() > 0) {
            holder.binding.totalTv.setVisibility(View.VISIBLE);
            holder.binding.priceTv.setVisibility(View.VISIBLE);
            double subTotal = cartDM.getProductPrice() * cartDM.getProductQuantity();
            holder.binding.priceTv.setText(cartDM.getProductPrice().toString());
            Log.d("proImage", "cart proImage: " + cartDM.getImage());
            holder.binding.totalTv.setText(NumberHandler.formatDouble(subTotal, UtilityApp.getLocalData().getFractional()) + " " + currency);
            Glide.with(context).load(cartDM.getImage()).placeholder(R.drawable.holder_image).into(holder.binding.imageView1);


        } else {
            double subTotal = cartDM.getProductPrice() * cartDM.getProductQuantity();
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
                subTotal += cartDMS.get(i).getProductPrice() * cartDMS.get(i).getProductQuantity();
            }
        }

        return subTotal;
    }


    @Override
    public int getItemCount() {
        return cartDMS.size();
    }

    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private void addToCart(int position, int productId, int product_barcode_id, int quantity, int userId, int storeId) {
        new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_add_cart), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_add_cart), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    cartDMS.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    Toast.makeText(context, "" + context.getString(R.string.success_added_to_cart), Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(context, "" + context.getString(R.string.fail_to_add_cart), Toast.LENGTH_SHORT).show();
                }
            }

        }).addCartHandle(productId, product_barcode_id, quantity, userId, storeId);
    }

    private void updateCart(int position, int productId, int product_barcode_id, int quantity, int userId, int storeId, int cart_id, String update_quantity) {
        new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_add_cart), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_add_cart), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    cartDMS.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    Toast.makeText(context, "" + context.getString(R.string.success_added_to_cart), Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(context, "" + context.getString(R.string.fail_to_add_cart), Toast.LENGTH_SHORT).show();
                }
            }

        }).updateCartHandle(productId, product_barcode_id, quantity, userId, storeId, cart_id, update_quantity);
    }

    private void deleteCart(int position, int productId, int product_barcode_id, int cart_id, int userId, int storeId) {
        new DataFeacher((Activity) context, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_delete_cart), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_delete_cart), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    cartDMS.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    Toast.makeText(context, "" + context.getString(R.string.success_add), Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(context, "" + context.getString(R.string.fail_to_delete_cart), Toast.LENGTH_SHORT).show();
                }
            }

        }).deleteCartHandle(productId, product_barcode_id, cart_id, userId, storeId);
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

            binding.deleteCartBtn.setOnClickListener(view1 -> {
                int position = getAdapterPosition();

            });


            binding.plusCartBtn.setOnClickListener(v -> {
                CartModel productModel = cartDMS.get(getAdapterPosition());
                count = Integer.parseInt(binding.productCartQTY.getText().toString());
                count++;

                binding.productCartQTY.setText(String.valueOf(count));
                binding.productCartQTY.setText(String.valueOf(count));
                binding.deleteCartBtn.setVisibility(View.GONE);
                binding.minusCartBtn.setVisibility(View.VISIBLE);

                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getId();
                int product_barcode_id = productModel.getProductBarcodeId();

                updateCart(position, productId, product_barcode_id, count, userId, storeId, 0, "quantity");

            });


            binding.minusCartBtn.setOnClickListener(v -> {
                CartModel productModel = cartDMS.get(getAdapterPosition());
                count = Integer.parseInt(binding.productCartQTY.getText().toString());

                binding.productCartQTY.setText(String.valueOf(count));
                binding.productCartQTY.setText(String.valueOf(count));
                if (count == 1) {
                    binding.minusCartBtn.setVisibility(View.GONE);
                    binding.deleteCartBtn.setVisibility(View.VISIBLE);

                } else {
                    binding.minusCartBtn.setVisibility(View.VISIBLE);
                    binding.deleteCartBtn.setVisibility(View.GONE);
                    count--;
                    if (count == 1) {
                        binding.minusCartBtn.setVisibility(View.GONE);
                        binding.deleteCartBtn.setVisibility(View.VISIBLE);
                    }


                }
                binding.plusCartBtn.setVisibility(View.VISIBLE);
                binding.productCartQTY.setVisibility(View.VISIBLE);
                binding.productCartQTY.setVisibility(View.GONE);
                binding.productCartQTY.setText(String.valueOf(count));
                binding.productCartQTY.setText(String.valueOf(count));

                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getId();
                int product_barcode_id = productModel.getProductBarcodeId();

                updateCart(position, productId, product_barcode_id, count, userId, storeId, 0, "quantity");


            });


            binding.deleteCartBtn.setOnClickListener(v -> {
                CartModel productModel = cartDMS.get(getAdapterPosition());
                binding.productCartQTY.setVisibility(View.GONE);
                binding.productCartQTY.setVisibility(View.GONE);
                binding.productCartQTY.setVisibility(View.GONE);
                binding.plusCartBtn.setVisibility(View.GONE);
                binding.productCartQTY.setText("1");
                binding.productCartQTY.setText("1");
                binding.deleteCartBtn.setVisibility(View.GONE);

                int position = getAdapterPosition();
                int userId = UtilityApp.getUserData().getId();
                int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
                int productId = productModel.getId();
                int product_barcode_id = productModel.getProductBarcodeId();
                int cart_id = 0;

                deleteCart(position, productId, product_barcode_id, cart_id, userId, storeId);
                notifyItemChanged(getAdapterPosition());

            });


            binding.plusCartBtn.setOnClickListener(v -> {
                CartModel cartDM = cartDMS.get(getAdapterPosition());
                count = Integer.parseInt(binding.productCartQTY.getText().toString());
                if (count == 1) {
                    binding.minusCartBtn.setVisibility(View.VISIBLE);
                    binding.deleteCartBtn.setVisibility(View.GONE);
                }
                count++;
                binding.productCartQTY.setText(String.valueOf(count));
                binding.quantityTv.setText(String.valueOf(count));

                if (cartDM.getProductPrice() > 0) {
                    double newSubTotal = cartDM.getProductPrice() * count;
                    binding.totalTv.setText(NumberHandler.formatDouble(newSubTotal, UtilityApp.getLocalData().getFractional()) + " " + currency);
                    binding.priceTv.setText(NumberHandler.formatDouble(newSubTotal, UtilityApp.getLocalData().getFractional()) + " " + currency);

                }

                count = Integer.parseInt(binding.productCartQTY.getText().toString());
                cartDM.setProductQuantity(count);


            });

            binding.minusCartBtn.setOnClickListener(v -> {
                CartModel cartDM = cartDMS.get(getAdapterPosition());
                count = Integer.parseInt(binding.productCartQTY.getText().toString());
                binding.productCartQTY.setText(String.valueOf(count));

                if (count == 1) {
                    binding.minusCartBtn.setVisibility(View.GONE);
                    binding.plusCartBtn.setVisibility(View.VISIBLE);
                    binding.deleteCartBtn.setVisibility(View.VISIBLE);

                } else {
                    binding.minusCartBtn.setVisibility(View.VISIBLE);
                    binding.plusCartBtn.setVisibility(View.VISIBLE);
                    binding.deleteCartBtn.setVisibility(View.GONE);
                    count--;

                }

                binding.productCartQTY.setText(String.valueOf(count));
                binding.quantityTv.setText(String.valueOf(count));

                if (cartDM.getProductPrice() > 0) {
                    double newSubTotal = cartDM.getProductPrice() * count;
                    binding.totalTv.setText(NumberHandler.formatDouble(newSubTotal, UtilityApp.getLocalData().getFractional()) + " " + currency);

                }
                count = Integer.parseInt(binding.productCartQTY.getText().toString());
                cartDM.setProductQuantity(count);

            });


        }


    }


}
