package com.example.lvtn_babershop;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Common.Common;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int APP_REQUEST_CODE = 7117;

    private List<AuthUI.IdpConfig> providers;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth;

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.txt_skip)
    TextView txt_skip;
    @OnClick(R.id.btn_login)
    void loginUser(){
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers).build(),APP_REQUEST_CODE);
    }
    @OnClick(R.id.txt_skip)
    void skipLoginJustGoHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Common.IS_LOGIN, false);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providers = Collections.singletonList(new AuthUI.IdpConfig.PhoneBuilder().build());

        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                checkUserFromFirebase(user);
            }
        };
        Dexter.withActivity(this)
                .withPermission(String.valueOf(new String[]{
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR
                })).withListener(new MultiplePermissionsListener(){
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){

                    FirebaseMessaging.getInstance()
                            .getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<String> task) {
                            if(task.isSuccessful()){
                                
                                Common.updateToken(getBaseContext(),task.getResult();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra(Common.IS_LOGIN, true);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                            Intent intent  = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra(Common.IS_LOGIN, true);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else {
                    setContentView(R.layout.activity_main);
                    ButterKnife.bind(MainActivity.this);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void checkUserFromFirebase(FirebaseUser user) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Common.updateToken(getBaseContext(), task.getResult().getToken());
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra(Common.IS_LOGIN, true);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra(Common.IS_LOGIN, true);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    protected void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
            else
            {
                Toast.makeText(this, "Fail to Sign In", Toast.LENGTH_SHORT).show();
            }
        }


    }

}