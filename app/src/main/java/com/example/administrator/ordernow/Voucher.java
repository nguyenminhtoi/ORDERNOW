package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 12/12/2019.
 */

public class Voucher implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("ID_USER")
    private int ID_USER;
    @SerializedName("NAME_VOUCHER")
    private String NAME_VOUCHER;
    @SerializedName("CODE_VOUCHER")
    private String CODE_VOUCHER;
    @SerializedName("PRICE_SALE")
    private String PRICE_SALE;


    public Voucher(int ID, int ID_USER, String NAME_VOUCHER, String CODE_VOUCHER, String PRICE_SALE) {
        this.ID = ID;
        this.ID_USER = ID_USER;
        this.NAME_VOUCHER = NAME_VOUCHER;
        this.CODE_VOUCHER = CODE_VOUCHER;
        this.PRICE_SALE = PRICE_SALE;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_USER() {
        return ID_USER;
    }
    public void setID_USER(int ID_USER) {
        this.ID_USER = ID_USER;
    }

    public String getNAME_VOUCHER() {
        return NAME_VOUCHER;
    }
    public void setNAME_VOUCHER(String NAME_VOUCHER) {
        this.NAME_VOUCHER = NAME_VOUCHER;
    }

    public String getCODE_VOUCHER() {
        return CODE_VOUCHER;
    }
    public void setCODE_VOUCHER(String CODE_VOUCHER) {
        this.CODE_VOUCHER = CODE_VOUCHER;
    }

    public String getPRICE_SALE() {
        return PRICE_SALE;
    }
    public void setPRICE_SALE(String PRICE_SALE) {
        this.PRICE_SALE = PRICE_SALE;
    }

}