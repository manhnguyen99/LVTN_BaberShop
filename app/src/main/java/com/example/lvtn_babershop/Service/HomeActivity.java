package com.example.lvtn_babershop.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvtn_babershop.Model.HairStyle;
import com.example.lvtn_babershop.Model.HairStyleAdapter;
import com.example.lvtn_babershop.Model.ProductAdapter;
import com.example.lvtn_babershop.Model.User;
import com.example.lvtn_babershop.Model.product;
import com.example.lvtn_babershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    ArrayList<product> arrProduct;
    ProductAdapter adapter;

    ArrayList<HairStyle> arrHairStyle;
    HairStyleAdapter adapterHairStyle;

    RecyclerView recyclerView, recyclerViewHairStyle;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    TextView txtNameCustomer, txtEmail, txtPhoneNumber;
    ImageView imgBooking;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrProduct = new ArrayList<product>();
        arrHairStyle = new ArrayList<HairStyle>();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        getControls();

        imgBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        LoadDataProduct();
        LoadDataHairStyle();


    }
    private void getControls() {
        txtNameCustomer = findViewById(R.id.txtNameCustomer);
        txtEmail  = findViewById(R.id.txtEmail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        imgBooking = findViewById(R.id.imgBooking);

        recyclerView = findViewById(R.id.recycleView);
        recyclerViewHairStyle = findViewById(R.id.recycleViewHairStyle);
        mDrawerlayout = findViewById(R.id.drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }


    private void LoadDataHairStyle() {
        reference = FirebaseDatabase.getInstance().getReference().child("HairStyle");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    HairStyle hairStyle;
                    hairStyle = dataSnapshot.getValue(HairStyle.class);
                    arrHairStyle.add(hairStyle);
                }
                adapterHairStyle = new HairStyleAdapter(HomeActivity.this, arrHairStyle);
                LinearLayoutManager manager  = new LinearLayoutManager(HomeActivity.this);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewHairStyle.setLayoutManager(manager);
                recyclerViewHairStyle.setAdapter(adapterHairStyle);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void LoadDataProduct()
    {
        reference = FirebaseDatabase.getInstance().getReference().child("product"); //child: chọn một nốt
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    product p;
                    p = dataSnapshot.getValue(product.class);
                    arrProduct.add(p);

                }

                adapter = new ProductAdapter(HomeActivity.this, arrProduct);
                LinearLayoutManager manager = new LinearLayoutManager(HomeActivity.this);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, StaffLogin.class));
                finish();
                return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
}