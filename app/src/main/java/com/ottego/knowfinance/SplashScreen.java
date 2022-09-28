package com.ottego.knowfinance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ottego.knowfinance.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends Activity {
    ActivitySplashScreenBinding b;
    Context context;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = SplashScreen.this;
        sessionManager = new SessionManager(context);/**/
        start();
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLoggedIn()) {
                    startActivity(new Intent(context, FingerAuthenticationActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }

                finish();
               // startActivity(new Intent(context, FingerAuthenticationActivity.class));
            }

        }, 2000);
    }
}
