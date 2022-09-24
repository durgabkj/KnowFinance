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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.ottego.knowfinance.Model.RegistrationModel;
import com.ottego.knowfinance.Model.SessionModel;
import com.ottego.knowfinance.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// created by Durga on 20/08/22
public class LoginActivity extends AppCompatActivity {
    //Activity Binding
    ActivityLoginBinding b;
    String url = Utils.BASEURL + "login/";
    Context context;
    SessionManager sessionManager;
    String username;
    String password;
    String token;
    SessionModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        // return view of activity
        setContentView(b.getRoot());
        context = LoginActivity.this;
        sessionManager = new SessionManager(context);
        listener();


    }

    //Write click function under the listener...
    private void listener() {
        b.btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go one Activity to Another Activity through using this code
                Intent intent = new Intent(context, RegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        b.tvForgetPassLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ForgetPasswordActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (checkForm()) {
                    submitForm();
                }
            }
        });
    }

    //Check Validation during Login...
    private boolean checkForm() {
        username = b.etLoginUserName.getText().toString().trim();
        password = b.etLoginPass.getText().toString().trim();

        if (username.isEmpty()) {
            b.etLoginUserName.setError("Please enter Username");
            b.etLoginUserName.setFocusableInTouchMode(true);
            b.etLoginUserName.requestFocus();
            return false;
        }  else {
            b.etLoginUserName.setError(null);
        }
        if (password.length() < 6) {
            b.etLoginPass.setError("Password must be at least 6 characters long");
            b.etLoginPass.setFocusableInTouchMode(true);
            b.etLoginPass.requestFocus();
            return false;
        } else {
            b.etLoginPass.setError(null);
        }
        return true;
    }

    //Hide keyboard Automatic after click on login button
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = LoginActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //    submit credential for login
    private void submitForm() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "checking credential please wait....", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", String.valueOf((response)));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("results");
                    if (code.equalsIgnoreCase("1")) {
                        Gson gson = new Gson();
                        model = gson.fromJson(String.valueOf(response), SessionModel.class);
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login Successful", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Intent intent = new Intent(context, FingerAuthenticationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        sessionManager.createSession(model);
                    } else {
                        //Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Sorry, something went wrong. Please try again.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    error.printStackTrace();
                    Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("token", sessionManager.getUserToken());
                Log.e("params", String.valueOf(params));
                return params;
            }
        } ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}