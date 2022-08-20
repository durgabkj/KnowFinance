package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.ottego.knowfinance.databinding.ActivityChartBinding;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {
ActivityChartBinding b;
    public String HistoricalDataUrl = "http://103.150.186.33:8080/saathidaar_backend/api/request/count/accept-request/";
    WebView webview;
    ProgressDialog pDialog;
    String data;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


//        ArrayList<String> cars = new ArrayList<String>();
//        cars.add("10:00 A.M");
//        cars.add("10:30 A.M");
//        cars.add("11:00 A.M");
//        cars.add("11:30 A.M");
//        cars.add("1:00 A.M");
//        cars.add("2:00 A.M");
//        cars.add("3:00 A.M");
//        cars.add("4:30 A.M");
//        cars.add("5:00 A.M");
//        cars.add("5:30 A.M");

//        List<CandleEntry> ceList = new ArrayList<>();
//        ceList.add(new CandleEntry(0, 4.62f, 2.02f, 2.70f, -4.13f));
//        ceList.add(new CandleEntry(1, 5.50f, 2.70f, 3.35f, 4.96f));
//        ceList.add(new CandleEntry(2, 5.25f, 3.02f, 3.50f, 4.50f));
//        ceList.add(new CandleEntry(3, 6f,    3.25f, 4.40f, 5.0f));
//        ceList.add(new CandleEntry(4, 5.57f, 2f,    -2.80f, 4.5f));
//        ceList.add(new CandleEntry(5, 2.62f, 2.02f, 2.70f, 4.13f));
//        ceList.add(new CandleEntry(6, 4.50f, 6.70f, 3.35f, -4.96f));
//        ceList.add(new CandleEntry(7, 5.25f, 9.02f, 3.50f, 4.50f));
//        ceList.add(new CandleEntry(8, 9f,    3.25f, 4.40f, 5.0f));
//        ceList.add(new CandleEntry(9, 7.57f, 2f,    2.80f, 4.5f));
//
//
//        CandleDataSet cds = new CandleDataSet(ceList, "Entries");
//
//        cds.setColor(Color.rgb(80, 80, 80));
//        cds.setShadowColor(Color.GREEN);
//        cds.setShadowWidth(0.7f);
//        cds.setDecreasingColor(Color.RED);
//        cds.setDecreasingPaintStyle(Paint.Style.FILL);
//        cds.setIncreasingColor(Color.GREEN);
//        cds.setIncreasingPaintStyle(Paint.Style.FILL);
//        cds.setNeutralColor(Color.BLUE);
//        cds.setValueTextColor(Color.RED);
//        cds.setShadowWidth(0.8f);
//        cds.setDrawValues(false);
//
//
//        CandleData cd = new CandleData(cds);
//
//        b.CandleStickChart.setData(cd);
//        b.CandleStickChart.invalidate();
//        b.CandleStickChart.getCandleData();
//        b.CandleStickChart.setBackgroundColor(getResources().getColor(R.color.white));


init();
listener();


    }


    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);

        pDialog = new ProgressDialog(ChartActivity.this);
        pDialog.setTitle("Document");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.OFF);
        webview.setScrollbarFadingEnabled(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.setInitialScale(1);
        webview.setHorizontalScrollBarEnabled(false);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        String url = "https://www.investing.com/equities/livedeal-candlestick";
        webview.loadUrl(url);

    }

    private void listener() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
    }

}