package com.ottego.knowfinance;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.ottego.knowfinance.Adapter.StockAdapter;
import com.ottego.knowfinance.Model.DataModelStockDetails;
import com.ottego.knowfinance.Model.DataModelStockList;
import com.ottego.knowfinance.databinding.ActivityAddStockInfoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStockInfoActivity extends AppCompatActivity implements ApiListener {
    //url of get Stock_name
    public String stock_name_url = Utils.BASEURL + "get/stock";
    public String stock_Details_url = Utils.BASEURL + "get/saved/stock";
    public String stock_add_url = Utils.BASEURL + "add/stock/details";

    ActivityAddStockInfoBinding binding;
    Context context;
    //  Module name
    String[] modules = {"Select Module", "1st Module", "Heikin Ashi"};

    //  Type name
    String[] type = {"Select Type", "Cash", "Future"};

    //ArrayList of Stock_name
    ArrayList<String> Stock_list = new ArrayList<>();

    //Adapter of stock_name
    ArrayAdapter<String> stockAdapter;

    //model of stock_list
    DataModelStockList modelStockList;

    //model of stock_details
    DataModelStockDetails dataModelStockDetails;

    ArrayList<String> religionList = new ArrayList<>();
    ArrayAdapter<String> religionAdapter;
    ListView listView;
    Dialog dialog;
    String original_value;
    String module;
    String stock_type;
    String quantity;
    String stock_name;
    int mCounter = 0;
    String count;
    boolean module_click = true;
    boolean type_click = true;
    boolean stock_click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStockInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = AddStockInfoActivity.this;

        //ArrayAdapter of module
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, modules);
        binding.spModule.setAdapter(adapter);

        //ArrayAdapter of Type
        ArrayAdapter<String> type_adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, type);
        binding.spType.setAdapter(type_adapter);


        //call stock_list_data Api
        getStock_name_List();

        //call stock details
        getData();

        // Initialize dialog
        dialog = new Dialog(context);




        listener();
    }


    private void listener() {

        // Back on click back button
        binding.mtbAddStockFinance.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

// click listener on module spinner
        binding.spModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String createdBy = binding.spModule.getSelectedItem().toString();

                if(binding.spModule.getSelectedItem().toString().equalsIgnoreCase("1st Module")){
                    module = "FirstModule";
                }else{
                    module = "Heikin Ashi";
                }

                if (module_click) {
                    module_click = false;
                } else {
                    if (position == 0) {
                        Toast.makeText(AddStockInfoActivity.this, "Please select Module!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddStockInfoActivity.this, modules[position] + " Selected !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

// click listener on Type spinner
        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String createdBy = binding.spType.getSelectedItem().toString();

                if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Future")) {
                    binding.spStock.setPrompt("Select Stock name");
                }

                if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Cash")) {
                    binding.tvQuantity.setText("1");
                }

                if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Select Type")) {
                    binding.tvQuantity.setText("");
                }

                if (type_click) {
                    type_click = false;
                } else {
                    if (position == 0) {
                        Toast.makeText(AddStockInfoActivity.this, "Please select Type!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddStockInfoActivity.this, type[position] + " Selected !", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        // click listener on stock list spinner

        binding.spStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String createdBy = binding.spStock.getSelectedItem().toString();
                original_value = (modelStockList.data.get(position).lot_size);
                stock_name = (modelStockList.data.get(position).id);

                if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Future")) {
                    binding.tvQuantity.setText(modelStockList.data.get(position).lot_size);
                } else if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Cash")) {
                    binding.tvQuantity.setText("1");
                } else if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Select Type")) {
                    binding.tvQuantity.setText("");
                } else {
                    binding.tvQuantity.setText(modelStockList.data.get(position).lot_size);
                }
                Log.e("stock_size", modelStockList.data.get(position).lot_size);

                if (stock_click) {
                    stock_click = false;
                } else {
                    if (position == 0) {
                        Toast.makeText(AddStockInfoActivity.this, "Select Stock Name", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        binding.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCounter++;
                if (binding.spType.getSelectedItem().equals("Cash")) {

                    binding.tvQuantity.setText(Integer.toString(mCounter));
                } else {
                    int value = Integer.parseInt(count) + Integer.parseInt(String.valueOf(original_value));
                    binding.tvQuantity.setText(Integer.toString(value));

                }

            }
        });


        binding.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCounter--;
                if (binding.spType.getSelectedItem().equals("Cash")) {

                    binding.tvQuantity.setText(Integer.toString(mCounter));
                } else {
                    int value = Integer.parseInt(count) - Integer.parseInt(String.valueOf(original_value));
                    binding.tvQuantity.setText(Integer.toString(value));
                }

            }
        });


//        global val=firstTimeVisit


        binding.tvQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                count = binding.tvQuantity.getText().toString();
                if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("Cash")) {
                    if (count.equalsIgnoreCase("1")) {
                        binding.decrease.setVisibility(View.GONE);
                    } else {
                        binding.decrease.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (count.equalsIgnoreCase(String.valueOf(original_value))) {
                        binding.decrease.setVisibility(View.GONE);
                    } else {
                        binding.decrease.setVisibility(View.VISIBLE);
                    }
                }

                if(!count.isEmpty() && !count.equalsIgnoreCase(""))
                {
                    binding.llIcreaseDecrease.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.mbSaveStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    submitForm();
                }
            }
        });

    }

    private boolean checkForm() {

        quantity = binding.tvQuantity.getText().toString().trim();
        stock_type= binding.spType.getSelectedItem().toString().trim();


        if (binding.spModule.getSelectedItem().toString().trim().contains("Select Module")) {
//                Toast.makeText(context, "Please Select Profile Created by ", Toast.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Module", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        if (binding.spType.getSelectedItem().toString().trim().contains("Select Type")) {
//                Toast.makeText(context, "Please Select Profile Created by ", Toast.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Type", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        if (quantity.isEmpty()) {
            binding.tvQuantity.setError("Please fill Quantity");
            binding.tvQuantity.setFocusableInTouchMode(true);
            binding.tvQuantity.requestFocus();
            return false;
        } else {
            binding.tvStock.setError(null);
        }
        return true;
    }

    private void getStock_name_List() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                stock_name_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response stock", String.valueOf(response));
                Gson gson = new Gson();
                try {
                    if (response != null) {
                        modelStockList = gson.fromJson(String.valueOf(response), DataModelStockList.class);
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String stock_name = jsonObject.getString("stock_name");
                            Stock_list.add(stock_name);

                            //  Log.e("Religion-list", String.valueOf(religionList));
                        }

                    }
                    stockAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item, Stock_list);
                    // set adapter
                    binding.spStock.setAdapter(stockAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(jsonObjectRequest);

        // listView.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);
    }
    public void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                stock_Details_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response stock", String.valueOf(response));
                Gson gson = new Gson();
                if (response != null) {
                    dataModelStockDetails = gson.fromJson(String.valueOf(response), DataModelStockDetails.class);
                    setRecyclerView();

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

    private void setRecyclerView() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.rvStockDetailsTable.setLayoutManager(layoutManager);
        binding.rvStockDetailsTable.setHasFixedSize(true);
        binding.rvStockDetailsTable.setNestedScrollingEnabled(true);
        StockAdapter stockAdapter = new StockAdapter(context, dataModelStockDetails.data, this);
        binding.rvStockDetailsTable.setAdapter(stockAdapter);

    }


    private void submitForm() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, stock_add_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("results");
                    if (code.equalsIgnoreCase("1")) {
                         Toast.makeText(context, "Stock Added Successfully", Toast.LENGTH_SHORT).show();
                         getData();
//                        Intent intent = new Intent(context, LoginActivity.class);
                        //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {
                       // Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("module", module);
                params.put("type", stock_type);
                params.put("stock", stock_name);
                params.put("quantity", quantity);
                Log.e("stock params", String.valueOf(params));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    @Override
    public void onSuccess(int position) {
        getData();
    }

    @Override
    public void onFail(int position) {

    }
}




