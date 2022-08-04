package com.ottego.knowfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.ottego.knowfinance.databinding.ActivityDashBoardBinding;

public class DashBoardActivity extends AppCompatActivity {
ActivityDashBoardBinding b;
Context context;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context=DashBoardActivity.this;

        setSupportActionBar(b.mtbDashBoardFinance);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, b.dlMainActivity, b.mtbDashBoardFinance, R.string.navigation_open, R.string.navigation_close);
        b.dlMainActivity.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        listener();
    }

    private void listener() {
        b.nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

                if (id == R.id.nav_DashBoard) {
                    Intent intent = new Intent(context, DashBoardActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_BackTest) {
                    Intent intent = new Intent(context, LandingActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }


                if (id == R.id.nav_Setting) {
                    Intent intent = new Intent(context, SettingActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                return false;
            }
        });
    }

}