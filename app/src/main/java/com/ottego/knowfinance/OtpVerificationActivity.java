package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ottego.knowfinance.databinding.ActivityOtpVerificationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpVerificationActivity extends AppCompatActivity {
ActivityOtpVerificationBinding b;
Context context;
    String phone;
    String otpSentUrl =  "otp";
    String otp = "";
    String OtpVerifyUrl = "verify/otp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
//        phone = getIntent().getStringExtra("mobile");
//        EditText mEdit;
//        mEdit = (EditText) findViewById(R.id.edtOtpCode);
//        otp = mEdit.getText().toString().trim();
//
//        binding.tvmobileNo.setText(phone);
        receiveOtp();
        listener();
context=OtpVerificationActivity.this;
otpText();

    }

    private void receiveOtp() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        Log.e("params", String.valueOf(params));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, otpSentUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(" otp receive response", String.valueOf(response));

                        try {
                            String code = response.getString("result");
                            if (code.equalsIgnoreCase("1")) {

                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                //   Intent intent = new Intent(context, LoginActivity.class);
                                //  startActivity(intent);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
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
                        if (null != error.networkResponse) {
                            Log.e("Error Response", String.valueOf(error));
                        }
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(request);

    }

    private void listener() {
        b.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    submitFormOtp();
                }
            }
        });


        b.txtresentotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiveOtp();
            }
        });


    }

    private void otpText() {
        b.firstPinView.requestFocus();

        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

        b.firstPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==4)
                {

                }else
                {
                    Toast.makeText(context, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean checkForm() {
        otp = b.firstPinView.getText().toString().trim();
        if (otp.isEmpty() || otp.length() < 4) {
            b.firstPinView.setError("Please enter OTP number");
            b.firstPinView.setFocusableInTouchMode(true);
            b.firstPinView.requestFocus();
            return false;
        } else {
            b.firstPinView.setError(null);
        }
        return true;
    }

    private void submitFormOtp() {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("phone", phone);
//        params.put("otp", otp);
//        Log.e("VerifyOtp", String.valueOf(params));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, OtpVerifyUrl + otp + "/" + phone, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(" otp verify response", String.valueOf(response));

                        try {
                            String code = response.getString("message");
                            if (code.equalsIgnoreCase("OTP verified success")) {

                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
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
                        if (null != error.networkResponse) {
                            Log.e("Error Response", String.valueOf(error));
                        }
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(request);

    }



}