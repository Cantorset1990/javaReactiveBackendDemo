package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import org.springframework.data.mongodb.core.mapping.Field;

public class SolutionDataRetrieval {


    @Field
    private String SOLUTION_OWNER;

    @Field
    private String SOLUTION_TITLE;


    @Field
    private long SOLUTION_ID;

    @Field
    private long FORM_ID;

    @Field
    private long SOLUTION_PROVIDER_PRIMARY_KEY;

    @Field
    private String USERTYPE;

    @Field
    private long[] COMMENTS;

    @Field
    private String JSON_SOLUTION_DATA;

    @Field
    private String DATETIME;

    @Field
    private long STATUS;

    public SolutionDataRetrieval(String SOLUTION_OWNER, String SOLUTION_TITLE,
                             long SOLUTION_ID, long FORM_ID,
                             long SOLUTION_PROVIDER_PRIMARY_KEY, String USERTYPE,
                                 long[] COMMENTS, String JSON_SOLUTION_DATA,
                                 String DATETIME, long STATUS) {


        this.SOLUTION_OWNER = SOLUTION_OWNER;
        this.SOLUTION_TITLE = SOLUTION_TITLE;
        this.SOLUTION_ID = SOLUTION_ID;
        this.FORM_ID = FORM_ID;
        this.SOLUTION_PROVIDER_PRIMARY_KEY = SOLUTION_PROVIDER_PRIMARY_KEY;
        this.USERTYPE = USERTYPE;
        this.COMMENTS = COMMENTS;
        this.JSON_SOLUTION_DATA = JSON_SOLUTION_DATA;
        this.DATETIME = DATETIME;
        this.STATUS = STATUS;
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



    public String getUSERTYPE() {
        return USERTYPE;
    }

    public void setUSERTYPE(String USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public long[] getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(long[] COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

    public String getJSON_SOLUTION_DATA() {
        return JSON_SOLUTION_DATA;
    }

    public void setJSON_SOLUTION_DATA(String JSON_SOLUTION_DATA) {
        this.JSON_SOLUTION_DATA = JSON_SOLUTION_DATA;
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
}
