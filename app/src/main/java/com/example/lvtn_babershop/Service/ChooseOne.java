package com.example.lvtn_babershop.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lvtn_babershop.R;

public class ChooseOne extends AppCompatActivity {

    Button btnStaff, btnUser;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();
        btnStaff = findViewById(R.id.btnSignStaff);
        btnUser = findViewById(R.id.btnSignUser);

        btnStaff.setOnClickListener(v -> {
            if (type.equals("Email")){
                Intent loginemail = new Intent(ChooseOne.this, StaffLogin.class);
                startActivity(loginemail);
                finish();
            }
            if (type.equals("Phone")){
                Intent loginphone = new Intent(ChooseOne.this, StaffPhone.class);
                startActivity(loginphone);
                finish();
            }
            if (type.equals("Signup")){
                Intent register = new Intent(ChooseOne.this, StaffRegistration.class);
                startActivity(register);
                finish();
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")){
                    Intent loginemailuser = new Intent(ChooseOne.this, Login.class);
                    startActivity(loginemailuser);
                    finish();
                }
                if (type.equals("Phone")){
                    Intent loginphoneuser = new Intent(ChooseOne.this, CustomerPhone.class);
                    startActivity(loginphoneuser);
                    finish();
                }
                if (type.equals("Signup")){
                    Intent registeruser = new Intent(ChooseOne.this, CustomerRegistration.class);
                    startActivity(registeruser);
                    finish();
                }
            }
        });

    }
}