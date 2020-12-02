package com.ramez.shopp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ramez.shopp.Adapter.CategoryAdapter;
import com.ramez.shopp.Classes.CategoryModel;
import com.ramez.shopp.databinding.FragmentCategoryBinding;

import java.util.ArrayList;

public class CategoryFragment extends FragmentBase implements CategoryAdapter.OnItemClick {
    private FragmentCategoryBinding binding;
    private CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModelList;
    GridLayoutManager gridLayoutManager;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        categoryModelList =new ArrayList<>();
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        categoryModelList.add(new CategoryModel(1,"الأغذية المعلبة","Canned food","https://cdn.images.express.co.uk/img/dynamic/130/590x/can-605163.jpg"));
        categoryModelList.add(new CategoryModel(1,"مشروبات","drinks","https://www.supermama.me/system/App/Entities/Article/images/000/065/491/web-watermarked-large/%D9%85%D8%B4%D8%B1%D9%88%D8%A8%D8%A7%D8%AA-%D8%AD%D8%A7%D8%B1%D9%82%D8%A9-%D9%84%D9%84%D8%AF%D9%87%D9%88%D9%86.jpg"));
        gridLayoutManager=new GridLayoutManager(getActivityy(),3);


        initAdapter();

        return view;
    }

    private void initAdapter() {
        binding.catRecycler.setHasFixedSize(true);
        binding.catRecycler.setLayoutManager(gridLayoutManager);
        categoryAdapter=new CategoryAdapter(getActivityy(),categoryModelList,this);
        binding.catRecycler.setAdapter(categoryAdapter);

    }



    @Override
    public void onItemClicked(int position, CategoryModel categoryModel) {

    }
}