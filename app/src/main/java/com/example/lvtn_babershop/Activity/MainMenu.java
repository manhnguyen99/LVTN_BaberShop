package com.example.lvtn_babershop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.lvtn_babershop.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainMenu extends AppCompatActivity {
    Button btnSignWithPhone, btnSignWithMail, btnSignup;
    TextView txtSkip;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnSignWithPhone = findViewById(R.id.btnSignWithPhone);
        btnSignWithMail = findViewById(R.id.btnSignWithMail);
        btnSignup = findViewById(R.id.btnSigup);

        btnSignWithMail.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, CustomerLogin.class);
            intent.putExtra("Home", "Email");
            startActivity(intent);
            finish();
        });
        btnSignWithPhone.setOnClickListener(v -> {
            Intent intent = new Intent( MainMenu.this, CustomerPhone.class);
            intent.putExtra("Home", "Phone");
            startActivity(intent);
            finish();
        });
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, CustomerRegistration.class);
            intent.putExtra("Home", "Signup");
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}