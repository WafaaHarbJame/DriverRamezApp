package com.ramez.shopp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ramez.shopp.Adapter.ProductSliderAdapter;
import com.ramez.shopp.Adapter.ReviewAdapter;
import com.ramez.shopp.Adapter.SuggestedProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.MainModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductBarcode;
import com.ramez.shopp.Models.ProductDetailsModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.Models.ReviewModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityProductDeatilsBinding;

import java.util.ArrayList;

public class ProductDetailsActivity extends ActivityBase implements SuggestedProductAdapter.OnItemClick {
    ActivityProductDeatilsBinding binding;
    int user_id;
    ArrayList<String> sliderList;
    ArrayList<ProductModel> productList;
    ArrayList<ReviewModel> reviewList;
    String productName;
    ProductModel productModel;
    private int category_id = 0, country_id, city_id, product_id;
    private ProductSliderAdapter productSliderAdapter;
    private SuggestedProductAdapter productOfferAdapter;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager productLayoutManager;
    private LinearLayoutManager reviewManger;
    private int storeId;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDeatilsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MemberModel memberModel = UtilityApp.getUserData();
        country_id = UtilityApp.getLocalData().getCountryId();
        city_id = Integer.parseInt(UtilityApp.getLocalData().getCityId());
        user_id = Integer.parseInt(String.valueOf(memberModel.getId()));

        sliderList = new ArrayList<String>();
        productList = new ArrayList<>();
        reviewList = new ArrayList<>();
        storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());

//        reviewList.add(new ReviewModel(1, "very good"));
//        reviewList.add(new ReviewModel(1, "good"));
//        reviewList.add(new ReviewModel(1, "very good"));
//

        productLayoutManager = new LinearLayoutManager(getActiviy(), RecyclerView.HORIZONTAL, false);
        binding.offerRecycler.setLayoutManager(productLayoutManager);
        binding.offerRecycler.setHasFixedSize(true);


        reviewManger = new LinearLayoutManager(getActiviy(), RecyclerView.VERTICAL, false);
        binding.reviewRecycler.setLayoutManager(reviewManger);
        binding.reviewRecycler.setHasFixedSize(true);

        getIntentExtra();

        getSingleProduct(country_id, city_id, product_id, String.valueOf(user_id));

        getSuggestedProduct();

        binding.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        binding.favBut.setOnClickListener(v -> {
            Log.i("tag", "Log isFavorite" + isFavorite);

            if (isFavorite) {
                removeFromFavorite(v, product_id, user_id, storeId);

            } else {
                addToFavorite(v, product_id, user_id, storeId);

            }


        });


        binding.cartBut.setOnClickListener(view1 -> {

            int count = productModel.getProductBarcodes().get(0).getCartQuantity();
            int userId = UtilityApp.getUserData().getId();
            int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
            int productId = productModel.getId();
            int product_barcode_id = productModel.getProductBarcodes().get(0).getId();

            addToCart(view1, productId, product_barcode_id, count + 1, userId, storeId);


        });

        binding.plusCartBtn.setOnClickListener(v -> {

            int count = Integer.parseInt(binding.productCartQTY.getText().toString());
            int stock = productModel.getProductBarcodes().get(0).getStockQty();
            int userId = UtilityApp.getUserData().getId();
            int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
            int productId = productModel.getId();
            int product_barcode_id = productModel.getProductBarcodes().get(0).getId();
            int cartId=productModel.getProductBarcodes().get(0).getCartId();

            if (count + 1 < stock) {
                updateCart(v, productId, product_barcode_id, count + 1, userId, storeId, cartId, "quantity");

            } else {
                initSnackBar(getString(R.string.stock_empty), v);
            }


        });

        binding.minusCartBtn.setOnClickListener(v -> {

           // int count = productModel.getProductBarcodes().get(0).getCartQuantity();
            int count = Integer.parseInt(binding.productCartQTY.getText().toString());
            int userId = UtilityApp.getUserData().getId();
            int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
            int productId = productModel.getId();
            int product_barcode_id = productModel.getProductBarcodes().get(0).getId();
            int cart_id=productModel.getProductBarcodes().get(0).getCartId();

            updateCart(v, productId, product_barcode_id, count - 1, userId, storeId, cart_id, "quantity");


        });

        binding.deleteCartBtn.setOnClickListener(v -> {

            int userId = UtilityApp.getUserData().getId();
            int storeId = Integer.parseInt(UtilityApp.getLocalData().getCityId());
            int productId = productModel.getId();
            int product_barcode_id = productModel.getProductBarcodes().get(0).getId();
            int cart_id=productModel.getProductBarcodes().get(0).getCartId();

            deleteCart(v, productId, product_barcode_id, cart_id, userId, storeId);

        });


    }

    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            ProductModel productModel = (ProductModel) bundle.getSerializable(Constants.DB_productModel);
            if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
                product_id = productModel.getId();
                if (UtilityApp.getLanguage().equals(Constants.Arabic)) {
                    productName = productModel.getHName();

                } else {
                    productName = productModel.getName();

                }
                binding.productNameTv.setText(productName);

                binding.mainTitleTxt.setText(productName);


            }


        }
    }

    public void initAdapter() {

        productSliderAdapter = new ProductSliderAdapter(this, sliderList);
        binding.viewPager.setAdapter(productSliderAdapter);
    }

    public void initReviewAdapter() {

        reviewAdapter = new ReviewAdapter(getActiviy(), reviewList);
        binding.reviewRecycler.setAdapter(reviewAdapter);
    }


    @Override
    public void onItemClicked(int position, ProductModel productModel) {
        Intent intent = new Intent(getActiviy(), ProductDetailsActivity.class);
        intent.putExtra(Constants.DB_productModel, productModel);
        startActivity(intent);

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

                        productModel = result.getData().get(0);
                        binding.productDescTv.setText(Html.fromHtml(productModel.getDescription().toString()));
                        binding.productPriceTv.setText(productModel.getProductBarcodes().get(0).getPrice().toString());
                        sliderList = productModel.getImages();

                        isFavorite = productModel.getFavourite();

                        if (productModel.getFavourite() != null && isFavorite) {

                            binding.favBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.favorite_icon));
                        } else {
                            binding.favBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.empty_fav));

                        }

                        int quantity = productModel.getProductBarcodes().get(0).getCartQuantity();
                        if (quantity > 0) {
                            binding.productCartQTY.setText(String.valueOf(quantity));
                            binding.CartLy.setVisibility(View.VISIBLE);
                            binding.cartBut.setVisibility(View.GONE);

                            if (quantity == 1) {
                                binding.deleteCartBtn.setVisibility(View.VISIBLE);
                                binding.minusCartBtn.setVisibility(View.GONE);
                            } else {
                                binding.minusCartBtn.setVisibility(View.VISIBLE);
                                binding.deleteCartBtn.setVisibility(View.GONE);
                            }

                        } else {
                            binding.CartLy.setVisibility(View.GONE);
                            binding.cartBut.setVisibility(View.VISIBLE);
                        }


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

    private void addToFavorite(View v, int productId, int userId, int storeId) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(getActiviy(), "" + getString(R.string.fail_to_add_favorite), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(getActiviy(), "" + getString(R.string.fail_to_add_favorite), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    binding.favBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.favorite_icon));
                    Toast.makeText(getActiviy(), "" + getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    isFavorite = true;

                } else {
                    Toast.makeText(getActiviy(), "" + getString(R.string.fail_to_add_favorite), Toast.LENGTH_SHORT).show();
                }
            }

        }).addToFavoriteHandle(userId, storeId, productId);
    }


    private void removeFromFavorite(View view, int productId, int userId, int storeId) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(getActiviy(), "" + getString(R.string.fail_to_remove_favorite), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(getActiviy(), "" + getString(R.string.fail_to_remove_favorite), Toast.LENGTH_SHORT).show();

            } else {
                if (IsSuccess) {
                    binding.favBut.setImageDrawable(ContextCompat.getDrawable(getActiviy(), R.drawable.empty_fav));
                    isFavorite = false;
                    Toast.makeText(getActiviy(), "" + getString(R.string.success_delete), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActiviy(), "" + getString(R.string.fail_to_remove_favorite), Toast.LENGTH_SHORT).show();
                }
            }

        }).deleteFromFavoriteHandle(userId, storeId, productId);
    }


    public void getSuggestedProduct() {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            MainModel result = (MainModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result != null && result.getMessage() != null) {
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
                    if (result.getFeatured() != null && result.getFeatured().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        productList = result.getQuickProducts();
                        initProductsAdapter();

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

        }).GetMainPage(0, country_id, city_id, String.valueOf(user_id));
    }

    private void initProductsAdapter() {
        productOfferAdapter = new SuggestedProductAdapter(getActiviy(), productList, this, productList.size());
        binding.offerRecycler.setAdapter(productOfferAdapter);

    }

    private void addToCart(View v, int productId, int product_barcode_id, int quantity, int userId, int storeId) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            if (IsSuccess) {

                binding.productCartQTY.setText(String.valueOf(quantity));
                initSnackBar(getString(R.string.success_added_to_cart), v);

            } else {

                initSnackBar(getString(R.string.fail_to_add_cart), v);
            }


        }).addCartHandle(productId, product_barcode_id, quantity, userId, storeId);
    }

    private void initSnackBar(String message, View viewBar) {
        Snackbar snackbar = Snackbar.make(viewBar, message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView snackBarMessage = view.findViewById(R.id.snackbar_text);
        snackBarMessage.setTextColor(Color.WHITE);
//        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.green));
//        snackbar.setAction(context.getResources().getString(R.string.show_cart), v -> {
//
//        });
        snackbar.show();
    }

    private void deleteCart(View v, int productId, int product_barcode_id, int cart_id, int userId, int storeId) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            if (IsSuccess) {
                initSnackBar(getString(R.string.success_delete_from_cart), v);


            } else {

                initSnackBar(getString(R.string.fail_to_delete_cart), v);
            }


        }).deleteCartHandle(productId, product_barcode_id, cart_id, userId, storeId);
    }

    private void updateCart(View v, int productId, int product_barcode_id, int quantity, int userId, int storeId, int cart_id, String update_quantity) {
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (IsSuccess) {

                binding.productCartQTY.setText(String.valueOf(quantity));
                initSnackBar(getString(R.string.success_to_update_cart), v);

            } else {

                initSnackBar(getString(R.string.fail_to_update_cart), v);

            }

        }).updateCartHandle(productId, product_barcode_id, quantity, userId, storeId, cart_id, update_quantity);
    }


}