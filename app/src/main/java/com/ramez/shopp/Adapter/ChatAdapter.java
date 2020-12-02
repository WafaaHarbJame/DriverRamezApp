package com.ramez.shopp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.ChatModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.RowChatBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatModel> countryModels;

    public ChatAdapter(Context context, ArrayList<ChatModel> countryModels) {
        this.context = context;
        this.countryModels = countryModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowChatBinding itemView =RowChatBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatModel countryModel = countryModels.get(position);

        holder.binding.messageTxt.setText(countryModel.getMessageTxt().trim());

        Glide.with(context).asBitmap()
                .load(countryModel.getCustomerAvatar()).placeholder(R.drawable.avatar).placeholder(R.drawable.avatar)
                .into(holder.binding.userImg);



    }

    @Override
    public int getItemCount() {
        return countryModels != null ? countryModels.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RowChatBinding binding;

        ViewHolder(RowChatBinding view) {
            super(view.getRoot());
            binding = view;

        }



    }


}
