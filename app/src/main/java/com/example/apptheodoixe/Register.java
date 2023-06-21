package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText etrEmail, etrPassword;
    private Button btnrDangKy;
    private TextView tvrDangNhap;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        tvrDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        btnrDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDangKy();
            }
        });
    }

    private void init(){
        etrEmail=findViewById(R.id.etrEmail);
        etrPassword=findViewById(R.id.etrPassword);
        btnrDangKy=findViewById(R.id.btnrDangKy);
        tvrDangNhap=findViewById(R.id.tvrDangNhap);
        progressDialog=new ProgressDialog(this);
    }

    private void onClickDangKy(){
        int kt=1;
        String email=etrEmail.getText().toString().trim();
        String password = etrPassword.getText().toString().trim();
        if(email==null){
            Toast.makeText(Register.this, "Không được để trống email",
                    Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(password==null){
            Toast.makeText(Register.this, "Không được để trông password",
                    Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(kt==1){
            FirebaseAuth auth =FirebaseAuth.getInstance();
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent=new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}