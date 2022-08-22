package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.ottego.knowfinance.Adapter.SliderAdapter;
import com.ottego.knowfinance.Model.SliderModel;
import com.ottego.knowfinance.databinding.ActivityBackTestingBinding;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BackTestingActivity extends AppCompatActivity {
ActivityBackTestingBinding b;
    final Calendar myCalendar= Calendar.getInstance();
     int quantity=0;
    // Urls of our images.
    String url1 = "https://dqvh7oj3vu3ch.cloudfront.net/720x,webp/fxmedia.s3.amazonaws.com/articles/How_to_backtest_a_trading_strategy.jpg";
    String url2 = "https://www.dhanistocks.com/blog/wp-content/uploads/2019/05/Important-functions-of-stock-market.jpg";
    String url3 = "https://tradingstrategyguides.com/wp-content/uploads/2018/03/backtest-trading-strategy.jpg";
        public int count;
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

        b.tvincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity =  quantity+Integer.parseInt(b.etQuantity.getText().toString ());
                quantity++;
                updateValue(quantity);
            }
        });

        b.tvdecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // quantity= Integer.parseInt(b.etQuantity.getText().toString().trim());
                quantity=  quantity-Integer.parseInt(b.etQuantity.getText().toString().trim());
                quantity--;
                updateValue(quantity);
            }
        });


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabelTo();
            }
        };
        b.etFromAgePartnerPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BackTestingActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        b.etToAgePartnerPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BackTestingActivity.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
    }

    private void updateValue(int number) {
        b.etQuantity.setText(""+ number);
    }

    private void updateLabel() {
            String myFormat="MM/dd/yyyy";
            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
            b.etFromAgePartnerPreference.setText(dateFormat.format(myCalendar.getTime()));

    }
    private void updateLabelTo() {
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        b.etToAgePartnerPreference.setText(dateFormat.format(myCalendar.getTime()));

    }

}