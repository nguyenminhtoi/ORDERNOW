package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by MINH TOI on 20/01/2018.
 */

public class NguoiDung implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("TAIKHOAN")
    private String TAIKHOAN;
    @SerializedName("MATKHAU")
    private String MATKHAU;
    @SerializedName("TEN")
    private String TEN;
    @SerializedName("NGAYSINH")
    private String NGAYSINH;
    @SerializedName("CMND")
    private String CMND;
    @SerializedName("DIACHI")
    private String DIACHI;
    @SerializedName("GIOITINH")
    private String GIOITINH;
    @SerializedName("SDT")
    private String SDT;
    @SerializedName("NGHENGHIEP")
    private String NGHENGHIEP;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("QUYEN")
    private String QUYEN;

    public NguoiDung(int ID, String TAIKHOAN, String MATKHAU, String TEN, String NGAYSINH, String CMND, String DIACHI, String GIOITINH, String SDT, String NGHENGHIEP, String EMAIL, String QUYEN) {
        this.ID = ID;
        this.TAIKHOAN = TAIKHOAN;
        this.MATKHAU = MATKHAU;
        this.TEN = TEN;
        this.NGAYSINH = NGAYSINH;
        this.CMND = CMND;
        this.DIACHI = DIACHI;
        this.GIOITINH = GIOITINH;
        this.SDT = SDT;
        this.NGHENGHIEP = NGHENGHIEP;
        this.EMAIL = EMAIL;
        this.QUYEN = QUYEN;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getTAIKHOAN() {
        return TAIKHOAN;
    }

    public void setTAIKHOAN(String TAIKHOAN) {
        this.TAIKHOAN = TAIKHOAN;
    }
    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }
    public String getTEN() {
        return TEN;
    }

    public void setTEN(String TEN) {
        this.TEN = TEN;
    }
    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;

    }public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.ID = ID;

    }public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;

    }public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }
    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    public String getNGHENGHIEP() {
        return NGHENGHIEP;
    }

    public void setNGHENGHIEP(String NGHENGHIEP) {
        this.NGHENGHIEP = NGHENGHIEP;
    }
    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
    public String getQUYEN() {
        return QUYEN;
    }

    public void setQUYEN(String QUYEN) {
        this.QUYEN = QUYEN;
    }
}
