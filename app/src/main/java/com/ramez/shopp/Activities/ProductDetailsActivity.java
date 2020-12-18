package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.Adapter.ProductSliderAdapter;
import com.ramez.shopp.Adapter.ReviewAdapter;
import com.ramez.shopp.Adapter.SuggestedProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductDetailsModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.Models.ReviewModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityProductDeatilsBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProductDetailsActivity extends ActivityBase implements SuggestedProductAdapter.OnItemClick {
    ActivityProductDeatilsBinding binding;
    String user_id;
    ArrayList<String> sliderList;
    ArrayList<ProductModel> productList;
    ArrayList<ReviewModel> reviewList;
    String productName;
    private int category_id = 0, country_id, city_id, product_id;
    private ProductSliderAdapter productSliderAdapter;
    private SuggestedProductAdapter productOfferAdapter;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager productLayoutManager;
    private LinearLayoutManager reviewManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDeatilsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getIntentExtra();

        MemberModel memberModel = UtilityApp.getUserData();
        country_id = UtilityApp.getLocalData().getCountryId();
        city_id = Integer.parseInt(UtilityApp.getLocalData().getCityId());
        user_id = String.valueOf(memberModel.getId());

        sliderList = new ArrayList<String>();
        productList = new ArrayList<>();
        reviewList = new ArrayList<>();

        reviewList.add(new ReviewModel(1, "very good"));
        reviewList.add(new ReviewModel(1, "good"));
        reviewList.add(new ReviewModel(1, "very good"));


        productLayoutManager = new LinearLayoutManager(getActiviy(), RecyclerView.HORIZONTAL, false);
        binding.offerRecycler.setLayoutManager(productLayoutManager);
        binding.offerRecycler.setHasFixedSize(true);

        reviewManger = new LinearLayoutManager(getActiviy(), RecyclerView.VERTICAL, false);
        binding.reviewRecycler.setLayoutManager(reviewManger);
        binding.reviewRecycler.setHasFixedSize(true);

        getSingleProduct(country_id, city_id, product_id, user_id);

        binding.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }

    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ProductModel productModel = (ProductModel) bundle.getSerializable(Constants.DB_productModel);
            if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
                productName = productModel.getHName().trim();
                product_id = productModel.getId();
                binding.productNameTv.setText(productName);

            } else {
                productName = productModel.getName().trim();

            }
            binding.mainTitleTxt.setText(productName);


        }
    }

    public void initAdapter() {

        productOfferAdapter = new SuggestedProductAdapter(getActiviy(), this, productList, productList.size());
        binding.offerRecycler.setAdapter(productOfferAdapter);

        productSliderAdapter = new ProductSliderAdapter(this, sliderList);
        binding.viewPager.setAdapter(productSliderAdapter);

        reviewAdapter = new ReviewAdapter(getActiviy(), reviewList);
        binding.reviewRecycler.setAdapter(reviewAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }


    @SuppressLint("SetTextI18n")
    public void getSingleProduct(int country_id, int city_id, int product_id, String user_id) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ProductDetailsModel result = (ProductDetailsModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result != null) {
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
                        Log.i(TAG, "Log productBestList" + productList.size());
                        binding.productDescTv.setText(Html.fromHtml(result.getData().get(0).getDescription().toString()));
                        binding.productPriceTv.setText(result.getData().get(0).getProductBarcodes().get(0).getPrice().toString());
                        sliderList = result.getData().get(0).getImages();
                        sliderList.add("https://s3.amazonaws.com/xadok-media/public/uploads/products/7rJeQuselTsUk0nTlhOfWGUQZORHxp0uKj6bsuSZ.jpeg");

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

        }).GetSingleProduct(country_id, city_id, product_id, user_id);
    }
}