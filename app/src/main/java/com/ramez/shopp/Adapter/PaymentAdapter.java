package com.ramez.shopp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Models.PaymentModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.Models.ReviewModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.RowItemReviewBinding;
import com.ramez.shopp.databinding.RowPaymentTypeBinding;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.Holder> {

    private Context context;
    private List<PaymentModel> list;
    private OnPaymentClick onPaymentClick;



    public PaymentAdapter(Context context, List<PaymentModel> list, OnPaymentClick onPaymentClick ) {
        this.context = context;
        this.list = list;
        this.onPaymentClick=onPaymentClick;
        ;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowPaymentTypeBinding itemView = RowPaymentTypeBinding.inflate(LayoutInflater.from(context), parent, false);

        return new Holder(itemView);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        PaymentModel paymentModel = list.get(position);
        holder.binding.paymentTv.setText(paymentModel.getName());


    }

    public void setReviewList(List<PaymentModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class Holder extends RecyclerView.ViewHolder {

        RowPaymentTypeBinding binding;

        Holder(RowPaymentTypeBinding view) {
            super(view.getRoot());
            binding = view;
        }


    }
    public interface OnPaymentClick {
        void onPaymentClicked(int position, PaymentModel paymentModel);
    }
}
