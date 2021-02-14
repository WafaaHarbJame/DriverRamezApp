package com.ramez.driver;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.ramez.driver.Activities.ActivityBase;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.databinding.ActivityHomeBinding;
import com.squareup.picasso.Picasso;

public class HomeActivity extends ActivityBase {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,
                R.id.nav_profile,R.id.nav_change_pass, R.id.nav_financial,
                R.id.nav_change_country, R.id.nav_lang,R.id.nav_messages,
                R.id.nav_logout).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nameTxt);
        TextView navMobile = headerView.findViewById(R.id.mobileTxt);
        ImageView userImage = headerView.findViewById(R.id.userImage);

        if (UtilityApp.getUserData() != null) {
            MemberModel memberModel = UtilityApp.getUserData();
            navUsername.setText(memberModel.getName());
            navMobile.setText(memberModel.getMobileNumber());
//            Picasso.get().load(memberModel.getProfilePicture()).placeholder(R.drawable.holder_image).error(R.drawable.small_logo_screen).into(userImage);


        }


    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}