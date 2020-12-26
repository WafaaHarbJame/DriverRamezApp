package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ramez.shopp.Activities.ConfirmPhoneActivity;
import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.GlobalData;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.ErrorMessagesDialog;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.GeneralModel;
import com.ramez.shopp.Models.LoginResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ResultAPIModel;
import com.ramez.shopp.Models.SocialRequestModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.NumberHandler;
import com.ramez.shopp.databinding.FragmentLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginFragment extends FragmentBase implements  GraphRequest.GraphJSONObjectCallback, GoogleApiClient.OnConnectionFailedListener {
    final String TAG = "Log";
    String FCMToken;
    private FragmentLoginBinding binding;
    private ViewPager viewPager;
    private CallbackManager _callbackManager;
    private static final int RC_SIGN_IN = 1001;
    private GoogleApiClient _googleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewPager = container.findViewById(R.id.viewPager);
        View view = binding.getRoot();

        getDeviceToken();

        _callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        _googleApiClient = new GoogleApiClient.Builder(getActivityy())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        binding.textForgotPassword.setOnClickListener(view1 -> {
            startRestPassword();
        });

        binding.skipButton.setOnClickListener(view1 -> {
            startMain();
        });
        binding.loginBut.setOnClickListener(view1 -> {
            if (isValidForm()) {
                loginUser();

            }


        });

        binding.loginGoogleBut.setOnClickListener(view1 -> {
            googleSignIn();


        });

        binding.loginFacebookBut.setOnClickListener(view1 -> {
            facebookSignIn();
        });

        binding.registerBut.setOnClickListener(view1 -> {
            startLogin();


        });


        return view;
    }

    private void loginUser() {

        final String mobileStr = NumberHandler.arabicToDecimal(binding.edtPhoneNumber.getText().toString());
        final String passwordStr = NumberHandler.arabicToDecimal(binding.edtPassword.getText().toString());

        final MemberModel memberModel = new MemberModel();
        memberModel.setMobileNumber(mobileStr);
        memberModel.setPassword(passwordStr);
        memberModel.setDeviceType(Constants.deviceType);
        memberModel.setDeviceToken(FCMToken);
        memberModel.setDeviceId(UtilityApp.getUnique());
        memberModel.setUserType(Constants.user_type);

        GlobalData.progressDialog(getActivityy(), R.string.text_login_login, R.string.please_wait_login);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            LoginResultModel result = (LoginResultModel) obj;

            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }

                GlobalData.errorDialog(getActivityy(), R.string.fail_signin, message);
            }

            else if (func.equals(Constants.FAIL)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                GlobalData.errorDialog(getActivityy(), R.string.fail_signin, message);
            }



            else {
                if (IsSuccess) {
                    MemberModel user = result.data;
                    UtilityApp.setUserData(user);

                    if(UtilityApp.getUserData()!=null){
                        UpdateToken();
                    }



                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).loginHandle(memberModel);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startRestPassword() {
        startActivity(new Intent(getActivityy(), ConfirmPhoneActivity.class));
    }

    private void startLogin() {
        viewPager.setCurrentItem(0);


    }

    private final boolean isValidForm() {
        FormValidator formValidator = FormValidator.Companion.getInstance();

        return formValidator.addField(binding.edtPhoneNumber, new NonEmptyRule(getString(R.string.enter_phone_number))).addField(binding.edtPassword, new NonEmptyRule(R.string.enter_password)).validate();
    }

    private void getDeviceToken() {

        FCMToken = UtilityApp.getFCMToken();
        if (FCMToken == null) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                FCMToken = instanceIdResult.getToken();
                UtilityApp.setFCMToken(FCMToken);
            });

        }

    }

    public void startMain() {
        Intent intent = new Intent(getActivityy(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivityy().finish();

    }

    private void UpdateToken() {

        MemberModel memberModel = UtilityApp.getUserData();
        new DataFeacher(false, (obj, func, IsSuccess) -> {
            GlobalData.hideProgressDialog();
            GeneralModel result = (GeneralModel) obj;
            if (func.equals(Constants.ERROR)) {
                String message = getString(R.string.fail_signin);
                if (result != null && result.getMessage() != null) {
                    message = result.getMessage();
                }
                Toast(message);

            } else {
                if (IsSuccess) {
                    startMain();

                } else {
                    Toast(getString(R.string.fail_signin));

                }
            }


        }).UpdateTokenHandle(memberModel);

    }

    private void facebookSignIn() {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "user_friends", "email"));

        LoginManager.getInstance().registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException arg0) {
            }

            @Override
            public void onSuccess(LoginResult arg0) {
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), null);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,picture,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }
        });
    }


    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
//        String email = null;
//        String facebookId = null;
//        String name = null;
//        try {
//            email = object.getString(PARAMS_EMAIL);
//            facebookId = object.getString(PARAMS_ID);
//            name = object.getString(PARAMS_NAME);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        SocialRequestModel request = new SocialRequestModel();
//        request.setName(name);
//        request.setEmail(email);
//        request.setFacebookKey(facebookId);
//
//
//        if (_loginPresenter != null) {
//            _loginPresenter.fbLogin(request);
//        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            _callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            SocialRequestModel request = new SocialRequestModel();
            request.setEmail(acct.getEmail());
            request.setName(acct.getDisplayName());
            /*  if (_textPhoneCode.getTag() != null) {
                CountryModel countryModel = (CountryModel) _textPhoneCode.getTag();
                request.setCountryId(countryModel.getCountryId());
            }*/
            request.setGoogleKey(acct.getId());
            Auth.GoogleSignInApi.signOut(_googleApiClient);
//            if (_loginPresenter != null)
//                _loginPresenter.googleLogin(request);
        } /*else {
            onError(Constant.EMPTY_STRING);
        }*/

    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(_googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}