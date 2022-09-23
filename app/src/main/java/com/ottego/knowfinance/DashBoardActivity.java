package com.ottego.knowfinance;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.ottego.knowfinance.databinding.ActivityDashBoardBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DashBoardActivity extends AppCompatActivity {
ActivityDashBoardBinding b;
Context context;
    MyReceiver myReceiver = new MyReceiver();
    Handler delayhandler;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private boolean doubleBackToExitPressedOnce;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = DashBoardActivity.this;
        setSupportActionBar(b.mtbDashBoardFinance);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, b.dlMainActivity, b.mtbDashBoardFinance, R.string.navigation_open, R.string.navigation_close);
        b.dlMainActivity.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
       // setDialog();
        dialogShow();
        listener();
    }

    private void dialogShow() {
       Calendar calendar= Calendar.getInstance();
       int currentDay=calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences setting =getSharedPreferences("PREFS",0);
        int lastDay=setting.getInt("day",0);

        if(lastDay!= currentDay){
            SharedPreferences.Editor editor=setting.edit();
            editor.putInt("day",currentDay);
            editor.commit();
            setDialog();
        }
    }


    private void setDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.layout_custom_dialog);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        ImageView btnClose = dialog.findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
                    Intent intent = new Intent(context, BackTestingActivity.class);
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

                if (id == R.id.nav_Stock) {
                    Intent intent = new Intent(context, AddStockInfoActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Trade) {
                    Intent intent = new Intent(context, TradingStatusActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Chart) {
                    Intent intent = new Intent(context, ChartActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_graph) {
                    Intent intent = new Intent(context, TradingStatusActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Profile) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }


                if (id == R.id.nav_Service) {
                    Intent intent = new Intent(context, ServiceActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Contact) {
                    Intent intent = new Intent(context, ContactUsActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (id == R.id.nav_About) {
                    Intent intent = new Intent(context, AboutUsActivity.class);
                    startActivity(intent);
                    b.dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }



    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(myReceiver, filter);

        super.onStart();
    }


    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver);
        super.onStop();
    }
}