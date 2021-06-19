package com.example.lvtn_babershop.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;

import com.example.lvtn_babershop.Adapter.MyViewPagerAdapter;
import com.example.lvtn_babershop.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    StepView stepView;
    ViewPager viewPager;
    Button btnPre, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        getControls();
        setUpStepView();
        setColorButton();

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0)
                    btnPre.setEnabled(false);
                else
                    btnPre.setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void getControls() {
        stepView = findViewById(R.id.step_view);
        viewPager = findViewById(R.id.viewPager);
        btnPre = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setColorButton() {
        if(btnNext.isEnabled())
        {
            btnNext.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btnNext.setBackgroundResource(R.color.purple_200);
        }
        if(btnPre.isEnabled())
        {
            btnPre.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btnPre.setBackgroundResource(R.color.purple_200);
        }
    }

    private void setUpStepView() {

        List<String> stepList = new ArrayList<>();
        stepList.add("Salon");
        stepList.add("Staff");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }
}