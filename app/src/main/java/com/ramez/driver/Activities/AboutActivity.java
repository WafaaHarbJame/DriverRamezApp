package com.ramez.driver.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.SettingModel;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.ActivityAboutBinding;

public class AboutActivity extends ActivityBase {
    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        setTitle(R.string.text_title_about_us);

        if(UtilityApp.getSettingData()!=null){

            SettingModel settingModel=UtilityApp.getSettingData();
            binding.textTv.setText(settingModel.getAbout());
        }
        else {
            getSetting();
        }


        binding.appVersionTv.setText(getString(R.string.app_version).concat(UtilityApp.getAppVersion()+""));


    }



    public void getSetting() {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<SettingModel> result = (ResultAPIModel<SettingModel>) obj;

            if (IsSuccess) {
                SettingModel settingModel = new SettingModel();
                settingModel.setAbout(result.data.getAbout());
                settingModel.setConditions(result.data.getConditions());
                settingModel.setPrivacy(result.data.getPrivacy());
                UtilityApp.setSetting(settingModel);
                binding.textTv.setText(settingModel.getAbout());

            }

        }).getSetting();
    }
}