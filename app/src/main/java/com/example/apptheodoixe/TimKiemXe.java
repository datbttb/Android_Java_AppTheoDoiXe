package com.example.apptheodoixe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apptheodoixe.adapter.RecyclerViewTimKiem;
import com.example.apptheodoixe.model.ChuyenDi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimKiemXe extends AppCompatActivity implements RecyclerViewTimKiem.ItemListener {

    private Spinner sptkxTinhDi, sptkxTinhDen;
    private Button btntkxQuayLai, btntkxTimKiem;
    private RecyclerView rctkxDanhSach;
    private Map<String,ChuyenDi> chuyenDiMap;
    private RecyclerViewTimKiem recyclerViewTimKiem;
    private String loaixe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_xe);
        init();
        layDlChuyendi();

        LinearLayoutManager manager=new LinearLayoutManager(TimKiemXe.this,RecyclerView.VERTICAL,false);
        rctkxDanhSach.setLayoutManager(manager);
        rctkxDanhSach.setAdapter(recyclerViewTimKiem);
        recyclerViewTimKiem.setItemListener(this);
        btntkxTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timKiemXe();
            }
        });
        btntkxQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init(){
        sptkxTinhDen=findViewById(R.id.sptkxTinhDen);
        sptkxTinhDi=findViewById(R.id.sptkxTinhDi);
        btntkxTimKiem=findViewById(R.id.btntkxTimKiem);
        btntkxQuayLai=findViewById(R.id.btntkxQuayLai);
        rctkxDanhSach=findViewById(R.id.rctkxDanhSach);
        sptkxTinhDi.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        sptkxTinhDen.setAdapter(new ArrayAdapter<String>(this,R.layout.item_sp_vaitro,getResources().getStringArray(R.array.tinh_thanh)));
        chuyenDiMap=new HashMap<>();
        recyclerViewTimKiem=new RecyclerViewTimKiem();
        Intent intent=getIntent();
        loaixe=(String) intent.getSerializableExtra("loaixe");
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
//                        Toast.makeText(TimKiemXe.this, key, Toast.LENGTH_SHORT).show();
                    }
                }
                chuyenDiMap=chuyenDiMap1;
                List<ChuyenDi> chuyenDiList=new ArrayList<>();
                for (Map.Entry<String,ChuyenDi> entry: chuyenDiMap.entrySet()){
                    String key=entry.getKey();
                    ChuyenDi chuyenDi = entry.getValue();
                    if(chuyenDi.getLoaiXe().equals(loaixe)){
                            chuyenDiList.add(chuyenDi);
                    }
                }
                recyclerViewTimKiem.setList(chuyenDiList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ChuyenDi chuyenDi=recyclerViewTimKiem.getCD(position);
        Intent intent = new Intent(TimKiemXe.this,ThongTinChuyenDi.class);
        intent.putExtra("chuyendi",chuyenDi);
        startActivity(intent);
    }

    private void timKiemXe(){
        String dienDen=sptkxTinhDen.getSelectedItem().toString();
        String diemDi=sptkxTinhDi.getSelectedItem().toString();
        List<ChuyenDi> chuyenDiList=new ArrayList<>();
        for (Map.Entry<String,ChuyenDi> entry: chuyenDiMap.entrySet()){
            String key=entry.getKey();
            ChuyenDi chuyenDi = entry.getValue();
            if(chuyenDi.getLoaiXe().equals(loaixe)){
                int kt=1;
                if(!dienDen.equals("None") && !dienDen.equals(chuyenDi.getTinhDen())){
                    kt=0;
                }
                if(!diemDi.equals("None") && !diemDi.equals(chuyenDi.getTinhDi())){
                    kt=0;
                }
                if(kt==1) {
                    chuyenDiList.add(chuyenDi);
                }
            }
        }
        recyclerViewTimKiem.setList(chuyenDiList);
    }
}