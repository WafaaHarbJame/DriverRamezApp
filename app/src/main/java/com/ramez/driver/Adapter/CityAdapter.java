package com.ramez.driver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.CityModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.RowItemCitiesBinding;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private Context context;
    private OnCityClick onCityClick;
    private ArrayList<CityModel> list;
    private int selectedPosition = 0;

    public CityAdapter(Context context, ArrayList<CityModel> list, OnCityClick onCityClick, int selectedPosition) {
        this.context = context;
        this.onCityClick = onCityClick;
        this.list = list;
        this.selectedPosition=selectedPosition;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowItemCitiesBinding itemView = RowItemCitiesBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityModel languageModel = list.get(position);

        String lang= UtilityApp.getLanguage();
        if (lang.equals(Constants.Arabic)) {
            if(languageModel.getUserNameAr()==null||languageModel.getUserNameAr().isEmpty()){
                holder.binding.nameTxt.setText(languageModel.getUserName());

            }
            else {
                holder.binding.nameTxt.setText(languageModel.getUserNameAr());

            }
        }
        else {
            if(languageModel.getUserName()==null||languageModel.getUserName().isEmpty()){
                holder.binding.nameTxt.setText(languageModel.getUserNameAr());

            }
            else {
                holder.binding.nameTxt.setText(languageModel.getUserName());

            }
        }


        if (selectedPosition==languageModel.getUserId()) {
            holder.binding.selectTxt.setText(context.getString(R.string.fa_circle));
            holder.binding.selectTxt.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        } else {
            holder.binding.selectTxt.setText(context.getString(R.string.fa_circle_o));
            holder.binding.selectTxt.setTextColor(ContextCompat.getColor(context, R.color.header3));
        }



        if (position == getItemCount() - 1) {
            holder.binding.divider.setVisibility(View.GONE);
        } else {
            holder.binding.divider.setVisibility(View.VISIBLE);
        }





    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RowItemCitiesBinding binding;

        ViewHolder(RowItemCitiesBinding view) {
            super(view.getRoot());
            binding = view;


            itemView.setOnClickListener(v -> {
                onCityClick.onCityClicked(getAdapterPosition(), list.get(getAdapterPosition()));
                selectedPosition = list.get(getAdapterPosition()).getUserId();
                notifyDataSetChanged();
            });

        }



    }

    public interface OnCityClick {
        void onCityClicked(int position, CityModel cityModel);
    }


}
