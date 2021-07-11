package com.example.lvtn_babershop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerLogin extends AppCompatActivity {

    TextInputLayout edtEmail, edtPass;
    Button btnSigin, btnSiginphone;
    TextView txtForgotPassword, txtCreateAccount;
    String emailid, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        try {
            edtEmail = findViewById(R.id.edtEmail);
            edtPass = findViewById(R.id.edtPassword);
            btnSigin = findViewById(R.id.btnLogin);
            btnSiginphone  = findViewById(R.id.btnLoginWithPhone);
            txtForgotPassword = findViewById(R.id.txtForgotpass);
            txtCreateAccount = findViewById(R.id.txtCreateAccount);

            firebaseAuth = FirebaseAuth.getInstance();

            btnSigin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailid = edtEmail.getEditText().getText().toString().trim();
                    password = edtPass.getEditText().getText().toString().trim();
                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(CustomerLogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait...");
                        mDialog.show();

                        firebaseAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                mDialog.dismiss();

                                if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    mDialog.dismiss();
                                    Toast.makeText(CustomerLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CustomerLogin.this, HomeActivity.class);
                                    intent.putExtra(Common.IS_LOGIN, true);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    ReusableCodeForAll.ShowAlert(CustomerLogin.this,"Verification Failed","You Have Not Verified Your Email!!!");
                                }
                            }
                            else
                            {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(CustomerLogin.this, "Error", task.getException().getMessage());
                            }
                        });
                    }
                }
            });
            txtCreateAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CustomerLogin.this, CustomerRegistration.class ));
                    finish();
                }
            });
            btnSiginphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CustomerLogin.this, StaffPhone.class));
                    finish();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {

        edtEmail.setErrorEnabled(false);
        edtEmail.setError("");
        edtPass.setErrorEnabled(false);
        edtPass.setError("");

        boolean isvalid = false, isvalidemail = false, isvalidpassword = false;
        if (TextUtils.isEmpty(emailid))
        {
            edtEmail.setErrorEnabled(true);
            edtEmail.setError("Email is required");
        }
        else
        {
            if(emailid.matches(emailpattern)){ // nếu email hợp lệ
                isvalidemail =  true;
            }
            else
            {
                edtEmail.setErrorEnabled(true);
                edtEmail.setError("Invalid Email Address"); //email khonmg hợp lệ -> báo lỗi
            }
        }
        if(TextUtils.isEmpty(password)){
            edtPass.setErrorEnabled(true);
            edtPass.setError("Password is required");
        }
        else
        {
            isvalidpassword = true;
        }
        isvalid = (isvalidemail && isvalidpassword )? true : false;
        return isvalid;
    }

}