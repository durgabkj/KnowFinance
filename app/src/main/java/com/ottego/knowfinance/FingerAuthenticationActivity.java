package com.ottego.knowfinance;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.ottego.knowfinance.databinding.ActivityFingerAuthenticationBinding;

import java.util.concurrent.Executor;

public class FingerAuthenticationActivity extends AppCompatActivity {

    private ActivityFingerAuthenticationBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void
    onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFingerAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //create new method to check whether support or not
        checkBioMetricSupported();

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(FingerAuthenticationActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            // this method will automatically call when it is succeed verify fingerprint
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            // this method will automatically call when it is failed verify fingerprint
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //attempt not regconized fingerprint
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        //perform action button only fingerprint
        binding.btnFp.setOnClickListener(view -> {

            // call method launch dialog fingerprint
            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setNegativeButtonText("Cancel");

            //activate callback if it succeed
            biometricPrompt.authenticate(promptInfo.build());
        });

        //perform action button fingerprint with PIN code input
        binding.btnFppin.setOnClickListener(view -> {

            // call method launch dialog fingerprint
            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setDeviceCredentialAllowed(true);

            //activate callback if it succeed
            biometricPrompt.authenticate(promptInfo.build());
        });
    }

    BiometricPrompt.PromptInfo.Builder dialogMetric() {
        //Show prompt dialog
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Log in using your biometric credential");


    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkBioMetricSupported() {

        BiometricManager biometricManager = BiometricManager.from(this);

        String info = "";
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                info = "App can authenticate using biometrics.";
                enableButton(true);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                info = "No biometric features available on this device.";
                enableButton(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                info = "Biometric features are currently unavailable.";
                enableButton(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                info = "Need register at least one finger print";
                enableButton(false, true);
                break;
            default:
                info = "Unknown cause";
                enableButton(false);

        }
        //set message to text view so we can see what happen with sensor device
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), info,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    void enableButton(boolean enable) {
        //just enable or disable button
        binding.btnFp.setEnabled(enable);
        binding.btnFppin.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    void enableButton(boolean enable, boolean enroll) {
        enableButton(enable);

        if (!enroll) return;
        // Prompts the user to create credentials that your app accepts.
        //Open settings to set credential fingerprint or PIN
        final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
        enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL);
        startActivity(enrollIntent);
    }

}