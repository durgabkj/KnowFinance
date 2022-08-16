package com.ottego.knowfinance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.ottego.knowfinance.Model.PricingModel;
import com.ottego.knowfinance.databinding.ActivityLandingBinding;

import java.util.Objects;

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

        listener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.module, menu);
        return true;
    }

    private void listener() {
        b.mtbLandingFinance.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_top_add) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View layout_dialog1= LayoutInflater.from(context).inflate(R.layout.layout_module,null);
                    builder.setView(layout_dialog1);

                    MaterialButton ok =layout_dialog1.findViewById(R.id.mbModule);
                    // show dialog

                    AlertDialog dialog=builder.create();
                    dialog.show();
                    dialog.setCancelable(false);

                    dialog.getWindow().setGravity(Gravity.CENTER);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });
                }
                return false;
            }
        });



        b.mbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,LoginActivity.class);
                startActivity(intent);
            }
        });

        b.mbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,RegistrationActivity.class);
                startActivity(intent);
            }
        });



        b.nvHeaderLanding.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

                if (id == R.id.nav_Home) {
                    Intent intent = new Intent(context, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_About) {
                    Intent intent = new Intent(context, AboutUsActivity.class);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }


                if (id == R.id.nav_Setting) {
                    Intent intent = new Intent(context, SettingActivity.class);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Stock) {
                    Intent intent = new Intent(context, AddStockInfoActivity.class);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Chart) {
                    Intent intent = new Intent(context, LandingActivity.class);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Service) {
                    Intent intent = new Intent(context, ServiceActivity.class);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (id == R.id.nav_Contact) {
                    Intent intent = new Intent(context, ContactUsActivity.class);
                    startActivity(intent);
                    b.dlLanding.closeDrawer(GravityCompat.START);
                    return true;
                }


//                if (id == R.id.nav_Pricing) {
//                    Intent intent = new Intent(context, Pricing.class);
//                    startActivity(intent);
//                    b.dlLanding.closeDrawer(GravityCompat.START);
//                    return true;
//                }

                return false;
            }
        });

    }




}