package com.example.lvtn_babershop.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvtn_babershop.Activity.BookingActivity;
import com.example.lvtn_babershop.Activity.HomeActivity;
import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Model.BookingInformation;
import com.example.lvtn_babershop.Model.HairStyle;
import com.example.lvtn_babershop.Model.HairStyleAdapter;
import com.example.lvtn_babershop.Model.ProductAdapter;
import com.example.lvtn_babershop.Model.User;
import com.example.lvtn_babershop.Model.product;
import com.example.lvtn_babershop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;
    Button btnDelete;


    CardView cardViewBookingInfo;

    ArrayList<product> arrProduct;
    ProductAdapter adapter;

    ArrayList<HairStyle> arrHairStyle;
    HairStyleAdapter adapterHairStyle;
    BottomNavigationView bottomNavigationView;



    RecyclerView recyclerView, recyclerViewHairStyle;

    TextView txtBookingTimeText, txtBookingBarberText, txtLocationSalon, txtSalonName, txtSalonOpenHours, txtSalonPhoneNumber, txtSalonWebsite;
    SimpleDateFormat simpleDateFormat;

    TextView txtNameCustomer, txtEmail, txtPhoneNumber;
    CardView imgBooking;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView =  inflater.inflate(R.layout.fragment_home, container, false);

        arrProduct = new ArrayList<product>();
        arrHairStyle = new ArrayList<HairStyle>();

//        txtNameCustomer = itemView.findViewById(R.id.txtNameCus);
        txtEmail = itemView.findViewById(R.id.txtEmail);
        txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
        imgBooking = itemView.findViewById(R.id.imgBooking);
        txtBookingBarberText = itemView.findViewById(R.id.txtBookingBarberText);
        txtBookingTimeText = itemView.findViewById(R.id.txtBookingTimeText);
        txtLocationSalon = itemView.findViewById(R.id.txtLocationSalon);
        txtSalonName = itemView.findViewById(R.id.txtSalonName);
        txtSalonPhoneNumber = itemView.findViewById(R.id.txtSalonPhoneNumber);
        txtSalonOpenHours = itemView.findViewById(R.id.txtSalonOpenHours);
        txtSalonWebsite = itemView.findViewById(R.id.txtSalonWebsite);


        recyclerViewHairStyle = itemView.findViewById(R.id.recycleViewHairStyle);
        cardViewBookingInfo = itemView.findViewById(R.id.cardViewInfoBooking);
        btnDelete = itemView.findViewById(R.id.btnDelete);

        recyclerView = itemView.findViewById(R.id.recycleView);
//        recyclerViewHairStyle = itemView.findViewById(R.id.recycleViewHairStyle);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        bottomNavigationView = itemView.findViewById(R.id.bottom_navigation);

        LoadDataProduct();
        LoadDataHairStyle();
        LoadInfoBooking();
        DeleteBooking();
        checkCustomerInfo();

        imgBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookingActivity.class);
                startActivity(intent);
            }
        });

        return itemView;
    }
    private void checkCustomerInfo() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Customer");
        userID = firebaseUser.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Common.currentUser = snapshot.getValue(User.class);
//                    Log.d("BBBBB", "onDataChange: " + dataSnapshot.getKey());
//                    reference.child(dataSnapshot.getKey())
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                                    User user = dataSnapshot.getValue(User.class);
//                                    User users = new User();
//                                    users.setFname(user.getFname());
//                                    users.setLname(user.getLname());
//                                    users.setMobile(user.getMobile());
//                                    users.setEmailID(user.getEmailID());
//                                    users.setCity(user.getCity());
//
//                                    Common.currentUser = users;
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                                }
//                            });
                }
//
//                txtNameCustomer.setText(Common.currentUser.getLname());
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }
    private void DeleteBooking() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Delete your booking");
                alertDialog.setMessage("Are you sure to Delete this booking?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        reference = FirebaseDatabase.getInstance().getReference("Customer");
                        userID = firebaseUser.getUid();
                        reference.child(userID).child("booking").removeValue();
                        DatabaseReference bookingDate  = FirebaseDatabase.getInstance()
                                .getReference("Salon")
                                .child(Common.city)
                                .child("Branch")
                                .child(Common.currentSalon.getSalonID())
                                .child("Baber")
                                .child(Common.currentBaber.getBaberId())
                                .child(Common.simpleDateFormat.format(Common.currentDate.getTime()))
                                .child(String.valueOf(Common.currentTimeSlot));
                        bookingDate.removeValue();
                        resetStaticData();
                        Toast.makeText(getActivity(), "Delete Success!!!", Toast.LENGTH_SHORT).show();
                        cardViewBookingInfo.setVisibility(View.GONE);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
    }
    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentSalon = null;
        Common.currentBaber = null;
        Common.currentDate.add(Calendar.DATE, 0);
    }
    private void LoadInfoBooking() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Customer");
        userID = firebaseUser.getUid();
        cardViewBookingInfo.setVisibility(View.GONE);
        reference.child(userID).child("booking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    cardViewBookingInfo.setVisibility(View.VISIBLE);
                    BookingInformation bookingInformation = snapshot.getValue(BookingInformation.class);
                    Log.i("CCCC", "onDataChange: "+snapshot.getChildren());
                    txtLocationSalon.setText(bookingInformation.getSalonAddress());
                    txtBookingBarberText.setText(bookingInformation.getBarberName());
                    txtBookingTimeText.setText(bookingInformation.getTime());
                    txtSalonName.setText(bookingInformation.getSalonName());
                    txtSalonPhoneNumber.setText(bookingInformation.getPhone());
                } else {
                    Toast.makeText(getContext(), "Hien chua booking", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoadDataProduct() {
        reference = FirebaseDatabase.getInstance().getReference().child("product"); //child: chọn một nốt
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    product p;
                    p = dataSnapshot.getValue(product.class);
                    arrProduct.add(p);
                }
                adapter = new ProductAdapter(getContext(), arrProduct);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void LoadDataHairStyle() {
        reference = FirebaseDatabase.getInstance().getReference().child("HairStyle");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HairStyle hairStyle;
                    hairStyle = dataSnapshot.getValue(HairStyle.class);
                    arrHairStyle.add(hairStyle);
                }
                adapterHairStyle = new HairStyleAdapter(getContext(), arrHairStyle);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewHairStyle.setLayoutManager(manager);
                recyclerViewHairStyle.setAdapter(adapterHairStyle);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}

