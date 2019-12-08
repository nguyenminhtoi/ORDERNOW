package com.example.administrator.ordernow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.R.attr.id;
import static android.R.attr.password;
import static android.os.Build.ID;

/**
 * Created by MINH TOI on 20/01/2018.
 */

public class User implements Serializable {
    @SerializedName("id")
    private int ID;
    @SerializedName("USER")
    private String USER;
    @SerializedName("PASSWORD")
    private String PASSWORD;
    @SerializedName("FULLNAME")
    private String FULLNAME;
    @SerializedName("NAME_STORE")
    private String NAME_STORE;
    @SerializedName("BIRTHDAY")
    private String BIRTHDAY;
    @SerializedName("ADDRESS")
    private String ADDRESS;
    @SerializedName("SEX")
    private int SEX;
    @SerializedName("PHONE")
    private int PHONE;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("ROLE")
    private int ROLE;
    @SerializedName("ID_CREATED")
    private int ID_CREATED;

    public User(int ID, String USER, String PASSWORD, String FULLNAME, String NAME_STORE, String BIRTHDAY, String ADDRESS, int SEX, int PHONE, String EMAIL, int ROLE, int ID_CREATED) {
        this.ID = ID;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.FULLNAME = FULLNAME;
        this.NAME_STORE = NAME_STORE;
        this.BIRTHDAY = BIRTHDAY;
        this.ADDRESS = ADDRESS;
        this.SEX = SEX;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
        this.ROLE = ROLE;
        this.ID_CREATED = ID_CREATED;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }
    public String getNAME_STORE() {
        return NAME_STORE;
    }

    public void setNAME_STORE(String NAME_STORE) {
        this.NAME_STORE = NAME_STORE;

    }public String getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;

    }public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;

    }public int getSEX() {
        return SEX;
    }

    public void setSEX(int SEX) {
        this.SEX = SEX;
    }
    public int getPHONE() {
        return PHONE;
    }

    public void setPHONE(int PHONE) {
        this.PHONE = PHONE;
    }
    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
    public int getROLE() {
        return ROLE;
    }

    public void setROLE(int ROLE) {
        this.ROLE = ROLE;
    }
    public int getID_CREATED() {
        return ID_CREATED;
    }

    public void setID_CREATED(int ID_CREATED) {
        this.ID_CREATED = ID_CREATED;
    }
}
