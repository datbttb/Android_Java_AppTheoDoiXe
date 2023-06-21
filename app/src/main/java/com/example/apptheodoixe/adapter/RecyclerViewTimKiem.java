package com.example.apptheodoixe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptheodoixe.R;
import com.example.apptheodoixe.model.ChuyenDi;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTimKiem extends RecyclerView.Adapter<RecyclerViewTimKiem.RecyclerViewTK> {

    private List<ChuyenDi> chuyenDiList;
    private ItemListener itemListener;

    public RecyclerViewTimKiem(){
        this.chuyenDiList = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener){
        this.itemListener=itemListener;
    }

    public void setList(List<ChuyenDi> chuyenDiList){
        this.chuyenDiList=chuyenDiList;
        notifyDataSetChanged();
    }

    public ChuyenDi getCD(int vitri){
        return chuyenDiList.get(vitri);
    }

    @NonNull
    @Override
    public RecyclerViewTK onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc,parent,false);
        return new RecyclerViewTK(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewTK holder, int position) {
        ChuyenDi chuyenDi=getCD(position);
        holder.tvircxLoaiXe.setText("Loại xe: "+chuyenDi.getLoaiXe());
        holder.tvircxGio.setText("Giờ đi: "+chuyenDi.getGioDi());
        holder.tvircxGia.setText("Giá: "+chuyenDi.getGia());
        holder.tvircxBienSo.setText("Biển số: "+chuyenDi.getBienSoXe());
        String dendi=chuyenDi.getTinhDi()+"-"+chuyenDi.getTinhDen();
        holder.tvircxDenDi.setText(dendi);
    }

    @Override
    public int getItemCount() {
        return chuyenDiList.size();
    }


    public class RecyclerViewTK  extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvircxDenDi,tvircxBienSo,tvircxGio,tvircxGia,tvircxLoaiXe;
        public RecyclerViewTK(@NonNull View itemView) {
            super(itemView);
            tvircxDenDi=itemView.findViewById(R.id.tvircxDenDi);
            tvircxBienSo=itemView.findViewById(R.id.tvircxBienSo);
            tvircxGia=itemView.findViewById(R.id.tvircxGia);
            tvircxGio=itemView.findViewById(R.id.tvircxGio);
            tvircxLoaiXe=itemView.findViewById(R.id.tvircxLoaiXe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.onItemClick(itemView,getAdapterPosition());
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
