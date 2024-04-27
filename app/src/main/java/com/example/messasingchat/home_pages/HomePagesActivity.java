package com.example.messasingchat.home_pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.messasingchat.Authentication.AuthActivity;
import com.example.messasingchat.R;
import com.example.messasingchat.Services.UserServices.UserServices;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.example.messasingchat.databinding.ActivityHomePagesBinding;
import com.google.android.material.navigation.NavigationView;

public class HomePagesActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomePagesBinding binding;

    private UserServices userServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHomePages.homeToolBar);
        userServices=new UserServices(getApplicationContext());
        binding.appBarHomePages.endIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager.getInstance(getApplicationContext()).clearToken();
                SharedPreferenceManager.getInstance(getApplicationContext()).clearId();
                SharedPreferenceManager.getInstance(getApplicationContext()).clearLogin();
                finishAffinity();
                Intent loginIntent = new Intent(getApplicationContext(), AuthActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);

                finish();

            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_friends, R.id.nav_search,R.id.requests)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_pages);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        try {
            userServices.getUserProfile();

        } catch (Exception e) {
            Log.d("TAG", "onCreate: "+e);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_pages);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}