package com.example.lvtn_babershop.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Model.BookingInformation;
import com.example.lvtn_babershop.R;
import com.example.lvtn_babershop.Service.HomeActivity;
import com.example.lvtn_babershop.Service.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
public class BookingStep4Fragment extends Fragment {
    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    TextView txtBookingTimeText, txtBookingBarberText, txtLocationSalon, txtSalonName, txtSalonOpenHours, txtSalonPhoneNumber, txtSalonWebsite;
    Button btnConfirm;
    Unbinder unbinder;
    BroadcastReceiver confirmBookingReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };
    private void setData() {
        txtBookingBarberText.setText(Common.currentBaber.getNameBaber());
        txtBookingTimeText.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append("at")
                .append(simpleDateFormat.format(Common.currentDate.getTime())));
        txtLocationSalon.setText(Common.currentSalon.getAddressSalon());
        txtSalonWebsite.setText(Common.currentSalon.getWebsite());
        txtSalonName.setText(Common.currentSalon.getNameSalon());
        txtSalonOpenHours.setText(Common.currentSalon.getOpenHours());
    }
    static BookingStep4Fragment instance;
    public static BookingStep4Fragment getInstance()
    {
        if (instance == null)
            instance = new BookingStep4Fragment();
        return instance;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver, new IntentFilter(Common.KEY_CONFIRM_BOOKING));
    }
    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.fragment_booking_step_four, container, false);
        unbinder = ButterKnife.bind(this, itemView);
        txtBookingBarberText = itemView.findViewById(R.id.txtBookingBarberText);
        txtBookingTimeText = itemView.findViewById(R.id.txtBookingTimeText);
        txtLocationSalon = itemView.findViewById(R.id.txtLocationSalon);
        txtSalonName = itemView.findViewById(R.id.txtSalonName);
        txtSalonPhoneNumber =itemView.findViewById(R.id.txtSalonPhoneNumber);
        txtSalonOpenHours = itemView.findViewById(R.id.txtSalonOpenHours);
        txtSalonWebsite = itemView.findViewById(R.id.txtSalonWebsite);
        btnConfirm = itemView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingInformation bookingInformation = new BookingInformation();

                bookingInformation.setBarberId(Common.currentBaber.getBaberId());
                bookingInformation.setBarberName(Common.currentBaber.getNameBaber());
                bookingInformation.setCustomerName(Common.currentUser.getFname());
                bookingInformation.setCustomerPhone(Common.currentUser.getMobile());
                bookingInformation.setSalonID(Common.currentSalon.getSalonID());
                bookingInformation.setSalonAddress(Common.currentSalon.getAddressSalon());
                bookingInformation. setSalonName(Common.currentSalon.getNameSalon());
                bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                        .append("at: ")
                        .append(simpleDateFormat.format(Common.currentDate.getTime())).toString());
                bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

                DatabaseReference bookingDate  = FirebaseDatabase.getInstance()
                        .getReference("Salon")
                        .child(Common.city)
                        .child("Branch")
                        .child(Common.currentSalon.getSalonID())
                        .child("Baber")
                        .child(Common.currentBaber.getBaberId())
                        .child(Common.simpleDateFormat.format(Common.currentDate.getTime()))
                        .child(String.valueOf(Common.currentTimeSlot));
                //write data
                bookingDate.setValue(bookingInformation)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                resetStaticData();
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(getContext(), "Success booking!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return itemView;
    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentSalon = null;
        Common.currentBaber = null;
        Common.currentDate.add(Calendar.DATE, 0);
    }
}