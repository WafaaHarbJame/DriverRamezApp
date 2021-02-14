package com.ramez.driver.Fragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramez.driver.Adapter.MessageAdapter;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.ChatMessage;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.databinding.FragmentMessagesBinding;
import com.ramez.driver.databinding.FragmentNewMessagesBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class NewMessagesFragment extends Fragment {
    MemberModel memberModel;
    int user_id = 0;
    RecyclerView recyclerView;
    ArrayList<ChatMessage> chatMessageArrayList;
    MessageAdapter messageAdapter;
    String currentMonthNumber;
    private FragmentNewMessagesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewMessagesBinding.inflate(inflater, container, false);

        chatMessageArrayList = new ArrayList<>();

        if (UtilityApp.isLogin()) {
            if (UtilityApp.getUserData() != null) {
                memberModel = UtilityApp.getUserData();
            }
        }


        Bundle bundle = getArguments();

        chatMessageArrayList = (ArrayList<ChatMessage>) bundle.getSerializable(Constants.KEY_MESSAGES_MODEL);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
        currentMonthNumber = (String) DateFormat.format("MM", Calendar.getInstance().getTime());
        initAdapter();


        binding.dataLY.setOnRefreshListener(() -> {
            binding.dataLY.setRefreshing(false);

        });

        return binding.getRoot();

    }


    private void initAdapter() {
        Collections.sort(chatMessageArrayList, Collections.reverseOrder());
        messageAdapter = new MessageAdapter(getActivity(), chatMessageArrayList);
        binding.recycler.setAdapter(messageAdapter);
    }
}