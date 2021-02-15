package com.ramez.driver.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack;
import com.kcode.permissionslib.main.PermissionCompat;
import com.ramez.driver.Adapter.ChatAdapter;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Dialogs.PickImageDialog;
import com.ramez.driver.Models.ChatModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.ramez.driver.Utils.PathUtil;
import com.ramez.driver.databinding.ActivityContactSupportBinding;
import com.ramez.driver.databinding.ActivityNewChatBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewChatActivity extends ActivityBase {
    ActivityNewChatBinding binding;
    ArrayList<ChatModel> list;
    DatabaseReference databaseReference;
    String TAG = "ContactSupportActivity";
    String message, userID = "", imageUrl = "", userName = "", user_image = "", providerID = "", providerName = "", orderID = "";
    String chatId = "";
    MemberModel user;
    PickImageDialog pickImageDialog;

    int REQUEST_PICK_IMAGE = 11;
    int REQUEST_CAPTURE_IMAGE = 12;
    File selectedPhotoFil = null;
    double inv_lat, inv_lng;


    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Map<String, Object> chatsUpdates;
    private ArrayList<ChatModel> chatMessages, tempChatList;
    private ChatModel chatMessage;
    private String inputType = "";
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        user = UtilityApp.getUserData();
        userID = String.valueOf(user.getId());

        userName = user.getName();
        user_image = user.getProfilePicture();

        chatMessages = new ArrayList<>();
        tempChatList = new ArrayList<>();
        chatsUpdates = new HashMap<>();

        inputType = Constants.inputType_text;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats").child("Rooms");

        setTitle(R.string.customer_service);

        linearLayoutManager = new LinearLayoutManager(getActiviy());
        binding.rv.setLayoutManager(linearLayoutManager);

        chatAdapter = new ChatAdapter(getActiviy(), chatMessages);
        binding.rv.setAdapter(chatAdapter);

        getExtraData();


        binding.sendMessageBtn.setOnClickListener(view1 -> {

            message = binding.messageTxt.getText() + "";

            if (inputType.equals(Constants.inputType_text) && message.trim().length() == 0) {
                binding.messageTxt.requestFocus();
                binding.messageTxt.setError(getString(R.string.enter_message));
            } else {
                if (inputType.equals(Constants.inputType_text)) {
                    sendChatMessage();
                } else {

                    uploadPhoto(Integer.parseInt(userID), selectedPhotoFil);

                }
            }
        });

        binding.addBut.setOnClickListener(view1 -> {
            openPicker();
        });

        binding.ivRemoveImage.setOnClickListener(view1 -> {
            resetInputs();

        });
    }

    public void initAdapter() {

        chatAdapter = new ChatAdapter(getActiviy(), list);
        binding.rv.setAdapter(chatAdapter);
    }


    private void getRoomChats() {

        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);

        databaseReference.child(chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    chatMessages.clear();
                    chatsUpdates.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: " + snapshot.getValue());
                        chatMessage = new ChatModel();

                        chatMessage = snapshot.getValue(ChatModel.class);

                        if (chatMessage.getSenderID().equals(userID)) {
                            chatMessage.setMessageType(Constants.Sender);
                            chatMessages.add(chatMessage);
                        } else {
                            chatMessage.setMessageType(Constants.Receiver);
                            chatMessages.add(chatMessage);
                            if (!chatMessage.isIs_read()) {
                                chatMessage.setIs_read(true);
                                chatsUpdates.put(snapshot.getKey(), chatMessage);
                            }
                        }
                    }

                    chatAdapter.notifyDataSetChanged();
                    databaseReference.child(chatId).updateChildren(chatsUpdates);
                    binding.rv.scrollToPosition(chatMessages.size() - 1);

                }

                binding.dataLY.setVisibility(View.VISIBLE);
                binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);
                binding.dataLY.setVisibility(View.VISIBLE);


            }
        });
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);
        binding.dataLY.setVisibility(View.VISIBLE);

    }

    private void getExtraData() {

        orderID = "0";

        if (providerName == null || providerName.isEmpty()) {
            providerName = getString(R.string.customer_service);
            orderID = "0";
            providerID = "0";
        }
        chatId = "room: " + (orderID + " - " + userID + " - " + providerID);


        orderID = getIntent().getExtras().getString(Constants.order_id, "");
        userID = getIntent().getExtras().getString(Constants.user_id, "");
        userName = getIntent().getExtras().getString(Constants.user_firstname, "");
        providerID = getIntent().getExtras().getString(Constants.provider_id, "");
        providerName = getIntent().getExtras().getString(Constants.provider_name, "");
        inv_lat = getIntent().getExtras().getDouble(Constants.inv_lat);
        inv_lng = getIntent().getExtras().getDouble(Constants.inv_lng);

        Log.d(TAG, "onDataChange: " + orderID + "" + userID + "" + providerID + "" + providerName);
        chatId = "room: " + (orderID + " - " + userID + " - " + providerID);

        binding.toolBar.mainTitleTxt.setText(userName);


        getRoomChats();

    }

    private void sendChatMessage() {
        chatMessage = new ChatModel();
        String key = databaseReference.child(chatId).push().getKey();
        chatMessage.setSenderID(userID);
        chatMessage.setSenderName(userName);
        chatMessage.setSenderImage(user_image);
        chatMessage.setReceiverID(providerID);
        chatMessage.setReceiverName(providerName);
        chatMessage.setReceiverImage("null");
        chatMessage.setMessageBody(message.trim());
        chatMessage.setIs_read(false);
        chatMessage.setMessageTime(System.currentTimeMillis());
        chatMessage.setMessageType(Constants.Sender);
        chatMessage.setInputType(inputType);
        chatMessage.setImageUrl(imageUrl);
        chatMessage.setLng(String.valueOf(""));
        chatMessage.setLat(String.valueOf(""));
        binding.messageTxt.setText("");

        if (key != null) ;
        databaseReference.child(chatId).child(key).setValue(chatMessage).addOnCompleteListener(this, task -> {

            binding.rv.scrollToPosition(chatMessages.size() - 1);
            binding.ImageContainer.setVisibility(View.GONE);
            chatAdapter.notifyDataSetChanged();


        });

    }

    private void openPicker() {
        try {
            PermissionCompat.Builder builder = new PermissionCompat.Builder((getActiviy()));
            builder.addPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            builder.addPermissionRationale(getString(R.string.should_allow_permission));

            builder.addRequestPermissionsCallBack(new OnRequestPermissionsCallBack() {
                public void onGrant() {

                    pickImage();
                }

                public void onDenied(@NotNull String permission) {
                    Toast(R.string.some_permission_denied);

                }
            });
            builder.build().request();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private void pickImage() {
        pickImageDialog = new PickImageDialog(getActiviy(), (obj, func, IsSuccess) -> {

            if (func.equals(Constants.CAPTURE)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);

            } else if (func.equals(Constants.PICK)) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, getString(R.string.selectImage)), REQUEST_PICK_IMAGE);

            }
        });
        pickImageDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Glide.with(getActiviy()).load(imageBitmap).into(binding.ivChatImage);
                String imagePath = PathUtil.getPath(getActiviy(), data.getData());
                selectedPhotoFil = new File(imagePath);
                binding.ImageContainer.setVisibility(View.VISIBLE);
                inputType = Constants.inputType_image;

            } catch (Exception e) {
                e.printStackTrace();
                GlobalData.errorDialog(getActiviy(), R.string.upload_photo, getString(R.string.textTryAgain));
            }
        }

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {

            try {
                imageBitmap = (Bitmap) data.getExtras().get("data");
                Glide.with(getActiviy()).load(imageBitmap).into(binding.ivChatImage);
                String imagePath = PathUtil.getPath(getActiviy(), data.getData());
                selectedPhotoFil = new File(imagePath);
                binding.ImageContainer.setVisibility(View.VISIBLE);
                inputType = Constants.inputType_image;


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", "onActivityResult: Exception GALLERY_CONSTANT: " + e.getMessage());
                GlobalData.errorDialog(getActiviy(), R.string.upload_photo, getString(R.string.textTryAgain));
            }

        }


    }


    private void uploadPhoto(int userId, File photo) {
        String country;

        Log.i("tag", "Log  userId " + userId);
        Log.i("tag", "Log  photo " + photo.getName());
        Log.i("tag", "Log  uploadPhoto " + photo.getName());

        GlobalData.progressDialog(getActiviy(), R.string.upload_photo, R.string.please_wait_to_upload_photo);

        if (UtilityApp.getLocalData().getShortname() != null) {
            country = UtilityApp.getLocalData().getShortname();

        } else {
            country = GlobalData.COUNTRY;

        }

        AndroidNetworking.upload(GlobalData.BetaBaseURL + country + GlobalData.grocery + GlobalData.Api + "v3/Account/UploadPhoto" + "?user_id=" + userId).addMultipartFile("file", photo)

                .addHeaders("ApiKey", Constants.api_key).build().
                setUploadProgressListener((bytesUploaded, totalBytes) -> {
                    // do anything with progress
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                GlobalData.hideProgressDialog();
                Log.i("tag", "Log data response " + response);

                String message = getString(R.string.fail_to_load);

                if (response.equals(Constants.ERROR)) {
                    GlobalData.errorDialog(getActiviy(), R.string.fail_to_load, message);
                } else {

                    String data = null;
                    try {
                        JSONObject jsonObject = response;
                        int status = jsonObject.getInt("status");
                        if (status == 200) {
                            data = jsonObject.getString("data");

                            Log.i("tag", "Log data result " + data);
                            imageUrl = data;
                            sendChatMessage();
                            GlobalData.successDialog(getActiviy(), getString(R.string.upload_photo), getString(R.string.success_upload));

                        } else {
                            message = jsonObject.getString("message");
                            GlobalData.errorDialog(getActiviy(), R.string.fail_to_load, message);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onError(ANError error) {
                // handle error
            }
        });
    }


    private void resetInputs() {
        binding.ImageContainer.setVisibility(View.GONE);
        inputType = Constants.inputType_text;
        imageUrl = "";
    }


}
