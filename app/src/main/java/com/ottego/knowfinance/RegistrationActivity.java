package com.ottego.knowfinance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.ottego.knowfinance.Model.RegistrationModel;
import com.ottego.knowfinance.databinding.ActivityRegistationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    ActivityRegistationBinding b;
    Context context;
    String registerUrl = Utils.BASEURL+"register/";
    String firstName = "";
    String lastName = "";
    String email = "";
    String gender = "";
    String username = "";
    String phone = "";
    String password = "";
RegistrationModel model;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityRegistationBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = RegistrationActivity.this;
        sessionManager = new SessionManager(context);

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
        username = b.etRegisterUserName.getText().toString().trim();
        password = b.etRegisterPassword.getText().toString().trim();

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

//        if (gender.isEmpty()) {
//            Toast.makeText(context, "Please select gender", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (username.isEmpty()) {
            b.etRegisterUserName.setError("Please enter username");
            b.etRegisterUserName.setFocusableInTouchMode(true);
            b.etRegisterUserName.requestFocus();
            return false;
        } else {
            b.etRegisterUserName.setError(null);
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
//        params.put("role", Utils.role_user);
            final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.e("response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String code = jsonObject.getString("results");
                        if (code.equalsIgnoreCase("1")) {
                            Gson gson = new Gson();
                            model= gson.fromJson(String.valueOf(response), RegistrationModel.class);

                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("token",model.token);
                            startActivity(intent);
                            sessionManager.createSessionToken(model.token);

                        } else {
                            Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
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
                    params.put("first_name", firstName);
                    params.put("last_name", lastName);
                    params.put("email", email);
                    params.put("username", username);
                    params.put("mobile_no", phone);
                    params.put("password", password);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
        }


}