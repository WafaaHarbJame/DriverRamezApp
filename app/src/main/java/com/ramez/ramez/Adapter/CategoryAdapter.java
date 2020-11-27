package com.ramez.ramez.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ramez.ramez.Classes.CategoryModel;
import com.ramez.ramez.Classes.Constants;
import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.RowCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    private Context context;
    private List<CategoryModel> categoryDMS;
    private OnItemClick onItemClick;

    public CategoryAdapter(Context context, List<CategoryModel> categoryDMS, OnItemClick onItemClick) {
        this.context = context;
        this.categoryDMS = categoryDMS;
        this.onItemClick = onItemClick;
        ;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowCategoryBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_category, parent, false);

        return new Holder(itemView);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        CategoryModel categoryModel = categoryDMS.get(position);
        if (UtilityApp.getLanguage() == Constants.Arabic) {
            holder.binding.tvCatTitle.setText(categoryModel.getName_ar());

        } else {
            holder.binding.tvCatTitle.setText(categoryModel.getName_en());

        }

        Glide.with(context).asBitmap().load(categoryModel.getIcon()).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                holder.binding.loadingLY.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                holder.binding.loadingLY.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.binding.ivCatImage);

        holder.binding.container.setOnClickListener(v -> onItemClick.onItemClicked(position,categoryModel));
    }

    public void setCategoriesList(List<CategoryModel> list) {
        categoryDMS = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categoryDMS.size();
    }

    public interface OnItemClick {
        void onItemClicked(int position,CategoryModel categoryModel);
    }

    static class Holder extends RecyclerView.ViewHolder {

        RowCategoryBinding binding;

        Holder(RowCategoryBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }
}
