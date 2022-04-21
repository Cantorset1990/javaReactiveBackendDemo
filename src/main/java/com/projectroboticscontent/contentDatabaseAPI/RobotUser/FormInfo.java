package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInfo;
import org.springframework.data.mongodb.core.mapping.Field;

public class FormInfo {

    @Field
    private String FORM_OWNER;

    @Field
    private String FORM_TITLE;


    @Field
    private long FORM_ID;

    @Field
    private long USER_PRIMARY_KEY;

    @Field
    private String USER_WALLET_ID;

    @Field
    private String DATETIME;

    @Field
    private long STATUS;


    public FormInfo(String FORM_TITLE,  long FORM_ID,
                    String FORM_OWNER, long USER_PRIMARY_KEY,
                    String DATETIME, String  USER_WALLET_ID, long STATUS) {
        this.FORM_TITLE = FORM_TITLE;
        //this.JSON_FORM_DATA = JSON_FORM_DATA;
        this.FORM_ID = FORM_ID;
        this.USER_PRIMARY_KEY = USER_PRIMARY_KEY;
        this.FORM_OWNER = FORM_OWNER;
        this.DATETIME = DATETIME;
        this.USER_WALLET_ID = USER_WALLET_ID;
        this.STATUS = STATUS;
    }

    public String getFORM_TITLE() {
        return FORM_TITLE;
    }



    public long getFORM_ID() {
        return FORM_ID;
    }

    public void setFORM_TITLE(String FORM_TITLE) {
        this.FORM_TITLE = FORM_TITLE;
    }



    public String getFORM_OWNER() {
        return FORM_OWNER;
    }

    public void setFORM_OWNER(String FORM_OWNER) {
        this.FORM_OWNER = FORM_OWNER;
    }



    public FormInfo getInfo() {
        return this;
    }

    public void setFORM_ID(long FORM_ID) {
        this.FORM_ID = FORM_ID;
    }



    public String getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(String DATETIME) {
        this.DATETIME = DATETIME;
    }

    public long getUSER_PRIMARY_KEY() {
        return USER_PRIMARY_KEY;
    }

    public void setUSER_PRIMARY_KEY(long USER_PRIMARY_KEY) {
        this.USER_PRIMARY_KEY = USER_PRIMARY_KEY;
    }

    public String getUSER_WALLET_ID() {
        return USER_WALLET_ID;
    }

    public void setUSER_WALLET_ID(String USER_WALLET_ID) {
        this.USER_WALLET_ID = USER_WALLET_ID;
    }

    public long getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(long STATUS) {
        this.STATUS = STATUS;
    }
}
