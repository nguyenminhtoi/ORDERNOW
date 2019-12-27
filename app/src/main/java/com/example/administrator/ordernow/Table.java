package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.provider.Telephony.Carriers.PASSWORD;

/**
 * Created by Administrator on 11/12/2019.
 */

public class Table implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("ID_USER")
    private int ID_USER;
    @SerializedName("NAME_TABLE")
    private String NAME_TABLE;


    public Table(int ID, int ID_USER, String NAME_TABLE) {
        this.ID = ID;
        this.ID_USER = ID_USER;
        this.NAME_TABLE = NAME_TABLE;

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

    public String getNAME_TABLE() {
        return NAME_TABLE;
    }

    public void setNAME_TABLE(String NAME_TABLE) {
        this.NAME_TABLE = NAME_TABLE;
    }

}