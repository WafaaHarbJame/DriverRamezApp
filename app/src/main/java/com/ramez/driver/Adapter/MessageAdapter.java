package com.ramez.driver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.UserInfo;
import com.ramez.driver.Activities.NewChatActivity;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Fragments.ProfileFragment;
import com.ramez.driver.Models.ChatMessage;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.DateHandler;
import com.ramez.driver.databinding.RowMessageItemBinding;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    private static final String TAG = "MessageAdapter";
    private Context mContext;
    private List<ChatMessage> chatMessageList;
    MemberModel memberModel;



    public MessageAdapter(Context context, List<ChatMessage> chatMessageList) {
        this.mContext = context;
        this.chatMessageList = chatMessageList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowMessageItemBinding itemView = RowMessageItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final ChatMessage  chatMessage = chatMessageList.get(position);
        holder.binding.messageBody.setText(chatMessage.getMessageBody());
        holder.binding.senderNameTv.setText(chatMessage.getSenderName());
        holder.binding.dateTv.setText(DateHandler.convertLongDateToString(String.valueOf(chatMessage.getMessageTime()),
                "yyyy-MM-dd hh:mm aa"));
        Glide.with(mContext).load(GlobalData.upload_user +chatMessage.getSenderImage()).placeholder(R.drawable.avatar).into(holder.binding.ivProfileImage);
        if(chatMessage.getCount()==0){
            holder.binding.countTv.setVisibility(View.GONE);

        }
        else {
            holder.binding.countTv.setText(chatMessage.getCount()+" ");

        }



    }




    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    public void addAll(List<ChatMessage> newchatMessageList) {
        int initialSize = chatMessageList.size();
        chatMessageList.addAll(newchatMessageList);
        notifyItemRangeInserted(initialSize, newchatMessageList.size());
    }


    public String getLastItemId() {
        return String.valueOf(chatMessageList.get(chatMessageList.size() - 1).getMessageTime());
    }

    public void removeLast(){
        chatMessageList.remove(chatMessageList.size()-1);

    }

    class Holder extends RecyclerView.ViewHolder {

        RowMessageItemBinding binding;

        Holder(RowMessageItemBinding view) {
            super(view.getRoot());
            binding = view;
            binding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NewChatActivity.class);
                    memberModel = UtilityApp.getUserData();
                    int position=getAdapterPosition();
                    intent.putExtra(Constants.user_id, chatMessageList.get(position).getSenderID() + "");
                    intent.putExtra(Constants.order_id, "0");
                    intent.putExtra(Constants.provider_id, chatMessageList.get(position).getReceiverID() + "");
                    intent.putExtra(Constants.provider_name, memberModel.getName() + "");
                    intent.putExtra(Constants.inv_lat,chatMessageList.get(position).getLat());
                    intent.putExtra(Constants.inv_lng, chatMessageList.get(position).getLng());
                    intent.putExtra(Constants.user_firstname, chatMessageList.get(position).getSenderName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);


                }
            });
            binding.ivProfileImage.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, ProfileFragment.class);
                int position=getAdapterPosition();
                intent.putExtra(Constants.user_id, chatMessageList.get(position).getSenderID() + "");
                mContext.startActivity(intent);


            });

            binding.senderNameTv.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, ProfileFragment.class);
                int position=getAdapterPosition();
                intent.putExtra(Constants.user_id, chatMessageList.get(position).getSenderID() + "");
                mContext.startActivity(intent);


            });


        }
    }

}