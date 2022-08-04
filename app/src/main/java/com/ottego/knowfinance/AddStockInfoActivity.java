package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ottego.knowfinance.databinding.ActivityAddStockInfoBinding;

public class AddStockInfoActivity extends AppCompatActivity {
ActivityAddStockInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddStockInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}