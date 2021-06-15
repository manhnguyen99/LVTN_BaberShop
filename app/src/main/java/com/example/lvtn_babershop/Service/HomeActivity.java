package com.example.lvtn_babershop.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    ArrayList<product> arrProduct;
    ProductAdapter adapter;
    RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    TextView txtNameCustomer, txtEmail, txtPhoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrProduct = new ArrayList<product>();


        mDrawerlayout = findViewById(R.id.drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);

        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        txtNameCustomer = findViewById(R.id.txtNameCustomer);
        txtEmail  = findViewById(R.id.txtEmail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);

        recyclerView = findViewById(R.id.recycleView);
        LoadDataProduct();
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