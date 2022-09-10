package com.ottego.knowfinance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ottego.knowfinance.Model.AutoLoginModel;
import com.ottego.knowfinance.Model.ModuleSettingModel;
import com.ottego.knowfinance.databinding.ActivitySettingBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    public String get_setting_moduleUrl = Utils.BASEURL + "get/module/setting";
    public String get_autoLoginUrl = Utils.BASEURL + "get/auto/login/setting";
    public String setting_moduleUrl = Utils.BASEURL + "add/module/setting";
    public String loginUrl = Utils.BASEURL + "add/auto/login/setting";


    ActivitySettingBinding b;
    Context context;
    ModuleSettingModel model;
    AutoLoginModel autoLoginModel;
//Login Credential variable
    String api_key="";
    String api_secret="";
    String user_id="";
    String user_pwd="";
    String otp_key="";

    //Module Setting Variable
    String module_id="";
    String status="";

    String module_id1="";
    String status1="";



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
                get_autoLoginUrl, null, new Response.Listener<JSONObject>() {
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
                get_setting_moduleUrl, null, new Response.Listener<JSONObject>() {
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
            b.rbFirstModuleDisable.setChecked(true);
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

        b.btnLoginSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    submitForm();
                    //hideKeyboard();
                }
            }
        });


        b.rgModule.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbEnableModule:
                        module_id="1";
                        status = "Enable";
                        setModuleFirst();
                        break;

                    case R.id.rbFirstModuleDisable:
                        module_id="1";
                        status = "Disable";
                        setModuleFirst();
                        break;
                }
            }
        });


        b.rgModule2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbSecondModule:
                        module_id="2";
                        status = "Enable";
                        setModuleFirst();
                        break;

                    case R.id.rbSecondModule1:
                        module_id="2";
                        status = "Disable";
                        setModuleFirst();
                        break;
                }
            }
        });
    }

    private void setModuleFirst() {
            final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, setting_moduleUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.e("response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String code = jsonObject.getString("results");
                        if (code.equalsIgnoreCase("1")) {
                            Toast.makeText(context, "Module Setting"+" "+status, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,  "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            error.printStackTrace();
                            Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("module_id",module_id);
                    params.put("status", status);
                    Log.e("params", String.valueOf(params));
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
        }

    //check form.....
    private boolean checkForm() {
        api_key = b.etApiKey.getText().toString().trim();
        api_secret = b.etSecretKey.getText().toString().trim();
        user_id = b.etUserId.getText().toString().trim();
        user_pwd = b.etAddUserPassword.getText().toString().trim();
        otp_key = b.etAddUserOTPKey.getText().toString().trim();


        //Api Key Check
        if (api_key.isEmpty()) {
            b.etApiKey.setError("Please enter Api Key");
            b.etApiKey.setFocusableInTouchMode(true);
            b.etApiKey.requestFocus();
            return false;
        }else {
            b.etApiKey.setError(null);
        }


        //Api Secret Check
        if (api_secret.isEmpty()) {
            b.etSecretKey.setError("Please enter Api Secret");
            b.etSecretKey.setFocusableInTouchMode(true);
            b.etSecretKey.requestFocus();
            return false;
        }else {
            b.etSecretKey.setError(null);
        }

//Api UserID Check
        if (user_id.isEmpty()) {
            b.etUserId.setError("Please enter User ID");
            b.etUserId.setFocusableInTouchMode(true);
            b.etUserId.requestFocus();
            return false;
        }else {
            b.etUserId.setError(null);
        }


//Api Secret Check
        if (user_pwd.isEmpty()) {
            b.etAddUserPassword.setError("Please enter User Password");
            b.etAddUserPassword.setFocusableInTouchMode(true);
            b.etAddUserPassword.requestFocus();
            return false;
        }else {
            b.etAddUserPassword.setError(null);
        }

//Api OTP Check
        if (otp_key.isEmpty()) {
            b.etAddUserOTPKey.setError("Please enter OTP Key");
            b.etAddUserOTPKey.setFocusableInTouchMode(true);
            b.etAddUserOTPKey.requestFocus();
            return false;
        }else {
            b.etAddUserOTPKey.setError(null);
        }

        return true;



    }
    //send request to server for login using OTP
    private void submitForm() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("results");
                    if (code.equals("1")) {
                        Toast.makeText(context, "Credentials Saved Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context,  "Credentials not saved Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key",api_key);
                params.put("api_secret", api_secret);
                params.put("user_id",user_id);
                params.put("user_pwd", user_pwd);
                params.put("totp_key", otp_key);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}