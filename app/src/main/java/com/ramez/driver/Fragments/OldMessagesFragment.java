package com.ramez.driver.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ramez.driver.Adapter.MessageAdapter;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.ChatMessage;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentMessagesBinding;
import com.ramez.driver.databinding.FragmentOldMessagesBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class OldMessagesFragment extends Fragment {
    private FragmentOldMessagesBinding binding;
    MemberModel memberModel;
    RecyclerView recyclerView;
    ArrayList<ChatMessage> chatMessageArrayList;
    MessageAdapter messageAdapter;
    String currentMonthNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOldMessagesBinding.inflate(inflater, container, false);

        chatMessageArrayList = new ArrayList<>();

        if(UtilityApp.isLogin()){
            if(UtilityApp.getUserData()!=null){
                memberModel=UtilityApp.getUserData();
            }
        }


        Bundle bundle=getArguments();
        chatMessageArrayList= (ArrayList<ChatMessage>) bundle.getSerializable(Constants.KEY_MESSAGES_MODEL);

        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
        currentMonthNumber = (String) DateFormat.format("MM",   Calendar.getInstance().getTime());
        initAdapter();

        binding.dataLY.setOnRefreshListener(() -> {
            binding.dataLY.setRefreshing(false);

        });
        return  binding.getRoot();

    }


    private void initAdapter(){
        Collections.sort(chatMessageArrayList, Collections.reverseOrder());
        messageAdapter = new MessageAdapter(getActivity(), chatMessageArrayList);
        binding.recycler.setAdapter(messageAdapter);
    }



}