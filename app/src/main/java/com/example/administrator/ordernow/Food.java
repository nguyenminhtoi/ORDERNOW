package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.provider.ContactsContract.CommonDataKinds.Note.NOTE;

/**
 * Created by Administrator on 16/12/2019.
 */

public class Food implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("ID_USER")
    private int ID_USER;
    @SerializedName("NAME_FOOD")
    private String NAME_FOOD;
    @SerializedName("IMAGE")
    private String IMAGE;
    @SerializedName("PRICE")
    private int PRICE;
    @SerializedName("NUMBER")
    private int NUMBER;
    @SerializedName("NOTE")
    private String NOTE;


    public Food(int ID, int ID_USER, String NAME_FOOD, String IMAGE, int PRICE, int NUMBER, String NOTE) {
        this.ID = ID;
        this.ID_USER = ID_USER;
        this.NAME_FOOD = NAME_FOOD;
        this.IMAGE = IMAGE;
        this.PRICE = PRICE;
        this.NUMBER = NUMBER;
        this.NOTE = NOTE;

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

    public String getNAME_FOOD() {
        return NAME_FOOD;
    }
    public void setNAME_FOOD(String NAME_FOOD) {
        this.NAME_FOOD = NAME_FOOD;
    }

    public String getIMAGE() {
        return IMAGE;
    }
    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public int getPRICE() {
        return PRICE;
    }
    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public int getNUMBER() {
        return NUMBER;
    }
    public void setNUMBER(int NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getNOTE() {
        return NOTE;
    }
    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

}
