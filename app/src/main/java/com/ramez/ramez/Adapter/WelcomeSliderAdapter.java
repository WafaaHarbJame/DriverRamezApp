package com.ramez.ramez.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ramez.ramez.Models.WelcomeModel;
import com.ramez.ramez.R;

import java.util.ArrayList;

public class WelcomeSliderAdapter extends PagerAdapter {
    public ArrayList<WelcomeModel> welcomeSliderModels;
    private Context context;


    public WelcomeSliderAdapter(Context context, ArrayList<WelcomeModel> welcomeSliderModels) {
        this.context = context;
        this.welcomeSliderModels = welcomeSliderModels;
    }


    @Override
    public int getCount() {
        return welcomeSliderModels.size();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.fragment_welcome, container, false);


        try {

            TextView textInfoTitle =  view.findViewById(R.id.infoTitle);
            TextView textInfoTv1 =  view.findViewById(R.id.infoTv1);
            TextView textInfoTv2 =  view.findViewById(R.id.infoTv2);

            WelcomeModel welcomeSliderModel=welcomeSliderModels.get(position);

            textInfoTitle.setText(welcomeSliderModel.getInfoTxtTitle());
            textInfoTv1.setText(welcomeSliderModel.getInfoTxt1());
            textInfoTv2.setText(welcomeSliderModel.getInfoTxt2());
            container.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }





}