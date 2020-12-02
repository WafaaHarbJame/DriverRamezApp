package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ramez.shopp.Adapter.AddressAdapter;
import com.ramez.shopp.Adapter.ChatAdapter;
import com.ramez.shopp.Models.AddressModel;
import com.ramez.shopp.Models.ChatModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityContactSupportBinding;
import com.ramez.shopp.databinding.ActivityInvoiceInfoBinding;

import java.util.ArrayList;

public class ContactSupportActivity extends ActivityBase {
    ActivityContactSupportBinding binding;
    private ChatAdapter chatAdapter;
    ArrayList<ChatModel> list;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityContactSupportBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        list=new ArrayList<>();
        list.add(new ChatModel(1,1,1,1,
                        "hhh","","httpp://","18/12/2020 5.30 م","",getString(R.string.Hello)));
        list.add(new ChatModel(1,1,1,1,
                "hhh","","httpp://","","18/12/2020 5.30 م",getString(R.string.Hello)));
        list.add(new ChatModel(1,1,1,1,
                "hhh","","httpp://","","18/12/2020 5.30 م",getString(R.string.Hello)));
        list.add(new ChatModel(1,1,1,1,
                "hhh","","httpp://","","18/12/2020 5.30 م",getString(R.string.Hello)));


        binding.toolBar.mainTitleTxt.setText(getString(R.string.customer_service));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        linearLayoutManager=new LinearLayoutManager(getActiviy());
        binding.rv.setLayoutManager(linearLayoutManager);

        initAdapter();


    }
    public void initAdapter(){

        chatAdapter=new ChatAdapter(getActiviy(),list);
        binding.rv.setAdapter(chatAdapter);
    }
}