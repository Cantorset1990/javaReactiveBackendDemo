package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.google.common.hash.Hashing;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormInfo;
import org.springframework.data.mongodb.core.mapping.Field;

import java.nio.charset.StandardCharsets;

public class SolutionInfo {

    @Field
    private String SOLUTION_OWNER;

    @Field
    private String SOLUTION_TITLE;


    @Field
    private long STATUS;

    @Field
    private long SOLUTION_ID;

    @Field
    private long FORM_ID;

    @Field
    private long SOLUTION_PROVIDER_PRIMARY_KEY;

    @Field
    private String SOLUTION_PROVIDER_WALLET_ID;

    @Field
    private String DATETIME;

    @Field
    private long USER_PRIMARY_KEY;

    @Field
    private String USER_WALLET_ID;

    @Field
    private String HASH;

    @Field
    private String JSON_SOLUTION_DATA;


    public SolutionInfo(String SOLUTION_OWNER, String SOLUTION_TITLE, long SOLUTION_ID,
                        long FORM_ID, long SOLUTION_PROVIDER_PRIMARY_KEY, String DATETIME, long STATUS,
                        String SOLUTION_PROVIDER_WALLET_ID, long USER_PRIMARY_KEY,
                        String USER_WALLET_ID, String JSON_SOLUTION_DATA) {

        this.SOLUTION_OWNER = SOLUTION_OWNER;
        this.SOLUTION_TITLE = SOLUTION_TITLE;
        this.SOLUTION_ID = SOLUTION_ID;
        this.FORM_ID = FORM_ID;
        this.SOLUTION_PROVIDER_PRIMARY_KEY = SOLUTION_PROVIDER_PRIMARY_KEY;
        this.DATETIME = DATETIME;
        this.STATUS = STATUS;
        this.SOLUTION_PROVIDER_WALLET_ID = SOLUTION_PROVIDER_WALLET_ID;
        this.USER_PRIMARY_KEY = USER_PRIMARY_KEY;
        this.USER_WALLET_ID = USER_WALLET_ID;
        this.JSON_SOLUTION_DATA = JSON_SOLUTION_DATA;

        String originalString = SOLUTION_OWNER + SOLUTION_TITLE + SOLUTION_ID
                                + FORM_ID + SOLUTION_PROVIDER_PRIMARY_KEY + DATETIME
                                + SOLUTION_PROVIDER_WALLET_ID +USER_PRIMARY_KEY + USER_WALLET_ID
                                + JSON_SOLUTION_DATA;

        this.HASH = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }

    public String getSOLUTION_OWNER() {
        return SOLUTION_OWNER;
    }

    public void setSOLUTION_OWNER(String SOLUTION_OWNER) {
        this.SOLUTION_OWNER = SOLUTION_OWNER;
    }

    public String getSOLUTION_TITLE() {
        return SOLUTION_TITLE;
    }

    public void setSOLUTION_TITLE(String SOLUTION_TITLE) {
        this.SOLUTION_TITLE = SOLUTION_TITLE;
    }



    public long getSOLUTION_ID() {
        return SOLUTION_ID;
    }

    public void setSOLUTION_ID(long SOLUTION_ID) {
        this.SOLUTION_ID = SOLUTION_ID;
    }

    public long getFORM_ID() {
        return FORM_ID;
    }

    public void setFORM_ID(long FORM_ID) {
        this.FORM_ID = FORM_ID;
    }



    public SolutionInfo getInfo() {
        return this;
    }



    public String getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(String DATETIME) {
        this.DATETIME = DATETIME;
    }

    public long getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(long STATUS) {
        this.STATUS = STATUS;
    }

    public long getSOLUTION_PROVIDER_PRIMARY_KEY() {
        return SOLUTION_PROVIDER_PRIMARY_KEY;
    }

    public void setSOLUTION_PROVIDER_PRIMARY_KEY(long SOLUTION_PROVIDER_PRIMARY_KEY) {
        this.SOLUTION_PROVIDER_PRIMARY_KEY = SOLUTION_PROVIDER_PRIMARY_KEY;
    }

    public String getSOLUTION_PROVIDER_WALLET_ID() {
        return SOLUTION_PROVIDER_WALLET_ID;
    }

    public void setSOLUTION_PROVIDER_WALLET_ID(String SOLUTION_PROVIDER_WALLET_ID) {
        this.SOLUTION_PROVIDER_WALLET_ID = SOLUTION_PROVIDER_WALLET_ID;
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

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }

    public String getJSON_SOLUTION_DATA() {
        return JSON_SOLUTION_DATA;
    }

    public void setJSON_SOLUTION_DATA(String JSON_SOLUTION_DATA) {
        this.JSON_SOLUTION_DATA = JSON_SOLUTION_DATA;
    }
}
