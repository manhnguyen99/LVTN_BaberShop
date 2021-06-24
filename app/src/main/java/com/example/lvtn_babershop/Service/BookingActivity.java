package com.example.lvtn_babershop.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lvtn_babershop.Adapter.MyViewPagerAdapter;
import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Comon.NonSwipeViewPager;
import com.example.lvtn_babershop.Model.Baber;
import com.example.lvtn_babershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shuhart.stepview.StepView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class BookingActivity extends AppCompatActivity {

    //LocalBroadcastManager có nhiệm vụ phát đi Intent kèm theo data nhưng nó chỉ được truyền trong nội bộ ứng dụng
    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    DatabaseReference baberRef;


    StepView stepView;
    NonSwipeViewPager viewPager;
    Button btnPre, btnNext;


    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int step = intent.getIntExtra(Common.KEY_STEP, 0);
            if (step == 1)
                Common.currentSalon = intent.getParcelableExtra(Common.KEY_SALON_STORE);
            else if(step == 2)
                Common.currentBaber = intent.getParcelableExtra(Common.KEY_BABER_SELECTED);
            btnNext.setEnabled(true);
            setColorButton();
        }
    };
    @Override
    protected void onDestroy() {

        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        getControls();
        setUpStepView();
        setColorButton();

        dialog  = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

                stepView.go(position, true);
                if(position == 0)
                    btnPre.setEnabled(false);
                else
                    btnPre.setEnabled(true);

                //disable btnNext
                btnNext.setEnabled(false);

                setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.step < 3 || Common.step == 0)
                {
                    Common.step++;
                    if (Common.step == 1)
                    {
                        if(Common.currentSalon != null)
                            loadBaberBySalon(Common.currentSalon.getSalonID());
                    }
                    else if(Common.step == 2)
                    {
                        if(Common.currentBaber != null)
                            loadTimeSlotOfBaber(Common.currentBaber.getBaberId());
                    }
                    viewPager.setCurrentItem(Common.step);
                }
            }

            private void loadTimeSlotOfBaber(String baberId) {
                //Send Local Broadcast to fragment step 3
                Intent intent  = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
                localBroadcastManager.sendBroadcast(intent);
            }
            private void loadBaberBySalon(String salonID) {
                dialog.show();
                if(!TextUtils.isEmpty(Common.city))
                {
                    baberRef = FirebaseDatabase.getInstance()
                            .getReference("Salon")
                            .child(Common.city)
                            .child("Branch")
                            .child(salonID)
                            .child("Baber")
                    ;
                    baberRef.get()
                            .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                                    ArrayList<Baber> babers = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot:task.getResult().getChildren()){
                                        Baber baber = dataSnapshot.getValue(Baber.class);
                                        baber.setPassword("");
                                        baber.setBaberId(dataSnapshot.getKey()); //getID of baber

                                        babers.add(baber);
                                    }
                                    //send Broadcast to BookingStep2Fragment to load Recybber
                                    Intent intent =new Intent(Common.KEY_BABER_LOAD_DONE);
                                    intent.putParcelableArrayListExtra(Common.KEY_BABER_LOAD_DONE, babers);
                                    localBroadcastManager.sendBroadcast(intent);
                                    dialog.dismiss();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            dialog.dismiss();
                        }
                    });
                }

            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.step == 3 || Common.step > 0)
                {
                    Common.step--;
                    viewPager.setCurrentItem(Common.step);
                }
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