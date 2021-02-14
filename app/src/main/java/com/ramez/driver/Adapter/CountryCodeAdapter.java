package com.ramez.driver.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ramez.driver.ApiHandler.DataFetcherCallBack;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Models.CountryModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.RowCountryCodeBinding;

import java.util.List;

public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.ViewHolder> {
    private Activity activity;
    private List<CountryModel> list;
    private int  selectedCountry;
    private DataFetcherCallBack dataFetcherCallBack;

    public CountryCodeAdapter(Activity activity, List<CountryModel> list, int  selectedCountry, DataFetcherCallBack dataFetcherCallBack) {
        this.activity = activity;
        this.list = list;
        this.selectedCountry = selectedCountry;
        this.dataFetcherCallBack = dataFetcherCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowCountryCodeBinding itemView = RowCountryCodeBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryModel countryModel = list.get(position);
        holder.binding.nameTxt.setText(countryModel.getUserName());
        String code = "+" + countryModel.getPhonecode();
        holder.binding.codeTxt.setText(code);

        Glide.with(activity).asBitmap().load(countryModel.getFlag()).placeholder(R.drawable.ic_flag_uae).into(holder.binding.flagImg);

        if (selectedCountry==countryModel.getUserId()) {
            holder.binding.selectTxt.setText(activity.getString(R.string.fa_circle));
            holder.binding.selectTxt.setTextColor(ContextCompat.getColor(activity,R.color.colorPrimaryDark));
        } else {
            holder.binding.selectTxt.setText(activity.getString(R.string.fa_circle_o));
            holder.binding.selectTxt.setTextColor(ContextCompat.getColor(activity, R.color.header3));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowCountryCodeBinding binding;

        public ViewHolder(RowCountryCodeBinding view) {
            super(view.getRoot());
            binding = view;

            itemView.setOnClickListener(v -> {

                CountryModel countryCodeModel = list.get(getAdapterPosition());
                selectedCountry=countryCodeModel.getUserId();
                notifyDataSetChanged();
                if (dataFetcherCallBack != null) {
                    dataFetcherCallBack.Result(countryCodeModel, Constants.success, true);
                }


            });

        }


    }

}