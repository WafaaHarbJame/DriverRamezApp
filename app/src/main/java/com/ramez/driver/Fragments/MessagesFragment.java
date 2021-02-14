package com.ramez.driver.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.ChatMessage;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.DateHandler;
import com.ramez.driver.databinding.FragmentMessagesBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;
    MemberModel memberModel;
    int user_id = 0;
    ArrayList<ChatMessage> oldMessages;
    ArrayList<ChatMessage> currentMessages;
    String currentMonthNumber;
    private DatabaseReference db_roomChat;
    private StorageReference storageReference;
    boolean showLoading = true;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        if(UtilityApp.isLogin()){
            if(UtilityApp.getUserData()!=null){
                memberModel=UtilityApp.getUserData();
            }
        }


        oldMessages = new ArrayList<>();
        currentMessages = new ArrayList<>();

        currentMonthNumber = (String) DateFormat.format("MM", Calendar.getInstance().getTime());

        storageReference = FirebaseStorage.getInstance().getReference();
        db_roomChat = FirebaseDatabase.getInstance().getReference().child("Chats").child("Rooms");
        db_roomChat.keepSynced(true);

        new UploadMessages().execute();





        return  binding.getRoot();

    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Bundle newBundle = new Bundle();
        newBundle.putSerializable(Constants.KEY_MESSAGES_MODEL, currentMessages);
        Fragment newFragment = new NewMessagesFragment();
        newFragment.setArguments(newBundle);
        newFragment.setRetainInstance(true);


        Bundle oldBundle = new Bundle();
        oldBundle.putSerializable(Constants.KEY_MESSAGES_MODEL, oldMessages);
        Fragment oldMessagesFragment = new OldMessagesFragment();
        oldMessagesFragment.setArguments(oldBundle);
        oldMessagesFragment.setRetainInstance(true);




        adapter.addFragment(newFragment, getString(R.string.New_Messages));
        adapter.addFragment(oldMessagesFragment, getString(R.string.old_messages));

        viewPager.setAdapter(adapter);

    }

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private class UploadMessages extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            GlobalData.progressDialog(getActivity(),R.string.get_data,R.string.please_wait_sending);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getMessages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GlobalData.hideProgressDialog();
        }
    }


    public void getMessages() {
        oldMessages.clear();
        currentMessages.clear();
        db_roomChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oldMessages.clear();
                for (DataSnapshot livenShot : dataSnapshot.getChildren()) {
                    ChatMessage roomModel = null;
                    ChatMessage lastMessage = null;
                    int count = 0;

                    for (DataSnapshot snapshot : livenShot.getChildren()) {
                        ChatMessage chatMessage1 = snapshot.getValue(ChatMessage.class);

                        if (lastMessage == null || (chatMessage1 != null && lastMessage.getMessageTime() < chatMessage1.getMessageTime())) {
                            lastMessage = chatMessage1;
                        }
                        if (chatMessage1 != null && chatMessage1.getReceiverID() != null && chatMessage1.getReceiverID().equals("0")) {

                            roomModel = chatMessage1;
                        }

                        if (chatMessage1 != null && !chatMessage1.isIs_read() && chatMessage1.getReceiverID() != null && chatMessage1.getReceiverID().equals("0")) {
                            count++;
                        }

                    }
                    if (roomModel != null) {
                        roomModel.setCount(count);
                        roomModel.setMessageBody(lastMessage.getMessageBody());
                        long messageDate = roomModel.getMessageTime();
                        String date = DateHandler.convertFromLongToDate(String.valueOf(messageDate));
                        String messageMonth = (String) DateFormat.format("MM", DateHandler.GetDate(date));
                        if (messageMonth.equals(currentMonthNumber)) {
                            currentMessages.add(roomModel);
                        } else {
                            oldMessages.add(roomModel);
                        }

                    }
                }

//                binding.viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
//                binding.tabs.setupWithViewPager(binding.viewPager);
//
                setupViewPager(binding.viewPager);
                binding.tabs.setupWithViewPager(binding.viewPager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
             GlobalData.hideProgressDialog();

            }
        });
    }

}