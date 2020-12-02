package com.ramez.shopp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.ramez.shopp.Classes.CartModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.RowCartItemBinding;

import java.io.IOException;
import java.util.List;


public class CartAdapter extends RecyclerSwipeAdapter<CartAdapter.Holder> {

    private static final String TAG = "Log CartAdapter";
    public int count;
    public String currency;
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
        currency = "AED";
        holder.binding.productCartQTY.setText(cartDM.getProductQuantity() + "");
        holder.binding.tvName.setText(cartDM.getProductName());

        holder.binding.cardViewOuter.setOnClickListener(v -> {
            Log.d(TAG, "name p" + cartDM.getProductName());
            Log.d(TAG, "ModelID p" + cartDM.getModelID());

            onCartItemClicked.onCartItemClicked(cartDM);
        });

        holder.binding.imageView1.setOnClickListener(v -> {
            Log.d(TAG, "name p" + cartDM.getProductName());

            onCartItemClicked.onCartItemClicked(cartDM);
        });


        if (cartDM.getProductPrice().isEmpty()) {
            holder.binding.totalTv.setVisibility(View.GONE);
            holder.binding.tvPrice.setVisibility(View.GONE);
            Log.d("proImage", "cart proImage: " + cartDM.getProductImage());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(cartDM.getProductImage()));
                Glide.with(context).load(bitmap).placeholder(R.drawable.holder_image).into(holder.binding.imageView1);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            double subTotal = Double.parseDouble(cartDM.getProductPrice()) * cartDM.getProductQuantity();
            holder.binding.totalTv.setText(NumberHandler.formatDouble(subTotal) + " " + currency);
            Glide.with(context).load(cartDM.getProductImage()).placeholder(R.drawable.holder_image).into(holder.binding.imageView1);

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
            if (!cartDMS.get(i).getProductPrice().isEmpty()) {
                subTotal += Double.parseDouble(cartDMS.get(i).getProductPrice()) * cartDMS.get(i).getProductQuantity();
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

            binding.plusCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartModel cartDM = cartDMS.get(getAdapterPosition());
                    count = Integer.parseInt(binding.productCartQTY.getText().toString());
                    if (count == 1) {
                        binding.minusCartBtn.setVisibility(View.VISIBLE);
                        binding.deleteCartBtn.setVisibility(View.GONE);
                    }
                    count++;
                    binding.productCartQTY.setText(String.valueOf(count));

                    if(!cartDM.getProductPrice().isEmpty()){
                        double newSubTotal = Double.parseDouble(cartDM.getProductPrice()) * count;
                        binding.totalTv.setText(NumberHandler.formatDouble(newSubTotal) + " " + currency);
                        binding.tvPrice.setText(NumberHandler.formatDouble(newSubTotal) + " " + currency);

                    }

                    count = Integer.parseInt(binding.productCartQTY.getText().toString());
                    cartDM.setProductQuantity(count);


                }
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
                if(!cartDM.getProductPrice().isEmpty()){
                    double newSubTotal = Double.parseDouble(cartDM.getProductPrice()) * count;
                    binding.totalTv.setText(NumberHandler.formatDouble(newSubTotal) + " " + currency);

                }
                count = Integer.parseInt(binding.productCartQTY.getText().toString());
                cartDM.setProductQuantity(count);

            });


        }
    }
}
