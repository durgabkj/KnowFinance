package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ottego.knowfinance.Adapter.SliderAdapter;
import com.ottego.knowfinance.Model.SliderModel;
import com.ottego.knowfinance.databinding.ActivityBackTestingBinding;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class BackTestingActivity extends AppCompatActivity {
ActivityBackTestingBinding b;

    // Urls of our images.
    String url1 = "https://dqvh7oj3vu3ch.cloudfront.net/720x,webp/fxmedia.s3.amazonaws.com/articles/How_to_backtest_a_trading_strategy.jpg";
    String url2 = "https://www.dhanistocks.com/blog/wp-content/uploads/2019/05/Important-functions-of-stock-market.jpg";
    String url3 = "https://tradingstrategyguides.com/wp-content/uploads/2018/03/backtest-trading-strategy.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityBackTestingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.rvOverView.setVisibility(View.VISIBLE);
        b.rvPerformance.setVisibility(View.GONE);
        b.rvTrade.setVisibility(View.GONE);
        listener();
        imageSlider();
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
        b.mtbBackTest.setNavigationOnClickListener(new View.OnClickListener(){
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
                b.rvTrade.setVisibility(View.GONE);
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
                b.rvTrade.setVisibility(View.GONE);
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
                b.rvTrade.setVisibility(View.VISIBLE);
                b.personalinfobtn.setTextColor(getResources().getColor(R.color.light_gray));
                b.experiencebtn.setTextColor(getResources().getColor(R.color.light_gray));
                b.reviewbtn.setTextColor(getResources().getColor(R.color.appColor));

            }
        });
    }
}