package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.google.common.hash.Hashing;
import com.projectroboticscontent.contentDatabaseAPI.PublicFiles.PublicFileStorage;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

public class SolutionMarketSubmission {



    @Field
    private String SOLUTION_OWNER;

    @Field
    private String SOLUTION_TITLE;


    @Field
    private long STATUS;

    @Field
    private long SOLUTION_ID;

    @Field
    private long[] PURCHASE_LIST;

    @Field
    private long SOLUTION_PROVIDER_PRIMARY_KEY;

    @Field
    private String SOLUTION_PROVIDER_WALLET_ID;

    @Field
    private String DATETIME;

    @Field
    private String HASH;

    @Field
    private String JSON_SOLUTION_DATA;

  //  @Field
  //  private PublicFileStorage[] FILE_LIST;

    @Field
    private String token;

    @Field
    private String USERTYPE;

    @Field
    private long PRICE;

    @Field
    String SERVER_STATUS_MESSAGE;

    @Field
    HttpStatus SERVER_STATUS;

    public SolutionMarketSubmission(String SERVER_STATUS_MESSAGE, HttpStatus SERVER_STATUS,
                                    String SOLUTION_OWNER, String SOLUTION_TITLE,
                                    long STATUS, long SOLUTION_ID,
                                    long SOLUTION_PROVIDER_PRIMARY_KEY,
                                    String SOLUTION_PROVIDER_WALLET_ID, String DATETIME,
                                    String JSON_SOLUTION_DATA,
                                    String token, String USERTYPE, long PRICE) {

        this.SERVER_STATUS_MESSAGE = SERVER_STATUS_MESSAGE;
        this.SERVER_STATUS = SERVER_STATUS;
        this.SOLUTION_OWNER = SOLUTION_OWNER;
        this.SOLUTION_TITLE = SOLUTION_TITLE;
        this.STATUS = STATUS;
        this.SOLUTION_ID = SOLUTION_ID;
        this.PURCHASE_LIST = new long[]{};
        this.SOLUTION_PROVIDER_PRIMARY_KEY = SOLUTION_PROVIDER_PRIMARY_KEY;
        this.SOLUTION_PROVIDER_WALLET_ID = SOLUTION_PROVIDER_WALLET_ID;
        this.DATETIME = DATETIME;
        //this.HASH = HASH;
        this.JSON_SOLUTION_DATA = JSON_SOLUTION_DATA;
        //this.FILE_LIST = FILE_LIST;
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.PRICE = PRICE;

        String originalString = SOLUTION_OWNER + SOLUTION_TITLE + SOLUTION_ID
                + PURCHASE_LIST.toString() + SOLUTION_PROVIDER_PRIMARY_KEY + DATETIME
                + SOLUTION_PROVIDER_WALLET_ID
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

    public long getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(long STATUS) {
        this.STATUS = STATUS;
    }

    public long getSOLUTION_ID() {
        return SOLUTION_ID;
    }

    public void setSOLUTION_ID(long SOLUTION_ID) {
        this.SOLUTION_ID = SOLUTION_ID;
    }

    public long[] getPURCHASE_LIST() {
        return PURCHASE_LIST;
    }

    public void setPURCHASE_LIST(long[] PURCHASE_LIST) {
        this.PURCHASE_LIST = PURCHASE_LIST;
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

    public String getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(String DATETIME) {
        this.DATETIME = DATETIME;
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

    /*
    public PublicFileStorage[] getFILE_LIST() {
        return FILE_LIST;
    }

    public void setFILE_LIST(PublicFileStorage[] FILE_LIST) {
        this.FILE_LIST = FILE_LIST;
    }

    */

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

    public long getPRICE() {
        return PRICE;
    }

    public void setPRICE(long PRICE) {
        this.PRICE = PRICE;
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
