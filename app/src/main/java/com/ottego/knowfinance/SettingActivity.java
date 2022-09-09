package com.ottego.knowfinance;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.ottego.knowfinance.Model.AutoLoginModel;
import com.ottego.knowfinance.Model.ModuleSettingModel;
import com.ottego.knowfinance.databinding.ActivitySettingBinding;

import org.json.JSONObject;

public class SettingActivity extends AppCompatActivity {
    public String setting_moduleUrl = Utils.BASEURL + "get/module/setting";
    public String autoLoginUrl = Utils.BASEURL + "get/auto/login/setting";

    ActivitySettingBinding b;
    Context context;
    ModuleSettingModel model;
    AutoLoginModel autoLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = SettingActivity.this;
        getData();
        getLoginData();
        listener();
    }

    private void getLoginData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                autoLoginUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response setting", String.valueOf(response));
                Gson gson = new Gson();
                if (response != null) {
                    autoLoginModel = gson.fromJson(String.valueOf(response), AutoLoginModel.class);
                    setCredentials();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(jsonObjectRequest);
    }

    private void setCredentials() {
        b.etApiKey.setText(autoLoginModel.api_key);
        b.etSecretKey.setText(autoLoginModel.api_secret);
        b.etUserId.setText(autoLoginModel.user_id);
        b.etAddUserPassword.setText(autoLoginModel.user_pwd);
        b.etAddUserOTPKey.setText(autoLoginModel.totp_key);
    }


    public void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                setting_moduleUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response setting", String.valueOf(response));
                Gson gson = new Gson();
                if (response != null) {
                    model = gson.fromJson(String.valueOf(response), ModuleSettingModel.class);
                    setSetting();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(jsonObjectRequest);
    }

    private void setSetting() {
        if (model.first_module.equalsIgnoreCase("Enable")) {
            b.rbEnableModule.setChecked(true);
        } else {
            b.rbFirstModule.setChecked(true);
        }


        if (model.heikin_ashi.equalsIgnoreCase("Enable")) {
            b.rbSecondModule.setChecked(true);
        } else {
            b.rbSecondModule1.setChecked(true);
        }

    }


    private void listener() {
        b.mtbSettingFinance.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}