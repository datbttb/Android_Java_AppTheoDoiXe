package com.example.apptheodoixe.model;

public class User {
    private String email, name, sodienthoai, ngaysinh, diachi, vaitro;


    public User() {
    }

    public User(String email, String name, String sodienthoai, String ngaysinh, String diachi, String vaitro) {
        this.email = email;
        this.name = name;
        this.sodienthoai = sodienthoai;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.vaitro = vaitro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }
}
