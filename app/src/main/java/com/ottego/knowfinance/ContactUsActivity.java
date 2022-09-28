package com.ottego.knowfinance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ottego.knowfinance.Model.RegistrationModel;
import com.ottego.knowfinance.databinding.ActivityContactUsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {
    ActivityContactUsBinding binding;
    String contactUrl = Utils.BASEURL+"save/contact";
    Context context;
    String name = "";
    String email = "";
    String subject = "";
    String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = ContactUsActivity.this;
        listener();
    }

    private void listener() {
        binding.btnSendComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    submitForm();
                    hideKeyboard();
                }
            }
        });

        binding.mtbContactFinance.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = ContactUsActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean checkForm() {
        name = binding.tvContactName.getText().toString().trim();
        email = binding.tvContactEmail.getText().toString().trim();
        subject = binding.tvContactSubject.getText().toString().trim();
        message = binding.etAddUserMessage.getText().toString().trim();


        if (name.isEmpty()) {
            binding.tvContactName.setError("Please enter your name");
            binding.tvContactName.setFocusableInTouchMode(true);
            binding.tvContactName.requestFocus();
            return false;
        } else {
            binding.tvContactName.setError(null);
        }

        if (email.isEmpty()) {
            binding.tvContactEmail.setError("Please enter your last name");
            binding.tvContactEmail.setFocusableInTouchMode(true);
            binding.tvContactEmail.requestFocus();
            return false;
        } else if (!Utils.isValidEmail(email)) {
            binding.tvContactEmail.setError("Invalid email.");
            binding.tvContactEmail.setFocusableInTouchMode(true);
            binding.tvContactEmail.requestFocus();
            return false;
        } else {
            binding.tvContactEmail.setError(null);
        }

        if (subject.isEmpty()) {
            binding.tvContactSubject.setError("Please enter mobile number");
            binding.tvContactSubject.setFocusableInTouchMode(true);
            binding.tvContactSubject.requestFocus();
            return false;
        } else {
            binding.tvContactSubject.setError(null);
        }

        if (message.isEmpty()) {
            binding.etAddUserMessage.setError("Please email id");
            binding.etAddUserMessage.setFocusableInTouchMode(true);
            binding.etAddUserMessage.requestFocus();
            return false;
        } else {
            binding.etAddUserMessage.setError(null);
        }

        return true;
    }

    private void submitForm() {
//        params.put("role", Utils.role_user);
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, contactUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("results");
                    if (code.equalsIgnoreCase("1")) {
                        Gson gson = new Gson();
                        Toast.makeText(context, "Your comments send successfully to customer support", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DashBoardActivity.class);
                        startActivity(intent);
                        //sessionManager.createSessionToken(model.token);

                    } else {
                        Toast.makeText(context, "Try again...", Toast.LENGTH_SHORT).show();
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
                params.put("name", name);
                params.put("email", email);
                params.put("subject", subject);
                params.put("descriptions", message);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }


}