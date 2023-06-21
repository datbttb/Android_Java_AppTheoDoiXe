package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.apptheodoixe.adapter.RecyclerViewLichXeAdapter;
import com.example.apptheodoixe.model.ChuyenDi;
import com.example.apptheodoixe.model.LichChay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LichChayXeKhach extends AppCompatActivity {

    private Spinner splcxTinhDi,splcxTinhDen;
    private RecyclerView rclcxkDSXe;
    private Button btnlcxQuayLai,btnlcxTimKiem;
    private Map<String, LichChay> lichChayMap;
    private RecyclerViewLichXeAdapter recyclerViewLichXeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_chay_xe_khach);
        init();
        LinearLayoutManager manager=new LinearLayoutManager(LichChayXeKhach.this,RecyclerView.VERTICAL,false);
        rclcxkDSXe.setLayoutManager(manager);
        rclcxkDSXe.setAdapter(recyclerViewLichXeAdapter);
        layDuLieu();
        btnlcxTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timKiemXe();
            }
        });
        btnlcxQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        splcxTinhDen=findViewById(R.id.splcxTinhDen);
        splcxTinhDi=findViewById(R.id.splcxTinhDi);
        rclcxkDSXe=findViewById(R.id.rclcxkDSXe);
        btnlcxQuayLai=findViewById(R.id.btnlcxQuayLai);
        btnlcxTimKiem=findViewById(R.id.btnlcxTimKiem);
        lichChayMap=new HashMap<>();
        recyclerViewLichXeAdapter=new RecyclerViewLichXeAdapter();
        splcxTinhDen.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        splcxTinhDi.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));

    }

    private void layDuLieu(){
        //Lấy dữ  liệu chuyến đi
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database.getReference("ds_lichxe");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, LichChay> lichChayMap1=new HashMap<>();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String key=dataSnapshot.getKey();
                        LichChay lichChay=dataSnapshot.getValue(LichChay.class);
                        lichChayMap1.put(key,lichChay);
//                        Toast.makeText(TimKiemXe.this, key, Toast.LENGTH_SHORT).show();
                    }
                }
                lichChayMap=lichChayMap1;
                List<LichChay> lichChayList=new ArrayList<>();
                for (Map.Entry<String,LichChay> entry: lichChayMap1.entrySet()){
                    String key=entry.getKey();
                    LichChay lichChay = entry.getValue();
                    if(lichChay.getTinhTrang()==0){
                        lichChayList.add(lichChay);
                    }
                }
                recyclerViewLichXeAdapter.setLichChayList(lichChayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void timKiemXe(){
        if(splcxTinhDi.getSelectedItem().toString().equals("None") && !splcxTinhDen.getSelectedItem().toString().equals("None")){
            List<LichChay> lichChayList=new ArrayList<>();
            for (Map.Entry<String,LichChay> entry: lichChayMap.entrySet()){
                String key=entry.getKey();
                LichChay lichChay = entry.getValue();
                if(lichChay.getTinhTrang()==0 && (lichChay.getTinhDen().equals(splcxTinhDen.getSelectedItem().toString()) || lichChay.getTinhDi().equals(splcxTinhDen.getSelectedItem().toString()))){
                    lichChayList.add(lichChay);
                }
            }
            recyclerViewLichXeAdapter.setLichChayList(lichChayList);
        }

        if(!splcxTinhDi.getSelectedItem().toString().equals("None") && splcxTinhDen.getSelectedItem().toString().equals("None")){
            List<LichChay> lichChayList=new ArrayList<>();
            for (Map.Entry<String,LichChay> entry: lichChayMap.entrySet()){
                String key=entry.getKey();
                LichChay lichChay = entry.getValue();
                if(lichChay.getTinhTrang()==0 && (lichChay.getTinhDen().equals(splcxTinhDi.getSelectedItem().toString()) || lichChay.getTinhDi().equals(splcxTinhDi.getSelectedItem().toString()))){
                    lichChayList.add(lichChay);
                }
            }
            recyclerViewLichXeAdapter.setLichChayList(lichChayList);
        }

        if(!splcxTinhDi.getSelectedItem().toString().equals("None") && !splcxTinhDen.getSelectedItem().toString().equals("None")){
            List<LichChay> lichChayList=new ArrayList<>();
            for (Map.Entry<String,LichChay> entry: lichChayMap.entrySet()){
                String key=entry.getKey();
                LichChay lichChay = entry.getValue();
                if(lichChay.getTinhTrang()==0 &&
                        ((lichChay.getTinhDen().equals(splcxTinhDi.getSelectedItem().toString()) && lichChay.getTinhDi().equals(splcxTinhDen.getSelectedItem().toString())) ||
                                (lichChay.getTinhDi().equals(splcxTinhDi.getSelectedItem().toString()) && lichChay.getTinhDen().equals(splcxTinhDen.getSelectedItem().toString())))){
                    lichChayList.add(lichChay);
                }
            }
            recyclerViewLichXeAdapter.setLichChayList(lichChayList);
        }

        if(splcxTinhDi.getSelectedItem().toString().equals("None") && splcxTinhDen.getSelectedItem().toString().equals("None")){
            List<LichChay> lichChayList=new ArrayList<>();
            for (Map.Entry<String,LichChay> entry: lichChayMap.entrySet()){
                String key=entry.getKey();
                LichChay lichChay = entry.getValue();
                if(lichChay.getTinhTrang()==0){
                    lichChayList.add(lichChay);
                }
            }
            recyclerViewLichXeAdapter.setLichChayList(lichChayList);
        }
    }
}