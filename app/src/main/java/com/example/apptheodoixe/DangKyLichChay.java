package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apptheodoixe.model.ChuyenDi;
import com.example.apptheodoixe.model.LichChay;
import com.example.apptheodoixe.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DangKyLichChay extends AppCompatActivity {

    private EditText etdklNhaXe, etdklBienSoXe,etdklGia,etdklThoiGianVe,etdklThoiGianDi;
    private Spinner spdklTinhDi,spdklTinhDen, spdklTinhTrang;
    private Button btndklQuayLai,btndklCapNhat;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_lich_chay);
        init();
        getData();
        getUser();
        etdklThoiGianDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        etdklThoiGianDi.setText(hourOfDay+":"+minute1);
                    }
                };

                TimePickerDialog timePickerDialog=new TimePickerDialog(DangKyLichChay.this, onTimeSetListener, hour, minute,false);
                timePickerDialog.show();
            }
        });
        etdklThoiGianVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        etdklThoiGianVe.setText(hourOfDay+":"+minute1);
                    }
                };

                TimePickerDialog timePickerDialog=new TimePickerDialog(DangKyLichChay.this, onTimeSetListener, hour, minute,false);
                timePickerDialog.show();
            }
        });
        btndklCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
                String email=userdn.getEmail();
                String emailgui=email.replace(".","");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(emailgui);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                if(user.getVaitro().equals("Người dùng")){
                                    Toast.makeText(DangKyLichChay.this, "Bạn phải trở thành tài xế để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    sendData();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btndklQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        user=new User();
        etdklNhaXe=findViewById(R.id.etdklNhaXe);
        etdklBienSoXe=findViewById(R.id.etdklBienSoXe);
        etdklGia=findViewById(R.id.etdklGia);
        etdklThoiGianVe=findViewById(R.id.etdklThoiGianVe);
        etdklThoiGianDi=findViewById(R.id.etdklThoiGianDi);
        spdklTinhDi=findViewById(R.id.spdklTinhDi);
        spdklTinhDen=findViewById(R.id.spdklTinhDen);
        btndklQuayLai=findViewById(R.id.btndklQuayLai);
        btndklCapNhat=findViewById(R.id.btndklCapNhat);
        spdklTinhTrang=findViewById(R.id.spdklTinhTrang);
        spdklTinhDen.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        spdklTinhDi.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        spdklTinhTrang.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_trang)));
    }

    private void sendData(){
        int kt=1;
        if(etdklNhaXe.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyLichChay.this, "Không được để trống nhà xe", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(etdklBienSoXe.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyLichChay.this, "Không được để trống biển số xe", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(etdklGia.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyLichChay.this, "Không được để trống giá", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(etdklThoiGianDi.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyLichChay.this, "Không được để trống thời gian đi", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(etdklThoiGianVe.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyLichChay.this, "Không được để trống thời gian về", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(spdklTinhDi.getSelectedItemPosition()==0){
            Toast.makeText(DangKyLichChay.this, "Vui lòng chọn tỉnh xuất phát", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(spdklTinhDen.getSelectedItemPosition()==0){
            Toast.makeText(DangKyLichChay.this, "Vui lòng chọn tỉnh đến", Toast.LENGTH_SHORT).show();
            kt=0;
        }
        if(kt==1){
            FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String email=userdn.getEmail();
            String emailgui=email.replace(".","");
            LichChay lichChay=new LichChay(spdklTinhDi.getSelectedItem().toString().trim(),spdklTinhDen.getSelectedItem().toString().trim(),
                    etdklThoiGianDi.getText().toString().trim(),etdklThoiGianVe.getText().toString().trim(),etdklNhaXe.getText().toString().trim(),
                    etdklBienSoXe.getText().toString().trim(),user.getSodienthoai(),Integer.parseInt(etdklGia.getText().toString()),spdklTinhTrang.getSelectedItemPosition());

            DatabaseReference myRef1 = database.getReference("ds_lichxe");
            myRef1.child(emailgui).setValue(lichChay, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(DangKyLichChay.this, "Cập nhật lịch chạy xe thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void getData(){
        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
        String email=userdn.getEmail();
        String emailgui=email.replace(".","");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_lichxe");
        myRef.child(emailgui).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String key=snapshot.getKey();
                    LichChay value=snapshot.getValue(LichChay.class);
                    etdklNhaXe.setText(value.getNhaXe());
                    etdklBienSoXe.setText(value.getBienSoXe());
                    etdklGia.setText(value.getGia()+"");
                    etdklThoiGianDi.setText(value.getGioDi());
                    etdklThoiGianVe.setText(value.getGioVe());
                    int vt=0;
                    for (int i=0; i<spdklTinhDen.getCount(); i++){
                        if(spdklTinhDen.getItemAtPosition(i).equals(value.getTinhDen())){
                            vt=i;
                        }
                    }
                    spdklTinhDen.setSelection(vt);

                    for (int i=0; i<spdklTinhDi.getCount(); i++){
                        if(spdklTinhDi.getItemAtPosition(i).equals(value.getTinhDi())){
                            vt=i;
                        }
                    }
                    spdklTinhDi.setSelection(vt);
                    spdklTinhTrang.setSelection(value.getTinhTrang());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUser(){
        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email=userdn.getEmail();
        String emailgui=email.replace(".","");
        DatabaseReference myRef = database.getReference(emailgui);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    User user1 = snapshot.getValue(User.class);
                    if (user1 != null) {
                        user=user1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}