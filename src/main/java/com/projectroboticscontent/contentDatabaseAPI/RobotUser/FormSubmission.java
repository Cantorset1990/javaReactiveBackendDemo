package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.google.common.hash.Hashing;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

public class FormSubmission extends FormInfo {

    @Field
    private String token;

    @Field
    private String USERTYPE;

    @Field
    private String JSON_FORM_DATA;

    @Field
    private String HASH;


    @Field
    private long[] COMMENTS;

    @Field
    private long[] FILES;

    @Field
    private long[] SOLUTION_LIST;

    @Field
    String SERVER_STATUS_MESSAGE;

    @Field
    HttpStatus SERVER_STATUS;


    public FormSubmission( String SERVER_STATUS_MESSAGE, HttpStatus SERVER_STATUS, String token, String USERTYPE, String FORM_TITLE, String JSON_FORM_DATA,
                          long USER_PRIMARY_KEY, long FORM_ID, long[] COMMENTS,
                          long[] SOLUTION_LIST, String FORM_OWNER, long[] FILES, String DATETIME,
                          String USER_WALLET_ID, long STATUS) {
        super(FORM_TITLE, FORM_ID, FORM_OWNER, USER_PRIMARY_KEY, DATETIME, USER_WALLET_ID, STATUS);

        this.SERVER_STATUS_MESSAGE = SERVER_STATUS_MESSAGE;
        this.SERVER_STATUS = SERVER_STATUS;
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.COMMENTS = COMMENTS;
        this.SOLUTION_LIST = SOLUTION_LIST;
        this.JSON_FORM_DATA = JSON_FORM_DATA;
        this.FILES = FILES;
        String originalString =  USERTYPE + USER_PRIMARY_KEY + USER_WALLET_ID + FORM_TITLE + JSON_FORM_DATA
        + FORM_ID + DATETIME;

        this.HASH = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();


    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public long[] getSOLUTION_LIST() {
        return SOLUTION_LIST;
    }

    public void setSOLUTION_LIST(long[] SOLUTION_LIST) {
        this.SOLUTION_LIST = SOLUTION_LIST;
    }

    public String getJSON_FORM_DATA() {
        return JSON_FORM_DATA;
    }

    public void setJSON_FORM_DATA(String JSON_FORM_DATA) {
        this.JSON_FORM_DATA = JSON_FORM_DATA;
    }

    public long[] getFILES() {
        return FILES;
    }

    public void setFILES(long[] FILES) {
        this.FILES = FILES;
    }

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
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
