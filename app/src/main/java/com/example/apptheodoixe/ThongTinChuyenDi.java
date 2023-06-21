package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptheodoixe.model.ChuyenDi;
import com.example.apptheodoixe.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThongTinChuyenDi extends AppCompatActivity {

    private TextView tvttcdDiemDi,tvttcdDiemDen,tvttcdGioDi,tvttcdGia,tvttcdGhiChu;
    private TextView tvttcdBienSo,tvttcdHangXe,tvttcdLoaiXe;
    private TextView tvttcdName,tvttcdNgaySinh,tvttcdPhoneNumber,tvttcdEmail;
    private Button btnttcdQuayLai;
    private ChuyenDi chuyenDi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chuyen_di);
        init();
        Intent intent=getIntent();
        chuyenDi=(ChuyenDi) intent.getSerializableExtra("chuyendi");
//        Toast.makeText(ThongTinChuyenDi.this, chuyenDi.getTinhDi(), Toast.LENGTH_SHORT).show();
        tvttcdDiemDi.setText(chuyenDi.getTinhDi());
        tvttcdDiemDen.setText(chuyenDi.getTinhDen());
        tvttcdGioDi.setText(chuyenDi.getGioDi());
        tvttcdGia.setText(chuyenDi.getGia()+"");
        tvttcdGhiChu.setText(chuyenDi.getGhiChu());
        tvttcdBienSo.setText(chuyenDi.getBienSoXe());
        tvttcdHangXe.setText(chuyenDi.getHangXe());
        tvttcdLoaiXe.setText(chuyenDi.getLoaiXe());
        getThongTinTaiXe();
        btnttcdQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        tvttcdDiemDi=findViewById(R.id.tvttcdDiemDi);
        tvttcdDiemDen=findViewById(R.id.tvttcdDiemDen);
        tvttcdGioDi=findViewById(R.id.tvttcdGioDi);
        tvttcdGia=findViewById(R.id.tvttcdGia);
        tvttcdGhiChu=findViewById(R.id.tvttcdGhiChu);
        tvttcdBienSo=findViewById(R.id.tvttcdBienSo);
        tvttcdHangXe=findViewById(R.id.tvttcdHangXe);
        tvttcdLoaiXe=findViewById(R.id.tvttcdLoaiXe);
        tvttcdName=findViewById(R.id.tvttcdName);
        tvttcdNgaySinh=findViewById(R.id.tvttcdNgaySinh);
        tvttcdPhoneNumber=findViewById(R.id.tvttcdPhoneNumber);
        tvttcdEmail=findViewById(R.id.tvttcdEmail);
        btnttcdQuayLai=findViewById(R.id.btnttcdQuayLai);
    }

    private void getThongTinTaiXe(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(chuyenDi.getTaixe());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        tvttcdName.setText("Họ và tên: "+user.getName());
                        tvttcdNgaySinh.setText("Ngày sinh: "+user.getNgaysinh());
                        tvttcdPhoneNumber.setText("Số điện thoại: "+user.getSodienthoai());
                        tvttcdEmail.setText("Email: "+user.getEmail());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}