package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.shopp.Adapter.MainCategoryAdapter;
import com.ramez.shopp.Adapter.ProductCategoryAdapter;
import com.ramez.shopp.Adapter.SubCategoryAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CategoryModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.CheckLoginDialog;
import com.ramez.shopp.Models.AutoCompleteModel;
import com.ramez.shopp.Models.ChildCat;
import com.ramez.shopp.Models.FavouriteResultModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityCategoryProductsActivityBinding;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

public class CategoryProductsActivity extends ActivityBase implements ProductCategoryAdapter.OnItemClick, MainCategoryAdapter.OnMainCategoryItemClicked {

    ActivityCategoryProductsActivityBinding binding;

    ArrayList<ProductModel> productList;
    ArrayList<AutoCompleteModel> data = null;
    ArrayList<String> autoCompleteList;
    ArrayList<CategoryModel> mainCategoryDMS;
    ArrayList<ChildCat> subCategoryDMS = new ArrayList<>();

    GridLayoutManager gridLayoutManager;
    int numColumn = 2;
    int selectedSubCat = 0;
    int category_id = 0, country_id, city_id;
    private String user_id="0", filter="";

    private MemberModel user;
    private LocalModel localModel;
    private ProductCategoryAdapter adapter;

    private CheckLoginDialog checkLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryProductsActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        productList = new ArrayList<>();
        subCategoryDMS = new ArrayList<>();
        mainCategoryDMS = new ArrayList<>();


        gridLayoutManager = new GridLayoutManager(getActiviy(), numColumn);
        binding.recycler.setLayoutManager(gridLayoutManager);


        binding.listShopCategories.setLayoutManager(new LinearLayoutManager(getActiviy(), LinearLayoutManager.HORIZONTAL, false));

        binding.listSubCategory.setLayoutManager(new LinearLayoutManager(getActiviy(), LinearLayoutManager.HORIZONTAL, false));

        binding.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });


        data = new ArrayList<>();
        autoCompleteList = new ArrayList<>();

        localModel = UtilityApp.getLocalData();
        country_id = localModel.getCountryId();

        if(UtilityApp.isLogin()){

            user = UtilityApp.getUserData();
            user_id = String.valueOf(user.getId());

        }
        city_id = Integer.parseInt(localModel.getCityId());

        getIntentExtra();


        binding.searchBut.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActiviy(), SearchActivity.class);
            startActivity(intent);

        });


        binding.view1But.setOnClickListener(view1 -> {
            numColumn = 1;
            gridLayoutManager.setSpanCount(numColumn);
            adapter.notifyDataSetChanged();


        });


        binding.view2But.setOnClickListener(view1 -> {
            numColumn = 2;
            gridLayoutManager.setSpanCount(numColumn);
            adapter.notifyDataSetChanged();

        });


        binding.priceBut.setOnClickListener(view1 -> {
            Collections.sort(productList, Collections.reverseOrder());

        });


        binding.categoriesCountTv.setText(String.valueOf(productList.size()));

        binding.offerCountTv.setText(String.valueOf(productList.size()));

    }

    public void initAdapter() {

        adapter = new ProductCategoryAdapter(getActiviy(), productList, category_id, selectedSubCat, country_id, city_id, user_id, 0, binding.recycler,"", this);
        binding.recycler.setAdapter(adapter);

        binding.categoriesCountTv.setText(String.valueOf(productList.size()));
        binding.offerCountTv.setText(String.valueOf(productList.size()));
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {
        Intent intent = new Intent(getActiviy(), ProductDetailsActivity.class);
        intent.putExtra(Constants.DB_productModel, productModel);
        startActivity(intent);


    }


    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            mainCategoryDMS = (ArrayList<CategoryModel>) bundle.getSerializable(Constants.CAT_LIST);
            category_id = bundle.getInt(Constants.SELECTED_POSITION, 0);
            CategoryModel categoryModel = (CategoryModel) bundle.getSerializable(Constants.CAT_MODEL);
            int position = bundle.getInt(Constants.position, 0);

            ChildCat childCat=new ChildCat();
            childCat.setId(0);
            childCat.setHName(getString(R.string.all));
            childCat.setName(getString(R.string.all));

            subCategoryDMS = mainCategoryDMS.get(position).getChildCat();

            subCategoryDMS.add(0, childCat);

            initMainCategoryAdapter();

            initSubCategoryAdapter();
            category_id = categoryModel.getId();

            getProductList(category_id, country_id, city_id, user_id, filter, 0, 10);

        }
    }


    private void initSubCategoryAdapter() {

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(getActiviy(), subCategoryDMS, object -> {

            category_id = object.getId();
            getProductList(category_id, country_id, city_id, user_id, "", 0, 10);


        }, selectedSubCat);

        binding.listSubCategory.setAdapter(subCategoryAdapter);

    }

    private void initMainCategoryAdapter() {
        MainCategoryAdapter mainCategoryShopAdapter = new MainCategoryAdapter(getActiviy(), mainCategoryDMS, this, category_id);
        binding.listShopCategories.setAdapter(mainCategoryShopAdapter);
    }


    public void getProductList(int category_id, int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {
        productList.clear();
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            FavouriteResultModel result = (FavouriteResultModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result.getMessage() != null) {
                    message = result.getMessage();
                }
                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.GONE);
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(message);

            } else if (func.equals(Constants.FAIL)) {

                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.GONE);
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(message);


            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        productList = result.getData();
                        Log.i(TAG, "Log productList" + productList.size());
                        initAdapter();

                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

                    }


                } else {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);


                }
            }

        }).getCatProductList(category_id, country_id, city_id, user_id, filter, page_number, page_size);
    }

    @Override
    public void OnMainCategoryItemClicked(CategoryModel mainCategoryDM, int position) {

        category_id = mainCategoryDM.getId();
        subCategoryDMS.clear();

        subCategoryDMS = mainCategoryDMS.get(position).getChildCat();
        selectedSubCat = mainCategoryDMS.get(position).getChildCat().get(0).getId();

        getProductList(category_id, country_id, city_id, user_id, "", 0, 10);
        initSubCategoryAdapter();


    }
}
