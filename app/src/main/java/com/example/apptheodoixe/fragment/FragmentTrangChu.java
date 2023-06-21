package com.example.apptheodoixe.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apptheodoixe.DangKyLichChay;
import com.example.apptheodoixe.DangKyXe;
import com.example.apptheodoixe.LichChayXeKhach;
import com.example.apptheodoixe.R;
import com.example.apptheodoixe.ThayDoiMatKhau;
import com.example.apptheodoixe.ThongTinTaiKhoan;
import com.example.apptheodoixe.TimKiemXe;
import com.example.apptheodoixe.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentTrangChu extends Fragment {

    private Button btntcDangKyXe,btntcXeMay,btntcOTo,btntcXeKhach,btntcDangKyLich;
    private User userDangNhap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trangchu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDangNhap=new User();
        btntcXeMay=view.findViewById(R.id.btntcXeMay);
        btntcOTo=view.findViewById(R.id.btntcOTo);
        btntcXeKhach=view.findViewById(R.id.btntcXeKhach);
        btntcDangKyLich=view.findViewById(R.id.btntcDangKyLich);
        btntcDangKyXe = view.findViewById(R.id.btntcDangKyXe);

        btntcDangKyLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DangKyLichChay.class);
                startActivity(intent);
            }
        });

        btntcDangKyXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DangKyXe.class);
                startActivity(intent);
            }
        });

        btntcXeMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TimKiemXe.class);
                intent.putExtra("loaixe","Xe máy");
                startActivity(intent);
            }
        });
        btntcOTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TimKiemXe.class);
                intent.putExtra("loaixe","Ô tô");
                startActivity(intent);
            }
        });
        btntcXeKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LichChayXeKhach.class);
                startActivity(intent);
            }
        });
    }

//    private void getUser(){
//        FirebaseUser userdn = FirebaseAuth.getInstance().getCurrentUser();
//        String email="";
//        String emailgui=email.replace(".","");
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference(emailgui);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    User user = snapshot.getValue(User.class);
//                    if (user != null) {
//                        userDangNhap=user;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


}
