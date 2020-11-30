package com.ramez.shopp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Models.ReviewModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.RowItemReviewBinding;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

    private Context context;
    private List<ReviewModel> list;

    public ReviewAdapter(Context context, List<ReviewModel> list) {
        this.context = context;
        this.list = list;
        ;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowItemReviewBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_item_review, parent, false);

        return new Holder(itemView);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        ReviewModel reviewModel = list.get(position);
        holder.binding.reviewTv.setText(reviewModel.getReviewText());

        if (position == getItemCount() - 1) {
            holder.binding.divider.setVisibility(View.GONE);
        } else {
            holder.binding.divider.setVisibility(View.VISIBLE);
        }
    }

    public void setReviewList(List<ReviewModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class Holder extends RecyclerView.ViewHolder {

        RowItemReviewBinding binding;

        Holder(RowItemReviewBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }
}
