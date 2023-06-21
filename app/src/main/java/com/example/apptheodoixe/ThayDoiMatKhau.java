package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ThayDoiMatKhau extends AppCompatActivity {

    private EditText etdoimkMatKhauMoi, etdoimkNhapLai;
    private Button btndoimkQuayLai, btndoimkCapNhat;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_mat_khau);
        init();
        btndoimkQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btndoimkCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thayDoiMatKhau();
            }
        });
    }

    private void init(){
        etdoimkMatKhauMoi=findViewById(R.id.etdoimkMatKhauMoi);
        etdoimkNhapLai=findViewById(R.id.etdoimkNhapLai);
        btndoimkCapNhat=findViewById(R.id.btndoimkCapNhat);
        btndoimkQuayLai=findViewById(R.id.btndoimkQuayLai);
        progressDialog=new ProgressDialog(ThayDoiMatKhau.this);
    }

    private void thayDoiMatKhau(){
        String newPassword=etdoimkMatKhauMoi.getText().toString();
        String repeatPassword=etdoimkNhapLai.getText().toString();
        int kt=1;
        if(newPassword.trim().isEmpty()){
            Toast.makeText(ThayDoiMatKhau.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(repeatPassword.trim().isEmpty()){
            Toast.makeText(ThayDoiMatKhau.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(!newPassword.equals(repeatPassword)){
            Toast.makeText(ThayDoiMatKhau.this, "Mật khẩu nhập lại không khớp vui lòng nhập lại", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(kt==1){
            progressDialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String newPassword1 = newPassword;

            user.updatePassword(newPassword1)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(ThayDoiMatKhau.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}