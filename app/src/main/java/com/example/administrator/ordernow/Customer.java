package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 13/12/2019.
 */

public class Customer implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("ID_USER")
    private int ID_USER;
    @SerializedName("NAME_CUSTOMER")
    private String NAME_CUSTOMER;
    @SerializedName("CODE_CUSTOMER")
    private String CODE_CUSTOMER;
    @SerializedName("PHONE")
    private int PHONE;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("BIRTHDAY")
    private String BIRTHDAY;
    @SerializedName("SEX")
    private int SEX;
    @SerializedName("SCORE")
    private int SCORE;
    @SerializedName("LEVEL")
    private int LEVEL;
    @SerializedName("PRICE_SALE")
    private int PRICE_SALE;

    public Customer(int ID, int ID_USER, String NAME_CUSTOMER, String CODE_CUSTOMER, int PHONE, String EMAIL, String BIRTHDAY, int SEX, int SCORE, int LEVEL, int PRICE_SALE) {
        this.ID = ID;
        this.ID_USER = ID_USER;
        this.NAME_CUSTOMER = NAME_CUSTOMER;
        this.CODE_CUSTOMER = CODE_CUSTOMER;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
        this.BIRTHDAY = BIRTHDAY;
        this.SEX = SEX;
        this.SCORE = SCORE;
        this.LEVEL = LEVEL;
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

    public String getNAME_CUSTOMER() {
        return NAME_CUSTOMER;
    }
    public void setNAME_CUSTOMER(String NAME_CUSTOMER) {
        this.NAME_CUSTOMER = NAME_CUSTOMER;
    }

    public String getCODE_CUSTOMER() {
        return CODE_CUSTOMER;
    }
    public void setCODE_CUSTOMER(String CODE_CUSTOMER) {
        this.CODE_CUSTOMER = CODE_CUSTOMER;
    }

    public int getPHONE() {
        return PHONE;
    }
    public void setPHONE(int PHONE) {
        this.PHONE = PHONE;
    }

    public String getBIRTHDAY() {
        return BIRTHDAY;
    }
    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public int getLEVEL() {
        return LEVEL;
    }
    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;

    }public int getSEX() {
        return SEX;
    }

    public void setSEX(int SEX) {
        this.SEX = SEX;
    }
    public int getSCORE() {
        return SCORE;
    }

    public void setSCORE(int SCORE) {
        this.SCORE = SCORE;
    }
    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
    public int getPRICE_SALE() {
        return PRICE_SALE;
    }

    public void setPRICE_SALE(int PRICE_SALE) {
        this.PRICE_SALE = PRICE_SALE;
    }
}
