package com.ramez.shopp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.AddressModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.databinding.RowAddressItemBinding;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Holder> {

    private Context context;
    private List<AddressModel> addressModelList;
    private OnRadioAddressSelect onRadioAddressSelect;
    private OnDeleteClicked onDeleteClicked;



    public AddressAdapter(Context context, List<AddressModel> addressModelList,
                          OnRadioAddressSelect onRadioAddressSelect, OnDeleteClicked onDeleteClicked) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.onRadioAddressSelect = onRadioAddressSelect;
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowAddressItemBinding itemView = RowAddressItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        RowAddressItemBinding binding = holder.binding;
        AddressModel addressModel=addressModelList.get(position);

        if (UtilityApp.getUserData().lastSelectedAddress ==addressModel.getAddressID()) {
            holder.binding.rbSelectAddress.setChecked(true);
        } else {
            holder.binding.rbSelectAddress.setChecked(false);
        }

        binding.tvAddressMark.setText(addressModel.getAddressMark());
        binding.tvAddressNote.setText(addressModel.getAddressNote());
        binding.tvaAddressTitle.setText(addressModel.getAddressText());

        binding.rbSelectAddress.setOnClickListener(v -> {
            onRadioAddressSelect.onAddressSelected(addressModel);
            MemberModel memberModel=UtilityApp.getUserData();
            memberModel.setLastSelectedAddress(addressModel.getAddressID());
            UtilityApp.setUserData(memberModel);
            notifyDataSetChanged();
        });



        binding.deleteAddressBut.setOnClickListener(v -> {
            onDeleteClicked.onDeleteClicked(addressModel,
                    UtilityApp.getUserData().lastSelectedAddress == position);
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        RowAddressItemBinding binding;
        public Holder(RowAddressItemBinding view) {
            super(view.getRoot());
            binding = view;
        }


    }

    public interface OnRadioAddressSelect {
        void onAddressSelected(AddressModel addressesDM);
    }

    public interface OnEditClicked {
        void onEditClicked(AddressModel addressModel, boolean isChecked);
    }

    public interface OnDeleteClicked {
        void onDeleteClicked(AddressModel addressModel, boolean isChecked);
    }
}