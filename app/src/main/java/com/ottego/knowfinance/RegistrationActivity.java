package com.ottego.knowfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ottego.knowfinance.databinding.ActivityRegistationBinding;

public class RegistrationActivity extends AppCompatActivity {
    ActivityRegistationBinding b;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityRegistationBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = RegistrationActivity.this;
        listener();
    }

    private void listener() {
        b.tvAlreadyAccRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go one Activity to Another Activity through using this code
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        b.mbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go one Activity to Another Activity through using this code
                Intent intent = new Intent(context, DashBoardActivity.class);
                startActivity(intent);
            }
        });
    }
}