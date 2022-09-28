package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ottego.knowfinance.databinding.ActivityMyProfileBinding;

public class MyProfileActivity extends AppCompatActivity {
ActivityMyProfileBinding binding;
SessionManager sessionManager;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
context=MyProfileActivity.this;
sessionManager=new SessionManager(context);
        listener();


        binding.ProfileEmail.setText(sessionManager.getEmail());
        binding.ProfilePhone.setText(sessionManager.getMob());
        binding.ProfileUsername.setText(sessionManager.getUsername());
    }

    private void listener() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


}