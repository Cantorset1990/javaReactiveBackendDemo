package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

public class FormDataRetrieval {


    @Field
    String FORM_OWNER;

    @Field
    String USERTYPE;

    @Field
    private String FORM_TITLE;

    @Field
    private long FORM_ID;

    @Field
    private long USER_PRIMARY_KEY;

    @Field
    private String JSON_FORM_DATA;


    @Field
    private long[] COMMENTS;

    @Field
    private long[] SOLUTION_LIST;

    @Field
    private String DATETIME;

    @Field
    private long STATUS;

    @Field
    String SERVER_STATUS_MESSAGE;

    @Field
    HttpStatus SERVER_STATUS;



    public FormDataRetrieval(String SERVER_STATUS_MESSAGE, HttpStatus SERVER_STATUS, String FORM_OWNER, String USERTYPE, String FORM_TITLE,
                         long FORM_ID, long USER_PRIMARY_KEY, String JSON_FORM_DATA, long[] COMMENTS,
                         long[] SOLUTION_LIST, String DATETIME, long STATUS
    )
    {
        this.SERVER_STATUS_MESSAGE = SERVER_STATUS_MESSAGE;
        this.SERVER_STATUS = SERVER_STATUS;
        this.FORM_OWNER = FORM_OWNER;
        this.USERTYPE = USERTYPE;
        this.FORM_TITLE = FORM_TITLE;
        this.FORM_ID = FORM_ID;
        this.USER_PRIMARY_KEY = USER_PRIMARY_KEY;
        this.JSON_FORM_DATA = JSON_FORM_DATA;
        this.COMMENTS = COMMENTS;
        this.SOLUTION_LIST = SOLUTION_LIST;
        this.DATETIME = DATETIME;
        this.STATUS = STATUS;
    }

    public String getFORM_OWNER() {
        return FORM_OWNER;
    }

    public void setFORM_OWNER(String FORM_OWNER) {
        this.FORM_OWNER = FORM_OWNER;
    }

    public String getUSERTYPE() {
        return USERTYPE;
    }

    public void setUSERTYPE(String USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public String getFORM_TITLE() {
        return FORM_TITLE;
    }

    public void setFORM_TITLE(String FORM_TITLE) {
        this.FORM_TITLE = FORM_TITLE;
    }

    public long getFORM_ID() {
        return FORM_ID;
    }

    public void setFORM_ID(long FORM_ID) {
        this.FORM_ID = FORM_ID;
    }



    public String getJSON_FORM_DATA() {
        return JSON_FORM_DATA;
    }

    public void setJSON_FORM_DATA(String JSON_FORM_DATA) {
        this.JSON_FORM_DATA = JSON_FORM_DATA;
    }

    public long[] getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(long[] COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

    public long[] getSOLUTION_LIST() {
        return SOLUTION_LIST;
    }

    public void setSOLUTION_LIST(long[] SOLUTION_LIST) {
        this.SOLUTION_LIST = SOLUTION_LIST;
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

    public long getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(long STATUS) {
        this.STATUS = STATUS;
    }

    public String getSERVER_STATUS_MESSAGE() {
        return SERVER_STATUS_MESSAGE;
    }

    public void setSERVER_STATUS_MESSAGE(String SERVER_STATUS_MESSAGE) {
        this.SERVER_STATUS_MESSAGE = SERVER_STATUS_MESSAGE;
    }

    public HttpStatus getSERVER_STATUS() {
        return SERVER_STATUS;
    }

    public void setSERVER_STATUS(HttpStatus SERVER_STATUS) {
        this.SERVER_STATUS = SERVER_STATUS;
    }
}
