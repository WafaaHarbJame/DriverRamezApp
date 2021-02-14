package com.ramez.driver.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramez.driver.Activities.GetMyInvoiceInfoActivity;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.HomeActivity;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.RamesOrders;
import com.ramez.driver.Models.RamezOrderCall;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;

import java.util.List;
import java.util.Locale;

public class Adapter_Ramez_Order extends RecyclerView.Adapter<Adapter_Ramez_Order.MyViewHolder> {

    private static final String TAG = "Adapter_My_Order";
    MemberModel memberModel;
    int postion_card;
    String LangCode;
    String type;
    private Context mContext;
    private List<RamesOrders> arrylist;
    // TODO: Firebase References
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_roomChat;


    public Adapter_Ramez_Order(Context mContext, List<RamesOrders> arrylist, int postion_card) {
        this.mContext = mContext;
        this.arrylist = arrylist;
        this.postion_card = postion_card;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        db_roomChat = FirebaseDatabase.getInstance().getReference().child("Chats").child("Rooms");
        db_roomChat.keepSynced(true);

    }

    @Override
    public Adapter_Ramez_Order.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_myorder_ramez, parent, false);

        return new Adapter_Ramez_Order.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final Adapter_Ramez_Order.MyViewHolder holder, final int position) {
        final RamesOrders ramesOrders = arrylist.get(position);
        holder.detiels.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, GetMyInvoiceInfoActivity.class);
            intent.putExtra(Constants.id, ramesOrders.getId() + "");
            intent.putExtra(Constants.ramezrequest, true);
            mContext.startActivity(intent);

        });

        holder.address.setText(ramesOrders.getAddress());

        holder.loction.setOnClickListener(v -> {


            String uri = "http://maps.google.com/maps?daddr=" + ramesOrders.getLat() + "," + ramesOrders.getLng() + " (" + "Where the party is at" + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            try {
                mContext.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                try {
                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    mContext.startActivity(unrestrictedIntent);
                } catch (ActivityNotFoundException innerEx) {
                    Toast.makeText(mContext, "Please install a maps application", Toast.LENGTH_LONG).show();
                }
            }
            return;
        });


        if (ramesOrders.getType() == 1) {
            holder.detiels.setVisibility(View.GONE);

        } else {
            holder.detiels.setVisibility(View.VISIBLE);

        }
        if (memberModel.getRole_id() == 4) {
            if (ramesOrders.getType() == 2) {

                holder.line4.setVisibility(View.VISIBLE);
            } else {
                holder.line4.setVisibility(View.GONE);

            }
        }


        holder.cardView.setOnClickListener(v -> {
            if (postion_card == 0) {
                final String confirm_procedure = mContext.getResources().getString(R.string.confirm_procedure);
                String done1 = mContext.getResources().getString(R.string.done);
                final String order_Processing = mContext.getResources().getString(R.string.order_Processing);
                final String Receiving_the_order = mContext.getResources().getString(R.string.Receiving_the_order);
                final String back = mContext.getResources().getString(R.string.back);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(order_Processing);
                builder.setMessage(confirm_procedure);
                builder.setCancelable(false);
                builder.setPositiveButton(Receiving_the_order, (dialog, which) -> {
                    if (memberModel.getRole_id() == 6) {
                        type = Constants.Packer;
                        //picker
                        ChangeOrderStatusFromRamez(type, arrylist.get(position).getId(), 1);
                    }

                    if (memberModel.getRole_id() == 4) {
                        // driver
                        type = Constants.Driver;
                        ChangeOrderStatusFromRamez(type, arrylist.get(position).getId(), 1);


                    }


                });
                builder.setNegativeButton(back, (dialog, which) -> {

                });
                builder.show();

            } else if (postion_card == 1) {

                final String confirm_procedure = mContext.getResources().getString(R.string.confirm_procedure);
                final String order_Processing = mContext.getResources().getString(R.string.order_Processing);
                final String finshed_order = mContext.getResources().getString(R.string.finshed_order);
                final String back = mContext.getResources().getString(R.string.back);
                final String cancel_order = mContext.getResources().getString(R.string.cancel_order);
                final String Reason_cancellation = mContext.getResources().getString(R.string.Reason_cancellation);
                final String Ok = mContext.getResources().getString(R.string.Ok);
                final String Access_alert = mContext.getResources().getString(R.string.Access_alert);
                final String cancellation = mContext.getResources().getString(R.string.cancellation);
                final String pick_alert = mContext.getResources().getString(R.string.pick_alert);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(order_Processing);

                if (memberModel.getRole_id() == 6) {
                    type = Constants.Packer;
                    builder.setItems(new CharSequence[]{finshed_order, back}, (dialog, which) -> {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {

                            case 0:
                                AlertDialog.Builder builders_Arriva = new AlertDialog.Builder(mContext);
                                builders_Arriva.setTitle(finshed_order);
                                builders_Arriva.setMessage(confirm_procedure);
                                builders_Arriva.setCancelable(false);
                                builders_Arriva.setPositiveButton(finshed_order, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ChangeOrderStatusFromRamez(type, arrylist.get(position).getId(), 2);

                                    }
                                });
                                builders_Arriva.setNeutralButton(back, null);
                                builders_Arriva.show();


                                break;

                            case 1:

                                break;
                        }
                    });
                }
                if (memberModel.getRole_id() == 4) {
                    type =Constants.Driver;
                    builder.setItems(new CharSequence[]{finshed_order, Access_alert, cancel_order, back}, new DialogInterface.OnClickListener() {
                        @SuppressLint("WrongConstant")
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0:
                                    AlertDialog.Builder builders = new AlertDialog.Builder(mContext);
                                    builders.setTitle(order_Processing);
                                    builders.setMessage(confirm_procedure);
                                    builders.setCancelable(false);
                                    builders.setPositiveButton(finshed_order, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ChangeOrderStatusFromRamez(type, arrylist.get(position).getId(), 2);

                                        }
                                    });
                                    builders.setNeutralButton(back, null);
                                    builders.show();
                                    break;


                                case 1:
                                    AlertDialog.Builder builders_Arriva = new AlertDialog.Builder(mContext);
                                    builders_Arriva.setTitle(Access_alert);
                                    builders_Arriva.setMessage(confirm_procedure);
                                    builders_Arriva.setCancelable(false);
                                    builders_Arriva.setPositiveButton(Access_alert, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            // Volley_DeliveryArrivalNotification(arrylist.get(position).getId() + "");
                                        }
                                    });
                                    builders_Arriva.setNeutralButton(back, null);
                                    builders_Arriva.show();


                                    break;


                                case 2:
                                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                    final EditText edittext = new EditText(mContext);

                                    alert.setMessage(Reason_cancellation);

                                    alert.setView(edittext);

                                    alert.setPositiveButton(cancel_order, (dialog1, whichButton) -> {

                                        final String YouEditTextValue_price = edittext.getText().toString();

                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                        builder1.setMessage(confirm_procedure);
                                        builder1.setCancelable(false);
                                        builder1.setPositiveButton(cancel_order, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog1, int which1) {

                                                if (YouEditTextValue_price.equals("")) {
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                                    builder1.setMessage(cancellation);
                                                    builder1.setCancelable(false);
                                                    builder1.setPositiveButton(back, null);
                                                    builder1.show();


                                                } else {
                                                    ChangeOrderStatusFromRamez(type, arrylist.get(position).getId(), 3);

                                                }
                                            }
                                        });
                                        builder1.setNeutralButton(back, null);
                                        builder1.show();
                                    });
                                    alert.setNeutralButton(back, null);

                                    alert.show();
                                    break;


                                case 3:

                                    break;
                            }
                        }
                    });
                }
                builder.create().show();

            } else if (postion_card == 2) {


            }
        });

        holder.tvInvID.setText("# " + arrylist.get(position).getId() + "");
        holder.name_shop.setText("Ramez");
        holder.payment_method.setText(arrylist.get(position).getPayment_method() + "");
        holder.data.setText(arrylist.get(position).getDelivery_date());

    }

    @Override
    public int getItemCount() {
        return arrylist.size();
    }

    void ChangeOrderStatusFromRamez(String type, int order_id, int mode) {

        RamezOrderCall ramezOrderCall = new RamezOrderCall();
        ramezOrderCall.type = type;
        ramezOrderCall.mode = mode;
        ramezOrderCall.user_id = memberModel.getId();
        ramezOrderCall.store_id = 7263;
        ramezOrderCall.order_id = order_id;

        GlobalData.progressDialog(mContext, R.string.change_order, R.string.please_wait_sending);
        new DataFeacher(false, (obj, func, IsSuccess) -> {

            ResultAPIModel<String> result = (ResultAPIModel) obj;
            GlobalData.hideProgressDialog();
            if (func.equals(Constants.ERROR)) {
                Toast.makeText(mContext, "" + mContext.getString(R.string.fail_to_update_order), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.FAIL)) {
                Toast.makeText(mContext, "" + mContext.getString(R.string.fail_to_update_order), Toast.LENGTH_SHORT).show();
            } else if (func.equals(Constants.NO_CONNECTION)) {
                GlobalData.Toast(mContext, R.string.no_internet_connection);
            } else {
                if (IsSuccess) {

                    if (result.status == 200) {
                        String ok = mContext.getResources().getString(R.string.Ok);

                        String message = result.message;

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(message);
                        builder.setCancelable(false);
                        builder.setPositiveButton(ok, (dialog, which) -> {
                            Intent intent = new Intent(mContext, HomeActivity.class);
                            mContext.startActivity(intent);
                            ((Activity) mContext).finish();
                        });


                        builder.show();


                    } else {

                        String message = mContext.getString(R.string.fail_to_update_order);
                        if (result != null && result.message != null) {
                            message = result.message;
                        }

                        GlobalData.errorDialog(mContext, R.string.change_order, message);
                    }


                } else {
                    String message = mContext.getString(R.string.fail_to_update_order);

                    GlobalData.errorDialog(mContext, R.string.change_order, message);
                }
            }

        }).ChangeOrderStatusFromRamez(ramezOrderCall);


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_shop, payment_method, data, price, status, tvInvID, countTv, priceTv, address;
        ImageView loction, image_type;
        Button detiels;
        CardView cardView;
        LinearLayout line4;

        public MyViewHolder(View view) {
            super(view);
            tvInvID = view.findViewById(R.id.tvInvID);
            name_shop = view.findViewById(R.id.name_shop);
            payment_method = view.findViewById(R.id.payment_method);
            data = view.findViewById(R.id.data);
            loction = view.findViewById(R.id.loction);
            image_type = view.findViewById(R.id.image_type);
            detiels = view.findViewById(R.id.detiels);
            cardView = view.findViewById(R.id.cardView);
            LangCode = Locale.getDefault().getLanguage();
            memberModel = UtilityApp.getUserData();
            address = view.findViewById(R.id.address);
            line4 = view.findViewById(R.id.line4);

        }
    }

}