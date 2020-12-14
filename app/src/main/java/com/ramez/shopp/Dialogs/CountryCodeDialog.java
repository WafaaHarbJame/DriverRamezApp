package com.ramez.shopp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Adapter.CountriesAdapter;
import com.ramez.shopp.Adapter.CountryCodeAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.ApiHandler.DataFetcherCallBack;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.Models.CountryModelResult;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CountryCodeDialog extends Dialog {

    List<CountryModel> countries;
    RecyclerView rv;
    Button okBtn,closeBut;
    EditText searchTxt;
    Activity activity;
    private int selectedPos;
    private CountryModel selectedCountry;
    private int countryCode;
    private LinearLayoutManager linearLayoutManager;
    private CountryCodeAdapter countriesAdapter;
    private DataFetcherCallBack dataFetcherCallBack;

    public CountryCodeDialog(Context context, int countryCode, final DataFetcherCallBack dataFetcherCallBack) {
        super(context);
        activity = (Activity) context;
        this.dataFetcherCallBack = dataFetcherCallBack;
        this.countryCode=countryCode;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_country_code);
        setCancelable(false);
        rv = findViewById(R.id.rv);
        okBtn = findViewById(R.id.okBtn);
        closeBut = findViewById(R.id.closeBtn);
        searchTxt= findViewById(R.id.searchTxt);

        linearLayoutManager=new LinearLayoutManager(activity);
        rv.setLayoutManager(linearLayoutManager);

        rv.hasFixedSize();
        getCountryList();

        try {
            if (activity != null && !activity.isFinishing()) show();
        } catch (Exception e) {
            dismiss();
        }


        okBtn.setOnClickListener(view -> {
            if (dataFetcherCallBack != null)
                dataFetcherCallBack.Result(selectedCountry, "InfoDialog", true);
            dismiss();
        });


        closeBut.setOnClickListener(view -> {
            dismiss();

        });
        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchStr = NumberHandler.arabicToDecimal(s.toString());
                List<CountryModel> countriesList=new ArrayList<>();
                for (int i = 0; i <countries.size() ; i++) {
                    CountryModel countryModel=countries.get(i);

                    if (countryModel.getName().toLowerCase()
                            .contains(searchStr) || countryModel.getPhonecode().toString()
                            .contains(searchStr))
                        countriesList.add(countryModel);
                    initAdapter(countriesList);


                }




            }
        });



    }

    private void getCountryList() {

        GlobalData.progressDialog(activity, R.string.upload_date, R.string.please_wait_upload);
        new DataFeacher(activity, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            CountryModelResult result = (CountryModelResult) obj;
            if (func.equals(Constants.ERROR)) {
                String message = activity.getString(R.string.fail_to_get_data);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(activity, R.string.fail_to_get_data, message);
            } else {
                if (IsSuccess) {
                    countries = result.getData();
                    initAdapter(countries);

                } else {
                    Toast.makeText(activity, "" + activity.getString(R.string.fail_to_get_data), Toast.LENGTH_SHORT).show();

                }
            }


        }).CountryHandle();

    }

    public void initAdapter(List<CountryModel> countries) {
        countriesAdapter = new CountryCodeAdapter(activity,countries,selectedCountry,dataFetcherCallBack);
        rv.setAdapter(countriesAdapter);
        rv.scrollToPosition(selectedPos);

    }



}
