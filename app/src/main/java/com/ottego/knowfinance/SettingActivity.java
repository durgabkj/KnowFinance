package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ottego.knowfinance.databinding.ActivityLandingBinding;
import com.ottego.knowfinance.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
ActivitySettingBinding b;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b= ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context=SettingActivity.this;
        listener();
    }

    private void listener() {
        b.mtbSettingFinance.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}