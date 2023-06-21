package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
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
import com.example.apptheodoixe.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class DangKyXe extends AppCompatActivity {

    private Spinner spdkTinhDen, spdkTinhDi, spdkTinhTrang, spdkLoaiXe;
    private EditText etdkGhiChu,etdkThoiGian,etdkGia, etdkBienSoXe,etdkHangXe;
    private Button btndkQuayLai,btndkCapNhat;
    private Map<String,ChuyenDi> chuyenDiMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_xe);
        init();
        layDlChuyendi();
        etdkThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        etdkThoiGian.setText(hourOfDay+":"+minute1);
                    }
                };

                TimePickerDialog timePickerDialog=new TimePickerDialog(DangKyXe.this, onTimeSetListener, hour, minute,false);
                timePickerDialog.show();
            }
        });

        btndkCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChuyenDi chuyenDi=setChuyenDi();
                if(chuyenDi!=null){
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
                                        Toast.makeText(DangKyXe.this, "Bạn phải trở thành tài xế để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
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
            }
        });

        btndkQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        spdkTinhDen=findViewById(R.id.spdkTinhDen);
        spdkTinhDi=findViewById(R.id.spdkTinhDi);
        spdkTinhTrang=findViewById(R.id.spdkTinhTrang);
        spdkLoaiXe=findViewById(R.id.spdkLoaiXe);
        etdkGhiChu=findViewById(R.id.etdkGhiChu);
        etdkThoiGian=findViewById(R.id.etdkThoiGian);
        etdkGia=findViewById(R.id.etdkGia);
        etdkBienSoXe=findViewById(R.id.etdkBienSoXe);
        etdkHangXe=findViewById(R.id.etdkHangXe);
        btndkQuayLai=findViewById(R.id.btndkQuayLai);
        btndkCapNhat=findViewById(R.id.btndkCapNhat);
        spdkTinhDi.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        spdkTinhDen.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        spdkLoaiXe.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.loai_xe)));
        spdkTinhTrang.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_trang)));
        chuyenDiMap=new HashMap<>();
    }

    private void sendData(){
        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
        String email=userdn.getEmail();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Đẩy dữ liệu chuyến đi
        ChuyenDi chuyenDi=setChuyenDi();
        if(chuyenDi.getTinhTrang()==0) {
            String emailgui = email.replace(".", "");
//            Toast.makeText(DangKyXe.this, emailgui, Toast.LENGTH_SHORT).show();
            chuyenDiMap.put(emailgui,chuyenDi);
            DatabaseReference myRef = database.getReference("ds_chuyendi");
            myRef.setValue(chuyenDiMap,new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(DangKyXe.this, "Cập nhật chuyến đi thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            String emailgui = email.replace(".", "");
            DatabaseReference myref1 = database.getReference(emailgui+"_history");
            String key=chuyenDi.getNgayDi()+"_"+chuyenDi.getGioDi();
            key=key.replace("/","");
            key=key.replace(":","");
            myref1.child(key).setValue(chuyenDi, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                }
            });
            chuyenDiMap.remove(emailgui);
            DatabaseReference myRef = database.getReference("ds_chuyendi");
            myRef.setValue(chuyenDiMap,new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(DangKyXe.this, "Cập nhật chuyến đi thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void layDlChuyendi(){
        //Lấy dữ  liệu chuyến đi
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database.getReference("ds_chuyendi");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, ChuyenDi> chuyenDiMap1=new HashMap<>();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String key=dataSnapshot.getKey();
                        ChuyenDi chuyenDi=dataSnapshot.getValue(ChuyenDi.class);
                        chuyenDiMap1.put(key,chuyenDi);
//                        Toast.makeText(DangKyXe.this, key, Toast.LENGTH_SHORT).show();
                    }
                }
                chuyenDiMap=chuyenDiMap1;
                setDuLieuForm(chuyenDiMap1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public ChuyenDi setChuyenDi(){
        int kt=1;
        if(etdkBienSoXe.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyXe.this, "Không được để trống biển số xe", Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(etdkGia.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyXe.this, "Không được để trống giá", Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(etdkThoiGian.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyXe.this, "Không được để trống thời gian", Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(etdkHangXe.getText().toString().trim().isEmpty()){
            Toast.makeText(DangKyXe.this, "Không được để trống hãng xe", Toast.LENGTH_SHORT).show();
            kt=0;
        }

        if(kt==1) {
            FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
            String email = userdn.getEmail();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            //Lay thong tin tai xe
            User user = new User();
            String emailgui = email.replace(".", "");
//            try {
//                DatabaseReference myRef2 = database.getReference(emailgui);
//
//                myRef2.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            User user1 = snapshot.getValue(User.class);
//                            user.setDiachi(user1.getDiachi());
//                            user.setEmail(user1.getEmail());
//                            user.setName(user1.getName());
//                            user.setNgaysinh(user1.getNgaysinh());
//                            user.setSodienthoai(user1.getSodienthoai());
//                            user.setVaitro(user1.getVaitro());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            } catch (Exception e) {
//            }

            ChuyenDi chuyenDi = new ChuyenDi();
            chuyenDi.setGhiChu(etdkGhiChu.getText().toString());
            chuyenDi.setBienSoXe(etdkBienSoXe.getText().toString().trim());
            chuyenDi.setGia(Integer.parseInt(etdkGia.getText().toString().trim()));
            chuyenDi.setGioDi(etdkThoiGian.getText().toString().trim());
            chuyenDi.setHangXe(etdkHangXe.getText().toString().trim());
            chuyenDi.setLoaiXe(spdkLoaiXe.getSelectedItem().toString());
            chuyenDi.setTinhTrang(spdkTinhTrang.getSelectedItemPosition());
            chuyenDi.setTinhDen(spdkTinhDen.getSelectedItem().toString());
            chuyenDi.setTinhDi(spdkTinhDi.getSelectedItem().toString());
            chuyenDi.setTaixe(emailgui);
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            chuyenDi.setNgayDi(year + "/" + month + "/" + day);
            return chuyenDi;
        }
        else {
            return null;
        }
    }

    private void setDuLieuForm(Map<String,ChuyenDi> chuyenDiMap1){
//        Toast.makeText(DangKyXe.this, chuyenDiMap1.size()+"", Toast.LENGTH_SHORT).show();
        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
        String email = userdn.getEmail();
        String emailgui = email.replace(".", "");
        if(chuyenDiMap1.containsKey(emailgui)){
//            Toast.makeText(DangKyXe.this, "ok1", Toast.LENGTH_SHORT).show();
            ChuyenDi cd=chuyenDiMap.get(emailgui);
            etdkThoiGian.setText(cd.getGioDi());
            etdkHangXe.setText(cd.getHangXe());
            etdkGia.setText(cd.getGia()+"");
            etdkBienSoXe.setText(cd.getBienSoXe());
            etdkGhiChu.setText(cd.getGhiChu());
            spdkTinhTrang.setSelection(cd.getTinhTrang());
            int vt=0;
            for (int i=0; i<spdkTinhDen.getCount(); i++){
                if(spdkTinhDen.getItemAtPosition(i).equals(cd.getTinhDen())){
                    vt=i;
                    break;
                }
            }
            spdkTinhDen.setSelection(vt);

            for (int i=0; i<spdkTinhDi.getCount(); i++){
                if(spdkTinhDi.getItemAtPosition(i).equals(cd.getTinhDi())){
                    vt=i;
                    break;
                }
            }
            spdkTinhDi.setSelection(vt);

            for (int i=0; i<spdkLoaiXe.getCount(); i++){
                if(spdkLoaiXe.getItemAtPosition(i).equals(cd.getLoaiXe())){
                    vt=i;
                    break;
                }
            }
            spdkLoaiXe.setSelection(vt);
        }
    }
}