package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 10/02/2020.
 */

public class GoupFood implements Serializable {
    @SerializedName("ID")
    private int ID;
    @SerializedName("ID_USER")
    private int ID_USER;
    @SerializedName("NAME_GOUP")
    private String NAME_GOUP;


    public GoupFood(int ID, int ID_USER, String NAME_GOUP) {
        this.ID = ID;
        this.ID_USER = ID_USER;
        this.NAME_GOUP = NAME_GOUP;

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

    public String getNAME_GOUP() {
        return NAME_GOUP;
    }

    public void setNAME_GOUP(String NAME_GOUP) {
        this.NAME_GOUP = NAME_GOUP;
    }

}