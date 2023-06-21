package com.example.apptheodoixe.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apptheodoixe.Login;
import com.example.apptheodoixe.R;
import com.example.apptheodoixe.ThayDoiMatKhau;
import com.example.apptheodoixe.ThongTinTaiKhoan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentCaNhan extends Fragment {

    private TextView tvcnName, tvcnChinhSua, tvcnThayDoiMK;
    private Button btncnDangXuat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvcnName=view.findViewById(R.id.tvcnName);
        btncnDangXuat=view.findViewById(R.id.btncnDangXuat);
        tenNguoiDung();
        btncnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

//        tvcnName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(getActivity(), ThongTinTaiKhoan.class);
////                startActivity(intent);
//                Toast.makeText(getContext(), "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
//            }
//        });

        tvcnChinhSua=view.findViewById(R.id.tvcnChinhSua);
        tvcnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ThongTinTaiKhoan.class);
                startActivity(intent);
            }
        });

        tvcnThayDoiMK=view.findViewById(R.id.tvcnThayDoiMK);
        tvcnThayDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ThayDoiMatKhau.class);
                startActivity(intent);
            }
        });

    }


    private void tenNguoiDung(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            tvcnName.setText("Xin Chào: "+email);
        }
    }
}
