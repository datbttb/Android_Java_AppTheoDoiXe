package com.example.apptheodoixe.model;

public class LichChay {
    private String tinhDi, tinhDen, gioDi, gioVe, nhaXe, bienSoXe, soDienThoai;
    private int gia, tinhTrang;

    public LichChay() {
    }

    public LichChay(String tinhDi, String tinhDen, String gioDi, String gioVe, String nhaXe, String bienSoXe, String soDienThoai, int gia, int tinhTrang) {
        this.tinhDi = tinhDi;
        this.tinhDen = tinhDen;
        this.gioDi = gioDi;
        this.gioVe = gioVe;
        this.nhaXe = nhaXe;
        this.bienSoXe = bienSoXe;
        this.soDienThoai = soDienThoai;
        this.gia = gia;
        this.tinhTrang=tinhTrang;
    }

    public String getTinhDi() {
        return tinhDi;
    }

    public void setTinhDi(String tinhDi) {
        this.tinhDi = tinhDi;
    }

    public String getTinhDen() {
        return tinhDen;
    }

    public void setTinhDen(String tinhDen) {
        this.tinhDen = tinhDen;
    }

    public String getGioDi() {
        return gioDi;
    }

    public void setGioDi(String gioDi) {
        this.gioDi = gioDi;
    }

    public String getGioVe() {
        return gioVe;
    }

    public void setGioVe(String gioVe) {
        this.gioVe = gioVe;
    }

    public String getNhaXe() {
        return nhaXe;
    }

    public void setNhaXe(String nhaXe) {
        this.nhaXe = nhaXe;
    }

    public String getBienSoXe() {
        return bienSoXe;
    }

    public void setBienSoXe(String bienSoXe) {
        this.bienSoXe = bienSoXe;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
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
}
