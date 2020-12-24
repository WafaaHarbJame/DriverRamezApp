package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ramez.shopp.Adapter.AddressAdapter;
import com.ramez.shopp.Adapter.ChatAdapter;
import com.ramez.shopp.Classes.Constants;
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

        list.add(new ChatModel("1","2","key",getString(R.string.Hello),
                        "hhh","",
                "http://","http", Constants.Sender,Constants.inputType_image,
                "http://","","",1699999,true));

        list.add(new ChatModel("1","2","key",Constants.inputType_image,
                "hhh","",
                "http://","http",Constants.Receiver,Constants.inputType_text,
                "http://","","",1698889999,true));

        list.add(new ChatModel("1","2","key",getString(R.string.Hello1),
                "hhh","",
                "http://","http",Constants.Sender,Constants.inputType_image,
                "http://","","",169955999,true));


        setTitle(R.string.customer_service);

        linearLayoutManager=new LinearLayoutManager(getActiviy());
        binding.rv.setLayoutManager(linearLayoutManager);

        initAdapter();


    }
    public void initAdapter(){

        chatAdapter=new ChatAdapter(getActiviy(),list);
        binding.rv.setAdapter(chatAdapter);
    }
}