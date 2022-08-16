package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.ottego.knowfinance.databinding.ActivityChartBinding;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {
ActivityChartBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


//        List<CandleEntry> ceList = new ArrayList<>();
//        ceList.add(new CandleEntry(0, 4.62f, 2.02f, 2.70f, 4.13f));
//        ceList.add(new CandleEntry(1, 5.50f, 2.70f, 3.35f, 4.96f));
//        ceList.add(new CandleEntry(2, 5.25f, 3.02f, 3.50f, 4.50f));
//        ceList.add(new CandleEntry(3, 6f,    3.25f, 4.40f, 5.0f));
//        ceList.add(new CandleEntry(4, 5.57f, 2f,    2.80f, 4.5f));
//
//
//        CandleDataSet cds = new CandleDataSet(ceList, "Entries");
//        cds.setColor(Color.rgb(80, 80, 80));
//        cds.setShadowColor(Color.DKGRAY);
//        cds.setShadowWidth(0.7f);
//        cds.setDecreasingColor(Color.RED);
//        cds.setDecreasingPaintStyle(Paint.Style.FILL);
//        cds.setIncreasingColor(Color.rgb(122, 242, 84));
//        cds.setIncreasingPaintStyle(Paint.Style.STROKE);
//        cds.setNeutralColor(Color.BLUE);
//        cds.setValueTextColor(Color.RED);
//        CandleData cd = new CandleData(cds);
//        b.CandleStickChart.setData(cd);
//        b.CandleStickChart.invalidate();
    }
}