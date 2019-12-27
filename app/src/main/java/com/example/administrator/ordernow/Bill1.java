package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 25/12/2019.
 */

public class Bill1 implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("ID_USER")
    private int ID_USER;
    @SerializedName("NAME_BILL")
    private String NAME_BILL;
    @SerializedName("ID_TABLE")
    private String ID_TABLE;
    @SerializedName("ID_FOOD")
    private String ID_FOOD;
    @SerializedName("ID_VOUCHER")
    private int ID_VOUCHER;
    @SerializedName("ID_CUSTOMER")
    private int ID_CUSTOMER;
    @SerializedName("ID_CREATED")
    private int ID_CREATED;
    @SerializedName("NOTE")
    private String NOTE;
    @SerializedName("TOTAL_PRICE")
    private int TOTAL_PRICE;
    @SerializedName("STATUS")
    private int STATUS;
    @SerializedName("TIME_CREATED")
    private String TIME_CREATED;


    public Bill1(int ID, int ID_USER, String NAME_BILL, String ID_TABLE, String ID_FOOD, int ID_VOUCHER, int ID_CUSTOMER, int ID_CREATED, String NOTE, int TOTAL_PRICE, int STATUS, String TIME_CREATED) {
        this.ID = ID;
        this.ID_USER = ID_USER;
        this.NAME_BILL = NAME_BILL;
        this.ID_TABLE = ID_TABLE;
        this.ID_FOOD = ID_FOOD;
        this.ID_VOUCHER = ID_VOUCHER;
        this.ID_CUSTOMER = ID_CUSTOMER;
        this.ID_CREATED = ID_CREATED;
        this.NOTE = NOTE;
        this.TOTAL_PRICE = TOTAL_PRICE;
        this.STATUS = STATUS;
        this.TIME_CREATED = TIME_CREATED;

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

    public String getNAME_BILL() {
        return NAME_BILL;
    }
    public void setNAME_BILL(String NAME_BILL) {
        this.NAME_BILL = NAME_BILL;
    }

    public String getID_TABLE() {
        return ID_TABLE;
    }
    public void setID_TABLE(String ID_TABLE) {
        this.ID_TABLE = ID_TABLE;
    }

    public String getID_FOOD() {
        return ID_FOOD;
    }
    public void setID_FOOD(String ID_FOOD) {
        this.ID_FOOD = ID_FOOD;
    }

    public int getID_VOUCHER() {
        return ID_VOUCHER;
    }
    public void setID_VOUCHER(int ID_VOUCHER) {
        this.ID_VOUCHER = ID_VOUCHER;
    }

    public int getID_CUSTOMER() {
        return ID_CUSTOMER;
    }
    public void setID_CUSTOMER(int ID_CUSTOMER) {
        this.ID_CUSTOMER = ID_CUSTOMER;
    }

    public int getID_CREATED() {
        return ID_CREATED;
    }
    public void setID_CREATED(int ID_CREATED) {
        this.ID_CREATED = ID_CREATED;
    }

    public String getNOTE() {
        return NOTE;
    }
    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public int getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }
    public void setTOTAL_PRICE(int TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public int getSTATUS() {
        return STATUS;
    }
    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getTIME_CREATED() {
        return TIME_CREATED;
    }
    public void setTIME_CREATED(String TIME_CREATED) {
        this.TIME_CREATED = TIME_CREATED;
    }

}
