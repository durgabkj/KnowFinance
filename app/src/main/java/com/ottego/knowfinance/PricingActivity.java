package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ottego.knowfinance.Adapter.PricingAdapter;
import com.ottego.knowfinance.Model.PricingModel;
import com.ottego.knowfinance.databinding.ActivityPricingBinding;

import java.util.ArrayList;
import java.util.List;

public class PricingActivity extends AppCompatActivity {
ActivityPricingBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityPricingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        PricingModel[] myListData = new PricingModel[] {
                new PricingModel("Email", android.R.drawable.ic_dialog_email),
                new PricingModel("Info", android.R.drawable.ic_dialog_info),
                new PricingModel("Delete", android.R.drawable.ic_delete),
                new PricingModel("Dialer", android.R.drawable.ic_dialog_dialer),
                new PricingModel("Alert", android.R.drawable.ic_dialog_alert),
                new PricingModel("Map", android.R.drawable.ic_dialog_map),
                new PricingModel("Email", android.R.drawable.ic_dialog_email),
                new PricingModel("Info", android.R.drawable.ic_dialog_info),
                new PricingModel("Delete", android.R.drawable.ic_delete),
                new PricingModel("Dialer", android.R.drawable.ic_dialog_dialer),
                new PricingModel("Alert", android.R.drawable.ic_dialog_alert),
                new PricingModel("Map", android.R.drawable.ic_dialog_map),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        PricingAdapter adapter = new PricingAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}