package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ottego.knowfinance.databinding.ActivityPricingBinding;

public class PricingActivity extends AppCompatActivity {
ActivityPricingBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityPricingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


    }


}