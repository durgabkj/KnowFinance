package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ottego.knowfinance.databinding.ActivityLoginBinding;
// created by Durga on 22/07/22
public class LoginActivity extends AppCompatActivity {
    //Activity Binding
ActivityLoginBinding b;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityLoginBinding.inflate(getLayoutInflater());
        // return view of activity
         setContentView(b.getRoot());
         context=LoginActivity.this;
         listener();

    }

    //Write click function under the listener...
    private void listener() {
        b.btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go one Activity to Another Activity through using this code
                Intent intent=new Intent(context, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}