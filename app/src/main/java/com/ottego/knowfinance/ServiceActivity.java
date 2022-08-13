package com.ottego.knowfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.ottego.knowfinance.databinding.ActivityServiceBinding;

public class ServiceActivity extends AppCompatActivity {
ActivityServiceBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        String yourText = " 1. Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n\n" +
                "2. Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae \n\n" +
                "3. sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec \n\n" +
                "4. semper mi et euismod tempor. Sed sodales eleifend mi id varius. Nam \n \n" +
                "5. et ornare enim, sit amet gravida sapien. Quisque gravida et enim vel \n\n" +
                "6. volutpat. Vivamus egestas ut felis a blandit. Vivamus fringilla \n \n" +
                "7. dignissim mollis. Maecenas imperdiet interdum hendrerit. Aliquam \n \n" +
                "8. dictum hendrerit ultrices. Ut vitae vestibulum dolor. Donec auctor ante \n \n" +
                "9. eget libero molestie porta. Nam tempor fringilla ultricies. Nam sem \n \n" +
                "10. lectus, feugiat eget ullamcorper vitae, ornare et sem. Fusce dapibus ipsum \n \n" +
                "11. sed laoreet suscipit. ";


        // getting reference of  ExpandableTextView
        ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        b.expandTextView.setText(yourText);

b.tvAboutUs.setText(yourText);

        b.tvUserDetailsReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.tvAboutUs.setMaxLines(100);
                b.tvUserDetailsReadMore.setVisibility(View.GONE);
                b.tvUserDetailsReadLess.setVisibility(View.VISIBLE);
            }
        });


        b.tvUserDetailsReadLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.tvAboutUs.setMaxLines(6);
                b.tvUserDetailsReadMore.setVisibility(View.VISIBLE);
                b.tvUserDetailsReadLess.setVisibility(View.GONE);
            }
        });
    }
}