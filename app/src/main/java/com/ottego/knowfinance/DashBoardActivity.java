package com.ottego.knowfinance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
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

        //Code for Show Navigation Drawer Menu...
        setSupportActionBar(b.mtbDashBoardFinance);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, b.dlMainActivity, b.mtbDashBoardFinance, R.string.navigation_open, R.string.navigation_close);
        b.dlMainActivity.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



//
        b.nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

//                if (id == R.id.nav_home) {
//                    Intent intent = new Intent(context, HomeActivity.class);
//                    startActivity(intent);
//                    b.home.closeDrawer(GravityCompat.START);
//                    return true;
//
//                } else if (id == R.id.nav_work) {
//                    Intent intent = new Intent(context, SlideActivity.class);
//                    startActivity(intent);
//                    b.home.closeDrawer(GravityCompat.START);
//                    return true;
//
//                } else if (id == R.id.nav_specialOffer) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("https://mymealdabba.com/site/offers"));
//                    try {
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        intent.setData(Uri.parse("https://mymealdabba.com/site/offers"));
//                    }
//                } else if (id == R.id.nav_bookmark) {
//                    Intent intent = new Intent(context, BookMarkActivity.class);
//                    startActivity(intent);
//                    b.home.closeDrawer(GravityCompat.START);
//                    return true;
//
//                } else if (id == R.id.nav_aboutUs) {
//                    Intent intent = new Intent(context, AboutUs.class);
//                    startActivity(intent);
//                    b.home.closeDrawer(GravityCompat.START);
//                    return true;
//
//                } else if (id == R.id.nav_Registration) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("https://mymealdabba.com/register"));
//                    try {
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        // intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
//                    }
//
//                } else if (id == R.id.nav_connect) {
//                    Intent intent = new Intent(context, ConnectWithActivity.class);
//                    startActivity(intent);
//                    b.home.closeDrawer(GravityCompat.START);
//                    return true;
//
//                } else if (id == R.id.nav_rateUs) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
//                    try {
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
//                    }
//
//                } else if (id == R.id.nav_logout) {
//                    sessionManager.logoutUser();
//
//                } else if (id == R.id.nav_shareApp) {
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    String shareSubText = "\t\n" +
//                            "MyMealDabba (Tiffin Service Listings)";
//                    String shareBodyText = "https://play.google.com/store/apps/details?id=app.com.mymealdabba";
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
//                    startActivity(Intent.createChooser(shareIntent, "Share With"));
//                    return true;
//                }
                return false;
            }
        });

    }
}