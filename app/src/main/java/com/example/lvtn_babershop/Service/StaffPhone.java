package com.example.lvtn_babershop.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lvtn_babershop.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class StaffPhone extends AppCompatActivity {

    EditText edtPhoneNumber;
    Button btnSendOTP, btnSignWithEmail;
    TextView txtSignup;
    CountryCodePicker cpp;
    FirebaseAuth firebaseAuth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_phone);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        txtSignup = findViewById(R.id.txtSignup);
        cpp = findViewById(R.id.countrycode);
        btnSignWithEmail = findViewById(R.id.btnSignWithMail);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSendOTP.setOnClickListener(v -> {
            number = edtPhoneNumber.getText().toString().trim();
            String phoneNum = cpp.getSelectedCountryCodeWithPlus()+number;
            Intent intent = new Intent(StaffPhone.this, StaffSendOTP.class);
            intent.putExtra("PhoneNum", phoneNum);
            startActivity(intent);
            finish();
        });

        txtSignup.setOnClickListener(v -> {
            Intent intent = new Intent(StaffPhone.this, StaffSendOTP.class);
            startActivity(intent);
            finish();
        });
    }
}