package com.ottego.knowfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ottego.knowfinance.databinding.ActivityRegistationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    ActivityRegistationBinding b;
    Context context;
    String registerUrl = "register";
    String firstName = "";
    String lastName = "";
    String email = "";
    String gender = "";
    String phone = "";
    String password = "";

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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

        b.mbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    submitForm();
                    hideKeyboard();
                }
            }
        });

        b.rgRegisterGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mrbRegisterMale:
                        gender = "male";
                        break;

                    case R.id.mrbRegisterFemale:
                        gender = "female";
                        break;
                }
            }
        });
    }


    public void hideKeyboard() {
        // Check if no view has focus:
        View view = RegistrationActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean checkForm() {
        firstName = b.etRegisterFirstName.getText().toString().trim();
        lastName = b.etRegisterLastName.getText().toString().trim();
        phone = b.etRegisterMobile.getText().toString().trim();
        email = b.etRegisterEmail.getText().toString().trim();
        password = b.etRegisterPassword.getText().toString().trim();



        if (gender.isEmpty()) {
            Toast.makeText(context, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (firstName.isEmpty()) {
            b.etRegisterFirstName.setError("Please enter your first name");
            b.etRegisterFirstName.setFocusableInTouchMode(true);
            b.etRegisterFirstName.requestFocus();
            return false;
        } else {
            b.etRegisterFirstName.setError(null);
        }

        if (lastName.isEmpty()) {
            b.etRegisterLastName.setError("Please enter your last name");
            b.etRegisterLastName.setFocusableInTouchMode(true);
            b.etRegisterLastName.requestFocus();
            return false;
        } else {
            b.etRegisterLastName.setError(null);
        }

        if (phone.isEmpty()) {
            b.etRegisterMobile.setError("Please enter mobile number");
            b.etRegisterMobile.setFocusableInTouchMode(true);
            b.etRegisterMobile.requestFocus();
            return false;
        } else if (!Utils.isValidMobile(phone)) {
            b.etRegisterMobile.setError("Invalid mobile no.");
            b.etRegisterMobile.setFocusableInTouchMode(true);
            b.etRegisterMobile.requestFocus();
            return false;
        } else {
            b.etRegisterMobile.setError(null);
        }

        if (email.isEmpty()) {
            b.etRegisterEmail.setError("Please email id");
            b.etRegisterEmail.setFocusableInTouchMode(true);
            b.etRegisterEmail.requestFocus();
            return false;
        } else if (!Utils.isValidEmail(email)) {
            b.etRegisterEmail.setError("Invalid email.");
            b.etRegisterEmail.setFocusableInTouchMode(true);
            b.etRegisterEmail.requestFocus();
            return false;
        } else {
            b.etRegisterEmail.setError(null);
        }

            if (password.isEmpty()) {
                b.etRegisterPassword.setError("Please enter your password");
                b.etRegisterPassword.setFocusableInTouchMode(true);
                b.etRegisterPassword.requestFocus();
                return false;
            } else if (password.length() < 6) {
                b.etRegisterPassword.setError("password must be at least 6 character long");
                b.etRegisterPassword.setFocusableInTouchMode(true);
                b.etRegisterPassword.requestFocus();
                return false;
            } else {
                b.etRegisterPassword.setError(null);
            }

        return true;
    }

    private void submitForm() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("email", email);
        params.put("gender", gender);
        params.put("phone", phone);
//        params.put("role", Utils.role_user);

        Log.e("params", String.valueOf(params));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registerUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", String.valueOf((response)));
                        try {
                            String code = response.getString("result");
                            if (code.equalsIgnoreCase("1")) {
//                                    Gson gson = new Gson();
//                                    UserModel sessionModel = gson.fromJson(String.valueOf((response)), UserModel.class);
//                                   // sessionManager.createSUserDetails(sessionModel);
                                // Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();  // sessionManager.createSessionLogin(userId);
                                Intent intent = new Intent(context, OtpVerificationActivity.class);
                                intent.putExtra("mobile", phone);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                            Log.e("Error response", String.valueOf(error));
                        }
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(request);
    }


}