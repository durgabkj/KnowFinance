package com.ottego.knowfinance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
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
import com.ottego.knowfinance.Adapter.BackTestAdapter;
import com.ottego.knowfinance.Adapter.SliderAdapter;
import com.ottego.knowfinance.Adapter.StockAdapter;
import com.ottego.knowfinance.Model.BackTestTradeListModel;
import com.ottego.knowfinance.Model.DataModelBackTest;
import com.ottego.knowfinance.Model.DataModelBackTestTradeList;
import com.ottego.knowfinance.Model.DataModelStockDetails;
import com.ottego.knowfinance.Model.DataModelStockList;
import com.ottego.knowfinance.Model.SliderModel;
import com.ottego.knowfinance.databinding.ActivityBackTestingBinding;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BackTestingActivity extends AppCompatActivity {
    static ActivityBackTestingBinding b;
    static String dateFromConver;
    final Calendar myCalendar = Calendar.getInstance();
    public int count;
    public String backTestUrl = Utils.BASEURL + "get/backtest";
    public String stock_name_url = Utils.BASEURL + "get/stock";
    Context context;
    //ArrayList of Stock_name
    ArrayList<String> Stock_list = new ArrayList<>();

    DataModelBackTestTradeList model;
    String fromDate;
    String toDate;
    String stock_type;
    //model of stock_list
    DataModelStockList modelStockList;


    //model of stock_details
    DataModelBackTest dataModelBackTest;
    //Adapter of stock_name
    ArrayAdapter<String> stockAdapter;

    String module;
    boolean module_click = true;

    //  Module name
    String[] modules = {"Select Module", "1st Module", "Heikin Ashi"};
    String quantity;
    // Urls of our images.
    String url1 = "https://dqvh7oj3vu3ch.cloudfront.net/720x,webp/fxmedia.s3.amazonaws.com/articles/How_to_backtest_a_trading_strategy.jpg";
    String url2 = "https://www.dhanistocks.com/blog/wp-content/uploads/2019/05/Important-functions-of-stock-market.jpg";
    String url3 = "https://tradingstrategyguides.com/wp-content/uploads/2018/03/backtest-trading-strategy.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityBackTestingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        context = BackTestingActivity.this;

        b.rvOverView.setVisibility(View.VISIBLE);
        b.rvPerformance.setVisibility(View.GONE);
        b.rvListOfTrade.setVisibility(View.GONE);

        //ArrayAdapter of module
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, modules);
        b.spModule.setAdapter(adapter);

        listener();
        imageSlider();
        //call stock_list_data Api
        getStock_name_List();
        moduleSpinner();
    }

    private void moduleSpinner() {
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Module, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        b.spModule.setAdapter(adapter);
    }

    private void imageSlider() {

        // we are creating array list for storing our image urls.
        ArrayList<SliderModel> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderModel(url1));
        sliderDataArrayList.add(new SliderModel(url2));
        sliderDataArrayList.add(new SliderModel(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();
    }


    private void listener() {
        b.mbtnBackTestSearchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    submitForm();
                }
            }
        });


        b.etFromAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!b.etFromAge.getText().toString().trim().equalsIgnoreCase("")) {

                    b.tvto.setVisibility(View.VISIBLE);
                    b.lldateto.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        b.etFromAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTruitonDatePickerDialog(view);
            }
        });
        b.etToAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToDatePickerDialog(view);
            }
        });


        // click listener on module spinner
        b.spModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String createdBy = b.spModule.getSelectedItem().toString();

                if (b.spModule.getSelectedItem().toString().equalsIgnoreCase("1st Module")) {
                    module = "FirstModule";
                } else {
                    module = "Heikin Ashi";
                }

                if (module_click) {
                    module_click = false;
                } else {
                    if (position == 0) {
                        Toast.makeText(context, "Please select Module!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, modules[position] + " Selected !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        b.mtbBackTest.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        b.personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.rvOverView.setVisibility(View.VISIBLE);
                b.rvPerformance.setVisibility(View.GONE);
                b.rvListOfTrade.setVisibility(View.GONE);
                b.personalinfobtn.setTextColor(getResources().getColor(R.color.appColor));
                b.experiencebtn.setTextColor(getResources().getColor(R.color.light_gray));
                b.reviewbtn.setTextColor(getResources().getColor(R.color.light_gray));

            }
        });

        b.experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.rvOverView.setVisibility(View.GONE);
                b.rvPerformance.setVisibility(View.VISIBLE);
                b.rvListOfTrade.setVisibility(View.GONE);
                b.personalinfobtn.setTextColor(getResources().getColor(R.color.light_gray));
                b.experiencebtn.setTextColor(getResources().getColor(R.color.appColor));
                b.reviewbtn.setTextColor(getResources().getColor(R.color.light_gray));

            }
        });

        b.reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.rvOverView.setVisibility(View.GONE);
                b.rvPerformance.setVisibility(View.GONE);
                b.rvListOfTrade.setVisibility(View.VISIBLE);
                b.personalinfobtn.setTextColor(getResources().getColor(R.color.light_gray));
                b.experiencebtn.setTextColor(getResources().getColor(R.color.light_gray));
                b.reviewbtn.setTextColor(getResources().getColor(R.color.appColor));

            }
        });


        b.spModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText(getApplicationContext(), "Selected:" + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        b.spStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                b.etQuantity.setText(modelStockList.data.get(i).lot_size);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void getStock_name_List() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                stock_name_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response stock name", String.valueOf(response));
                Gson gson = new Gson();
                try {
                    if (response != null) {
                        modelStockList = gson.fromJson(String.valueOf(response), DataModelStockList.class);
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String stock_name = jsonObject.getString("stock_name");
                            Stock_list.add(stock_name);

                            Log.e("Stock-list", String.valueOf(Stock_list));
                        }

                    }
                    stockAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item, Stock_list);
                    // set adapter
                    b.spStock.setAdapter(stockAdapter);
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


    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean checkForm() {

        module = b.spModule.getSelectedItem().toString().trim();
        stock_type = b.spStock.getSelectedItem().toString().trim();

        fromDate = b.etFromAge.getText().toString().trim();
        toDate = b.etToAge.getText().toString().trim();

        quantity = b.etQuantity.getText().toString().trim();


        if (b.spModule.getSelectedItem().toString().trim().contains("Select Module")) {
//                Toast.makeText(context, "Please Select Profile Created by ", Toast.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Module", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        if (b.spStock.getSelectedItem().toString().trim().contains("Select Type")) {
//                Toast.makeText(context, "Please Select Profile Created by ", Toast.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Type", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        if (fromDate.isEmpty()) {
            b.etFromAge.setError("Please fill from date");
            b.etFromAge.setFocusableInTouchMode(true);
            b.etFromAge.requestFocus();
            return false;
        } else {
            b.etFromAge.setError(null);
        }

        if (toDate.isEmpty()) {
            b.etToAge.setError("Please fill to date");
            b.etToAge.setFocusableInTouchMode(true);
            b.etToAge.requestFocus();
            return false;
        } else {
            b.etToAge.setError(null);
        }


        if (quantity.isEmpty()) {
            b.etQuantity.setError("Please fill Quantity");
            b.etQuantity.setFocusableInTouchMode(true);
            b.etQuantity.requestFocus();
            return false;
        } else {
            b.etQuantity.setError(null);
        }
        return true;
    }

    private void submitForm() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, backTestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response BackTest", response);
                if (response != null) {
                    b.llRecord.setVisibility(View.VISIBLE);
                    Gson gson = new Gson();
                    model = gson.fromJson(String.valueOf(response), DataModelBackTestTradeList.class);
                    setPerformanceData();
                    setOverViewData();
                    setRecyclerView();
                } else {
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
                params.put("dateFrom", fromDate);
                params.put("dateTo", toDate);
                params.put("stock", stock_type);
                params.put("quantity", quantity);
                Log.e("params", String.valueOf(params));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        b.rvListOfTrade.setLayoutManager(layoutManager);
        b.rvListOfTrade.setHasFixedSize(true);
        b.rvListOfTrade.setNestedScrollingEnabled(true);
        BackTestAdapter backTestAdapter = new BackTestAdapter(model.trade_list,context);
        b.rvListOfTrade.setAdapter(backTestAdapter);



    }

    private void setOverViewData() {
        b.tvProfitLossValue.setText(model.net_profit);
        b.tvProfitLossPer.setText(", "+model.profit_percantage +" %");
        b.tvTCloseTrade.setText(model.total_close_trade);
        b.tvTradeProfit.setText(model.net_profit+" %");
        b.tvTradeMaxLossDown.setText("");
        b.tvMaxLossPercentage.setText("");
    }

    private void setPerformanceData() {
        b.tvNetProfit.setText(model.net_profit);
        b.tvGrossProfit.setText(model.gross_profit_main);
        b.tvGrossLoss.setText(model.gross_loss_main);
        b.tvMaxDrawDown.setText("");
        b.tvCommissionPaid.setText(model.commission_paid);
        b.tvCloseTrade.setText(model.total_close_trade);
        b.tvTotalOpen.setText(model.total_open_trade);
        b.tvWinningTradeProfit.setText(model.total_winning_trade);
        b.tvLoosingTrade.setText(model.total_lossing_trade);
        b.tvProfitPercentageOpen.setText(model.net_profit_per+" %");
        b.tvAvgNetPnl.setText(model.avg_pnl);
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(), this, year,
                    month, day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            int mmonths = month + 1;
            dateFromConver = (day + "/" + month + "/" + year);
            Log.e("fromdate",dateFromConver);
            String getfrom[] = dateFromConver.split("/");
            int year1, month1, day1;
            year1 = Integer.parseInt(getfrom[2]);
            month1 = Integer.parseInt(getfrom[1]);
            day1 = Integer.parseInt(getfrom[0]);
            int pmonth = month + 1;
            b.etFromAge.setText(String.format("%d-%02d-%02d ", year1, pmonth, day1));


        }

    }

    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            String getfromdate = dateFromConver;
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day+1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dateTo = (day + "/" + month + "/" + year);
            String getfrom[] = dateTo.split("/");
            int year1, month1, day1;
            year1 = Integer.parseInt(getfrom[2]);
            month1 = Integer.parseInt(getfrom[1]);
            day1 = Integer.parseInt(getfrom[0]);
            int pmonth = month + 1;
            b.etToAge.setText(String.format("%d-%02d-%02d ", year1, pmonth, day1));
            //dateFrom=String.format("%02d-%02d-%d ", day,month,year);
        }
    }

}