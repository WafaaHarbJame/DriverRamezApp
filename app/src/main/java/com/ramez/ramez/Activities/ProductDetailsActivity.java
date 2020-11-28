package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.Adapter.ProductAdapter;
import com.ramez.ramez.Adapter.ProductSliderAdapter;
import com.ramez.ramez.Adapter.ReviewAdapter;
import com.ramez.ramez.Adapter.SuggestedProductAdapter;
import com.ramez.ramez.Adapter.WelcomeSliderAdapter;
import com.ramez.ramez.Classes.Constants;
import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.Models.ProductModel;
import com.ramez.ramez.Models.ReviewModel;
import com.ramez.ramez.Models.SliderModel;
import com.ramez.ramez.Models.WelcomeModel;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityProductDeatilsBinding;

import java.util.ArrayList;

public class ProductDetailsActivity extends ActivityBase implements SuggestedProductAdapter.OnItemClick {
    ActivityProductDeatilsBinding binding;

    ArrayList<SliderModel> sliderList;
    ArrayList<ProductModel> productList;
    ArrayList<ReviewModel> reviewList;

    private ProductSliderAdapter productSliderAdapter;
    private SuggestedProductAdapter productOfferAdapter;
    private ReviewAdapter reviewAdapter;
    private  LinearLayoutManager productLayoutManager;
    private  LinearLayoutManager reviewManger;

    String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductDeatilsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getIntentExtra();

        sliderList=new ArrayList<>();
        productList=new ArrayList<>();
        reviewList=new ArrayList<>();

        sliderList.add(new SliderModel(1,"https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        sliderList.add(new SliderModel(1,"https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        sliderList.add(new SliderModel(1,"https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        sliderList.add(new SliderModel(1,"https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        sliderList.add(new SliderModel(1,"https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));


        productList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),0));

        productList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),0));

        reviewList.add(new ReviewModel(1,"very good"));
        reviewList.add(new ReviewModel(1,"good"));
        reviewList.add(new ReviewModel(1,"very good"));


        productSliderAdapter=new ProductSliderAdapter(this,sliderList);
        binding.viewPager.setAdapter(productSliderAdapter);

        productLayoutManager =new LinearLayoutManager(getActiviy(), RecyclerView.HORIZONTAL,false);
        binding.offerRecycler.setLayoutManager(productLayoutManager);
        binding.offerRecycler.setHasFixedSize(true);

        reviewManger=new LinearLayoutManager(getActiviy(),RecyclerView.VERTICAL,false);
        binding.reviewRecycler.setLayoutManager(reviewManger);
        binding.reviewRecycler.setHasFixedSize(true);



        initAdapter();
        binding.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
                }
        );
    }

    private void getIntentExtra() {
        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
            ProductModel productModel= (ProductModel) bundle.getSerializable(Constants.DB_productModel);
            if(UtilityApp.getLanguage().equals(Constants.Arabic)){
                productName=productModel.getPro_name_ar().trim();

            }
            else {
                productName=productModel.getPro_name_en().trim();

            }
            binding.mainTitleTxt.setText(productName);



        }
    }

    public void initAdapter(){

        productOfferAdapter = new SuggestedProductAdapter(getActiviy(), this, productList,productList.size());
        binding.offerRecycler.setAdapter(productOfferAdapter);


        reviewAdapter = new ReviewAdapter(getActiviy(), reviewList);
        binding.reviewRecycler.setAdapter(reviewAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }
}