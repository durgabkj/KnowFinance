package com.ottego.knowfinance;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.ottego.knowfinance.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {
    ActivityLandingBinding b;
    Context context;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = LandingActivity.this;

        setSupportActionBar(b.mtbLandingFinance);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, b.dlLanding, b.mtbLandingFinance, R.string.navigation_open, R.string.navigation_close);
        b.dlLanding.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}