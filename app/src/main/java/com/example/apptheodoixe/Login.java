package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {

    private EditText etlEmail, etlPassword;
    private TextView tvlDangKy, tvlQuenMK;
    private Button btnlDangNhap;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        tvlDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        btnlDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangNhap();
            }
        });
        tvlQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quenMK();
            }
        });
    }

    private void init(){
        etlEmail=findViewById(R.id.etlEmail);
        etlPassword=findViewById(R.id.etlPassword);
        tvlDangKy=findViewById(R.id.tvlDangKy);
        btnlDangNhap=findViewById(R.id.btnlDangNhap);
        tvlQuenMK=findViewById(R.id.tvlQuenMK);
        progressDialog=new ProgressDialog(this);

    }


    private void dangNhap(){
        String email=etlEmail.getText().toString().trim();
        String password=etlPassword.getText().toString().trim();
        int kt=1;
        if(TextUtils.isEmpty(etlEmail.getText().toString())){
            Toast.makeText(Login.this, "Không được để trống email",
                    Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(TextUtils.isEmpty(etlPassword.getText().toString())){
            Toast.makeText(Login.this, "Không được để trông password",
                    Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(kt==1){
//            progressDialog.show();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                progressDialog.dismiss();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void quenMK(){
        String email=etlEmail.getText().toString().trim();
        int kt=1;
        if(TextUtils.isEmpty(etlEmail.getText().toString())){
            Toast.makeText(Login.this, "Xin vui lòng nhập email",
                    Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(kt==1) {
            progressDialog.show();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = email;
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Đã gửi thông in về địa chỉ "+emailAddress,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Login.this, "Thông tin email không chính xác",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}
//nguyendinhdat23082001ndd@gmail.com