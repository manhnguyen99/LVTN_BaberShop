package com.example.lvtn_babershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lvtn_babershop.Fragment.HomeFragment;
import com.example.lvtn_babershop.Fragment.ShopingFragment;
import com.example.lvtn_babershop.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import Common.Common;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(HomeActivity.this);

        //init
        userRef = FirebaseFirestore.getInstance().collection("User");
        if(getIntent() != null){
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if(isLogin){

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
               //luu thong tin nguoi dung
                DocumentReference curentUser = userRef.document(user.getPhoneNumber());
                curentUser.get()
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                DocumentSnapshot userSnapShot = task.getResult();
                                if(!userSnapShot.exists()){
                                    showUpdateDialog(user.getPhoneNumber());
                                }
                            }
                        });
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
               if(item.getItemId() == R.id.action_home)
                   fragment = new HomeFragment();
               else if(item.getItemId() ==  R.id.action_shoping)
                   fragment = new ShopingFragment();
                return loadFragment(fragment);
            }
        });
    }



    private void showUpdateDialog(String phoneNumber) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
         View sheetView = getLayoutInflater().inflate(R.layout.layout_update_imfomation, null);

        Button btn_update = sheetView.findViewById(R.id.btn_update);
        TextInputEditText edt_name = sheetView.findViewById(R.id.edt_name);
        TextInputEditText edt_address = sheetView.findViewById(R.id.edt_address);

        btn_update.setOnClickListener(v -> {
            User user = new User(edt_name.getText().toString(),
                    edt_address.getText().toString(),
                    phoneNumber);
            userRef.document(phoneNumber)
                    .set(user)
                    .addOnSuccessListener(unused -> {
                        bottomSheetDialog.dismiss();
                        Toast.makeText(HomeActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                        })
                    .addOnFailureListener(e ->{
                        bottomSheetDialog.dismiss();
                        Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
        });
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment!= null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

}