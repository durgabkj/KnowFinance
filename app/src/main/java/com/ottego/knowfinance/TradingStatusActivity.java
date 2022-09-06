package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.ottego.knowfinance.Adapter.StockAdapter;
import com.ottego.knowfinance.Adapter.TradeListAdapter;
import com.ottego.knowfinance.Model.DataModelStockDetails;
import com.ottego.knowfinance.Model.DataModelTradeList;
import com.ottego.knowfinance.databinding.ActivityAddStockInfoBinding;
import com.ottego.knowfinance.databinding.ActivityTradingStatusBinding;

import org.json.JSONObject;

public class TradingStatusActivity extends AppCompatActivity {
ActivityTradingStatusBinding binding;
    public String trade_list = Utils.BASEURL + "get/trade/details";
Context context;
    //model of Trade list
    DataModelTradeList dataModelTradeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTradingStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = TradingStatusActivity.this;

        getData();
    }

    public void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                trade_list, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response stock", String.valueOf(response));
                Gson gson = new Gson();
                if (response != null) {
                    dataModelTradeList = gson.fromJson(String.valueOf(response), DataModelTradeList.class);
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
        binding.rvTradeListTable.setLayoutManager(layoutManager);
        binding.rvTradeListTable.setHasFixedSize(true);
        binding.rvTradeListTable.setNestedScrollingEnabled(true);
        TradeListAdapter tradeListAdapter= new TradeListAdapter(context, dataModelTradeList.data);
        binding.rvTradeListTable.setAdapter(tradeListAdapter);

    }
}