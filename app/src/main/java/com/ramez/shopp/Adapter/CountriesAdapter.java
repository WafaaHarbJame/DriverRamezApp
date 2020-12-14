package com.ramez.shopp.Adapter;

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
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.RowItemCityBinding;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {
    private Context context;
    private  OnItemClick onItemClick;
    private ArrayList<CountryModel> countryModels;

    public CountriesAdapter(Context context,  OnItemClick onItemClick, ArrayList<CountryModel> countryModels) {
        this.context = context;
        this.onItemClick = onItemClick;
        this.countryModels = countryModels;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowItemCityBinding itemView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_item_city, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        CountryModel countryModel = countryModels.get(position);
            holder.binding.textCountry.setText(countryModel.getName());
            holder.binding.imgFlag.setImageResource(countryModel.getFlag());

        if (position == getItemCount() - 1) {
            holder.binding.divider.setVisibility(View.GONE);
        } else {
            holder.binding.divider.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return countryModels != null ? countryModels.size() : 0;
    }


    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RowItemCityBinding binding;

        CountryViewHolder(RowItemCityBinding view) {
            super(view.getRoot());
            binding = view;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (onItemClick != null) {
                onItemClick.onItemClicked(getAdapterPosition(),countryModels.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClick {
        void onItemClicked(int position,CountryModel countryModel);
    }
}
