package com.ramez.driver.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.ramez.driver.Activities.GetMyInvoiceInfoActivity;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Dialogs.ShowImageDialog;
import com.ramez.driver.HomeActivity;
import com.ramez.driver.Models.DeliveryStatusResponse;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.OrderItemDetail;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterProductsOrder extends RecyclerView.Adapter<AdapterProductsOrder.MyViewHolder> {
    private static final String TAG = "Adapter_My_Order";

    private Context context;
    private List<OrderItemDetail> myOrders;
    private MemberModel userInfo;
    private String LangCode;
    private List<OrderExtraAdapter> orderExtraAdapters;
    private RequestOptions requestOptions;


    public AdapterProductsOrder(Context context, List<OrderItemDetail> myOrders) {
        this.context = context;
        this.myOrders = myOrders;
        orderExtraAdapters = new ArrayList<>();
        requestOptions = new RequestOptions().fitCenter().error(R.drawable.holder_image);

    }

    @Override
    public AdapterProductsOrder.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products_my_order_extra, parent, false);

        return new AdapterProductsOrder.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterProductsOrder.MyViewHolder holder, final int position) {
        OrderItemDetail album = myOrders.get(position);

        String EGP = context.getResources().getString(R.string.EGP);
        final String done = context.getResources().getString(R.string.done_c);
        final String cancelled = context.getResources().getString(R.string.cancel_c);
        final String alart_do = context.getResources().getString(R.string.alart_do);

        holder.qty_text.setText(" : " + myOrders.get(position).getQuantity());
        holder.item_text.setText(myOrders.get(position).gethProductName());
        holder.en_item_text.setText(myOrders.get(position).getProductName());
        holder.item_barcode_text.setText(myOrders.get(position).getBarcode());

        holder.price_text.setText(myOrders.get(position).getProductPrice() + " " + EGP);


        orderExtraAdapters.add(position, new OrderExtraAdapter(context, myOrders.get(position).getExtraDMS()));
        orderExtraAdapters.get(position).notifyDataSetChanged();
        holder.listExtras.setAdapter(orderExtraAdapters.get(position));


        holder.linearListExtra.setVisibility(myOrders.get(position).getExtraDMS().size() == 0 ? View.GONE : View.VISIBLE);

        if (myOrders.get(position).getIs_picked() == 1) {
            holder.picked.setVisibility(View.VISIBLE);
            holder.pickedBtn.setVisibility(View.GONE);
        }


        if (myOrders.size() > 1) {
            holder.close.setVisibility(View.VISIBLE);
        }

        try {

            Picasso.get().load(Constants.imageURL_ramez + album.getImage()).placeholder(R.drawable.holder_image).into(holder.productImage);


        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.close.setOnClickListener(v -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(alart_do);
            builder.setCancelable(false);
            builder.setPositiveButton(done, (dialog, which) -> {
                Volley_DeliveryInvoiceUpdate(String.valueOf(myOrders.get(position).getProductId()));
                notifyDataSetChanged();
            });
            builder.setNegativeButton(cancelled, (dialog, which) -> {

            });
            builder.show();


        });


        holder.pickedBtn.setOnClickListener(v -> {
            int status;
            if (myOrders.get(position).getIs_picked() == 0) {
                status = 0;
            } else {
                status = 1;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(alart_do);
            builder.setCancelable(false);
            builder.setPositiveButton(done, (dialog, which) -> {
                PickProducts(myOrders.get(position).getProductId(), status, myOrders.get(position).getOrderId());
                notifyDataSetChanged();
            });
            builder.setNegativeButton(cancelled, (dialog, which) -> {

            });
            builder.show();


        });

    }


    @Override
    public int getItemCount() {
        return myOrders.size();
    }


    void Volley_DeliveryInvoiceUpdate(final String product_id) {


        GlobalData.progressDialog(context, R.string.change_product, R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            ResultAPIModel<DeliveryStatusResponse> result = (ResultAPIModel<DeliveryStatusResponse>) obj;
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_update), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_update), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(context, R.string.no_internet_connection);
            } else {
                if (IsSuccess) {


                    String ok = context.getResources().getString(R.string.Ok);

                    String message = result.message;

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(ok, (dialog, which) -> {
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    });


                    builder.show();

                }
            }

        }).DeliveryInvoiceUpdate(String.valueOf(product_id));


    }

    void PickProducts(final int product_id, final int status, int Inv_id) {


        GlobalData.progressDialog(context, R.string.change_order, R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            ResultAPIModel<String> result = (ResultAPIModel) obj;
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_update_order), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(context, "" + context.getString(R.string.fail_to_update_order), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(context, R.string.no_internet_connection);
            } else {
                if (IsSuccess) {


                    String ok = context.getResources().getString(R.string.Ok);
                    String message = result.message;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(ok, (dialog, which) -> {
                        Intent intent = new Intent(context, GetMyInvoiceInfoActivity.class);
                        intent.putExtra(Constants.id, String.valueOf(Inv_id));
                        context.startActivity(intent);
                        ((Activity) context).finish();


                    });
                    builder.show();

                }
            }

        }).GetInvoiceProductPickStatus(String.valueOf(product_id), status);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView qty_text, item_text, en_item_text, price_text, item_barcode_text;
        RecyclerView listExtras;
        LinearLayout linearListExtra;
        ImageView close, productImage, pickedBtn, picked;

        public MyViewHolder(View view) {
            super(view);
            userInfo = UtilityApp.getUserData();
            qty_text = view.findViewById(R.id.qty_text);
            item_text = view.findViewById(R.id.item_text);
            price_text = view.findViewById(R.id.price_text);
            en_item_text = view.findViewById(R.id.en_item_text);
            item_barcode_text = view.findViewById(R.id.item_barcode_text);
            listExtras = view.findViewById(R.id.listExtras);
            linearListExtra = view.findViewById(R.id.linearListExtra);
            pickedBtn = view.findViewById(R.id.pickedBtn);
            picked = view.findViewById(R.id.picked);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context);


            listExtras.setLayoutManager(mLayoutManager);
            listExtras.setItemAnimator(new DefaultItemAnimator());

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                OrderItemDetail album = myOrders.get(position);
                Log.i("tag","Log "+Constants.imageURL_ramez+album.getImage());

                ShowImageDialog showImageDialog = new ShowImageDialog((Activity) context, Constants.imageURL_ramez+album.getImage());
                showImageDialog.show();

            });


            close = view.findViewById(R.id.close);
            productImage = view.findViewById(R.id.ivProductImage);
            LangCode = Locale.getDefault().getLanguage();


        }
    }

}
