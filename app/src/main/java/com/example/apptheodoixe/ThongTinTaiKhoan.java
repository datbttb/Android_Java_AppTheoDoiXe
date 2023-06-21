package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptheodoixe.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ThongTinTaiKhoan extends AppCompatActivity {

    private EditText ettkName,ettkEmail,ettkPhoneNumber,ettkBirthday,ettkAddress;
    private Spinner sptkVaiTro;
    private Button btntkCapNhat, btntkQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        init();
        getData();
        ettkBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(ThongTinTaiKhoan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date="";
                        if(m>8){
                            date=d+"/"+(m+1)+"/"+y;
                        }
                        else {
                            date=d+"/0"+(m+1)+"/"+y;
                        }
                        ettkBirthday.setText(date);
                    }
                },year,month,day);
                dialog.show();
            }
        });

        btntkCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        btntkQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        ettkName=findViewById(R.id.ettkName);
        ettkEmail=findViewById(R.id.ettkEmail);
        ettkPhoneNumber=findViewById(R.id.ettkPhoneNumber);
        ettkBirthday=findViewById(R.id.ettkBirthday);
        ettkAddress=findViewById(R.id.ettkAddress);
        sptkVaiTro=findViewById(R.id.sptkVaiTro);
        btntkCapNhat = findViewById(R.id.btntkCapNhat);
        btntkQuayLai = findViewById(R.id.btntkQuayLai);
        sptkVaiTro.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.vaitro)));
    }

    private void getData(){
        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
        String email="";
        String emailgui=email.replace(".","");
        if(userdn!=null){
            email = userdn.getEmail();
            ettkEmail.setText(email);
            emailgui=email.replace(".","");
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            DatabaseReference myRef = database.getReference(emailgui);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            ettkName.setText(user.getName());
                            ettkBirthday.setText(user.getNgaysinh());
                            ettkAddress.setText(user.getDiachi());
                            ettkPhoneNumber.setText(user.getSodienthoai());
                            int vt = 0;
                            for (int i = 0; i < sptkVaiTro.getCount(); i++) {
                                if (sptkVaiTro.getItemAtPosition(i).equals(user.getVaitro())) {
                                    vt = i;
                                }
                            }
                            sptkVaiTro.setSelection(vt);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
        }
    }

    private void sendData(){
        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
        String email=userdn.getEmail();
        String emailgui=email.replace(".","");
        int kt=1;
        if(sptkVaiTro.getSelectedItemPosition()==0){
            if(ettkName.getText().toString().trim().isEmpty()){
                Toast.makeText(ThongTinTaiKhoan.this, "Không được để trống tên", Toast.LENGTH_SHORT).show();
                kt=0;
            }
            if(ettkBirthday.getText().toString().trim().isEmpty()){
                Toast.makeText(ThongTinTaiKhoan.this, "Không được để trống ngày sinh", Toast.LENGTH_SHORT).show();
                kt=0;
            }
            if(ettkAddress.getText().toString().trim().isEmpty()){
                Toast.makeText(ThongTinTaiKhoan.this, "Không được để trống địa chỉ", Toast.LENGTH_SHORT).show();
                kt=0;
            }
            if(ettkPhoneNumber.getText().toString().trim().isEmpty()){
                Toast.makeText(ThongTinTaiKhoan.this, "Không được để trống số điện thoại", Toast.LENGTH_SHORT).show();
                kt=0;
            }
        }
        if(kt==1) {
            User user = new User(userdn.getEmail(), ettkName.getText().toString().trim(),
                    ettkPhoneNumber.getText().toString().trim(), ettkBirthday.getText().toString(), ettkAddress.getText().toString(), sptkVaiTro.getSelectedItem().toString());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(emailgui);
            myRef.setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(ThongTinTaiKhoan.this, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}