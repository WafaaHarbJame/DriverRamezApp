package com.ramez.shopp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityAllListBinding;

import java.util.ArrayList;

public class AllListActivity extends ActivityBase implements ProductAdapter.OnItemClick {

    ActivityAllListBinding binding;
    private ProductAdapter adapter;
    ArrayList<ProductModel> list;
    GridLayoutManager gridLayoutManager;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllListBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        list=new ArrayList<>();
        gridLayoutManager =new GridLayoutManager(getActiviy(),2);
        binding.recycler.setLayoutManager(gridLayoutManager);
        binding.recycler.setHasFixedSize(true);

        getIntentExtra();

        setTitle(name);

        binding.swipeDataContainer.setOnRefreshListener(() -> {
            binding.swipeDataContainer.setRefreshing(false);

        });



    }

    public void initAdapter(){
        adapter = new ProductAdapter(getActiviy(), list,this,list.size());
        binding.recycler.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }


    private void getIntentExtra() {
        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
            list= (ArrayList<ProductModel>) bundle.getSerializable(Constants.LIST_MODEL);
            name=bundle.getString(Constants.LIST_MODEL_NAME);

            initAdapter();



        }
    }
}