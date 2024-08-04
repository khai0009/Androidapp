package com.example.anew;

public class User {
    private String hoTen;
    private String gioiTinh;
    private String phoneNumber;
    private String diaChi;
    private String cccd;
    private String email;

    private byte[] img;
    public User(){

    }
    public User(String hoTen, String gioiTinh, String phoneNumber, String diaChi,
                String cccd, String email,byte[] img){
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.phoneNumber = phoneNumber;
        this.diaChi = diaChi;
        this.cccd = cccd;
        this.email = email;
        this.img = img;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCCCD() {
        return cccd;
    }

    public void setCCCD(String cccd) {
        this.cccd = cccd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getImg() {return img;}
    public void setImg(byte[] img) {this.img = img;}
}
