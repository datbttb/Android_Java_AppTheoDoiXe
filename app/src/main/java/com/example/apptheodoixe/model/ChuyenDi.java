package com.example.apptheodoixe.model;

import java.io.Serializable;

public class ChuyenDi implements Serializable {
    private String tinhDen, tinhDi, loaiXe, ghiChu, gioDi, ngayDi, bienSoXe, hangXe, taixe;
    private int gia, tinhTrang;

    public ChuyenDi() {
    }

    public ChuyenDi(String tinhDi, String tinhDen, int gia, String ghiChu, String gioDi, String ngayDi, int tinhTrang, String bienSoXe, String hangXe, String loaiXe, String taixe) {
        this.tinhDen = tinhDen;
        this.tinhDi = tinhDi;
        this.tinhTrang = tinhTrang;
        this.loaiXe = loaiXe;
        this.ghiChu = ghiChu;
        this.gioDi = gioDi;
        this.ngayDi = ngayDi;
        this.bienSoXe = bienSoXe;
        this.hangXe = hangXe;
        this.gia = gia;
        this.taixe = taixe;
    }

    public String getTinhDen() {
        return tinhDen;
    }

    public void setTinhDen(String tinhDen) {
        this.tinhDen = tinhDen;
    }

    public String getTinhDi() {
        return tinhDi;
    }

    public void setTinhDi(String tinhDi) {
        this.tinhDi = tinhDi;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


    public String getBienSoXe() {
        return bienSoXe;
    }

    public void setBienSoXe(String bienSoXe) {
        this.bienSoXe = bienSoXe;
    }

    public String getHangXe() {
        return hangXe;
    }

    public void setHangXe(String hangXe) {
        this.hangXe = hangXe;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getGioDi() {
        return gioDi;
    }

    public void setGioDi(String gioDi) {
        this.gioDi = gioDi;
    }

    public String getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(String ngayDi) {
        this.ngayDi = ngayDi;
    }

    public String getTaixe() {
        return taixe;
    }
    public void setTaixe(String taixe) {
        this.taixe = taixe;
    }
}
