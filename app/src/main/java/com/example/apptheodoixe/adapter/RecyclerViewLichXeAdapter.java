package com.example.apptheodoixe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptheodoixe.R;
import com.example.apptheodoixe.model.LichChay;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewLichXeAdapter extends RecyclerView.Adapter<RecyclerViewLichXeAdapter.RecyclerViewLichXeHolder> {
    private List<LichChay> lichChayList;
    private ItemListenerLX itemListenerLX;

    public RecyclerViewLichXeAdapter() {
        this.lichChayList=new ArrayList<>();
    }
    public void setItemListenerLX(ItemListenerLX itemListenerLX){
        this.itemListenerLX=itemListenerLX;
    }
    public void setLichChayList(List<LichChay> lichChayList){
        this.lichChayList=lichChayList;
        notifyDataSetChanged();
    }
    public LichChay getLichChay(int vitri){
        return lichChayList.get(vitri);
    }

    @NonNull
    @Override
    public RecyclerViewLichXeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_lichchay,parent,false);
        return new RecyclerViewLichXeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewLichXeHolder holder, int position) {
        LichChay lichChay=getLichChay(position);
        holder.tvilcxDiaDiem.setText(lichChay.getTinhDi()+"-"+lichChay.getTinhDen());
        holder.tvilcxBienSo.setText("Biển số: "+lichChay.getBienSoXe());
        holder.tvilcxGioDi.setText("Giờ đi: "+lichChay.getGioDi());
        holder.tvilcxGioVe.setText("Giờ về: "+lichChay.getGioVe());
        holder.tvilcxGia.setText("Giá: "+lichChay.getGia());
        holder.tvilcxSDT.setText("SDT: "+lichChay.getSoDienThoai());
        holder.tvilcxNhaXe.setText("Nhà xe: "+lichChay.getNhaXe());
    }

    @Override
    public int getItemCount() {
        return lichChayList.size();
    }

    public class RecyclerViewLichXeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvilcxDiaDiem,tvilcxBienSo,tvilcxGioDi,tvilcxGioVe,tvilcxGia,tvilcxSDT,tvilcxNhaXe;

        public RecyclerViewLichXeHolder(@NonNull View itemView) {
            super(itemView);
            tvilcxDiaDiem=itemView.findViewById(R.id.tvilcxDiaDiem);
            tvilcxBienSo=itemView.findViewById(R.id.tvilcxBienSo);
            tvilcxGioDi=itemView.findViewById(R.id.tvilcxGioDi);
            tvilcxGioVe=itemView.findViewById(R.id.tvilcxGioVe);
            tvilcxGia=itemView.findViewById(R.id.tvilcxGia);
            tvilcxSDT=itemView.findViewById(R.id.tvilcxSDT);
            tvilcxNhaXe=itemView.findViewById(R.id.tvilcxNhaXe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListenerLX.onItemLXClick(itemView,getAdapterPosition());
        }
    }

    public interface ItemListenerLX{
        void onItemLXClick(View view,int position);
    }
}
