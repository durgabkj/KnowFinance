package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ottego.knowfinance.databinding.ActivityAddStockInfoBinding;

public class AddStockInfoActivity extends AppCompatActivity {
ActivityAddStockInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddStockInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listener();
    }


    private void listener() {
        binding.mtbAddStockFinance.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}