package com.example.lvtn_babershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private TextView txtName, txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        if(user != null){
            txtName.setText(user.getDisplayName());
            txtEmail.setText(user.getEmail());
        }
        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                LoginManager.getInstance().logOut();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user == null){
            openLogin();
        }
    }

    private void openLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}