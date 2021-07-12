package com.example.lvtn_babershop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Fragment.HomeFragment;
import com.example.lvtn_babershop.Fragment.ShoppingFragment;
import com.example.lvtn_babershop.Interface.IBannerLoadListener;
import com.example.lvtn_babershop.Model.Banner;
import com.example.lvtn_babershop.Model.BookingInformation;
import com.example.lvtn_babershop.Model.HairStyle;
import com.example.lvtn_babershop.Model.HairStyleAdapter;
import com.example.lvtn_babershop.Model.ProductAdapter;
import com.example.lvtn_babershop.Model.User;
import com.example.lvtn_babershop.Model.product;
import com.example.lvtn_babershop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import java.util.List;

//import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity implements IBannerLoadListener {


    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationView =findViewById(R.id.navigation_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
              if (item.getItemId() == R.id.action_home)
              {
                  fragment = new HomeFragment();
              }
              else if (item.getItemId() == R.id.action_shoping)
              {
                  fragment = new ShoppingFragment();
              }

                return loadFragment(fragment);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                if (item.getItemId() == R.id.logout)
                    LogOut();
                return true;
            }
        });
    }
    private void LogOut() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this, CustomerLogin.class));
                        finish();
                    }
                }).setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();
            return  true;
        }
        return false;
    }

//    private void LoadBanner() {
//        reference = FirebaseDatabase.getInstance().getReference("Banner").child("image");
//        reference.get()
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
//
//                        List<Banner> banners = new ArrayList<>();
//                        if (task.isSuccessful())
//                        {
//                            for (DataSnapshot dataSnapshot:task.getResult().getChildren())
//                            {
//                                Banner banner = dataSnapshot.getValue(Banner.class);
//                                banners.add(banner);
//                            }
//                            iBannerLoadListener.onBannerLoadSuccess(banners);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//                iBannerLoadListener.onBannerLoadFailed(e.getMessage());
//            }
//        });
//    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, CustomerLogin.class));
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

    @Override
    public void onBannerLoadSuccess(List<Banner> banners) {
//        sliderBanner.setAdapter(new HomeSliderAdapter(banners));
    }

    @Override
    public void onBannerLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}