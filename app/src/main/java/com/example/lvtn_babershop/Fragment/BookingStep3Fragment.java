package com.example.lvtn_babershop.Fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Adapter.MyTimeSlotAdapter;
import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Comon.SpacesItemDecoration;
import com.example.lvtn_babershop.Interface.ITimeSlotLoadListener;
import com.example.lvtn_babershop.Model.Salon;
import com.example.lvtn_babershop.Model.TimeSlot;
import com.example.lvtn_babershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dmax.dialog.SpotsDialog;

public class BookingStep3Fragment extends Fragment implements ITimeSlotLoadListener {
    DatabaseReference barberRef;
    DocumentSnapshot documentSnapshot;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    AlertDialog dialog;
    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;
    Calendar selected_date;
    RecyclerView recyclerTimeSlot;
    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;

    BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 0);
            loadAvailableTimeslotOfBarber(Common.currentBaber.getBaberId(),
                    simpleDateFormat.format(date.getTime()));
        }
    };

    private void loadAvailableTimeslotOfBarber(String baberId, final String bookDate) {
        dialog.show();
        //https://babershop-8628c-default-rtdb.firebaseio.com/Salon/HANOI/Branch/Salon1/Baber/Baber1
        barberRef = FirebaseDatabase.getInstance()
                .getReference("Salon")
                .child(Common.city)
                .child("Branch")
                .child(Common.currentSalon.getSalonID())
                .child("Baber")
                .child(Common.currentBaber.getBaberId());
        //get Informationn of this barber
        barberRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    DataSnapshot dataSnapshot = task.getResult();
                    if(dataSnapshot.exists())
                    {
                        // get information of booking
                        //if not created, teturn empty
                        Salon salon =  dataSnapshot.getValue(Salon.class);
                        salon.setSalonID(dataSnapshot.getKey());
                        DatabaseReference date = FirebaseDatabase.getInstance()
                                .getReference("Salon")
                                .child(Common.city)
                                .child("Branch")
                                .child(Common.currentSalon.getSalonID())
                                .child("Baber")
                                .child(Common.currentBaber.getBaberId())
                                .child(bookDate);
                        date.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DataSnapshot dataSnapshot1 = task.getResult();
                                    if(!dataSnapshot1.exists())
                                        iTimeSlotLoadListener.onTimeslotLoadempty();
                                    else
                                    {
                                        List<TimeSlot> timeSlots = new ArrayList<>();
                                        for (DataSnapshot dataSnapshot2:task.getResult().getChildren())
                                            timeSlots.add(dataSnapshot2.getValue(TimeSlot.class));
                                        iTimeSlotLoadListener.onTimeSlotLoadSuccess(timeSlots);
                                        dialog.dismiss();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                iTimeSlotLoadListener.onTimeSlotLoadFailed(e.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }

    static BookingStep3Fragment instance;
    public static BookingStep3Fragment getInstance()
    {
        if (instance == null)
            instance = new BookingStep3Fragment();
        return instance;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iTimeSlotLoadListener =  this;
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(displayTimeSlot, new IntentFilter(Common.KEY_DISPLAY_TIME_SLOT));
        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        selected_date = Calendar.getInstance();
        selected_date.add(Calendar.DATE, 0);
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(displayTimeSlot);
        super.onDestroy();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_booking_step_three, container, false); super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, itemView);
        recyclerTimeSlot = itemView.findViewById(R.id.recyclerTimeSlot);
        calendarView = itemView.findViewById(R.id.calendarView);

        initView(itemView);

        return itemView;

    }
    private void initView(View itemView) {
        recyclerTimeSlot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerTimeSlot.setLayoutManager(gridLayoutManager);
        recyclerTimeSlot.addItemDecoration(new SpacesItemDecoration(8));

        //calendar
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE,0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE,2); //2 day left

        HorizontalCalendar horizontalCalendar =  new HorizontalCalendar.Builder(itemView, R.id.calendarView)
                .range(startDate,endDate)
                .datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if(selected_date.getTimeInMillis() != date.getTimeInMillis())
                {
                    selected_date = date;
                    loadAvailableTimeslotOfBarber(Common.currentBaber.getBaberId(),
                            simpleDateFormat.format(date.getTime()));
                }
            }
        });
    }

    @Override
    public void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList) {
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getContext(),timeSlotList);
        recyclerTimeSlot.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void onTimeSlotLoadFailed(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeslotLoadempty() {
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getContext());
        recyclerTimeSlot.setAdapter(adapter);
        dialog.dismiss();
    }
}
