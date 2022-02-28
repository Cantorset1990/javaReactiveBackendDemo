package com.projectroboticscontent.contentDatabaseAPI.PublicFiles;

import org.springframework.data.mongodb.core.mapping.Field;

public class PublicFileInfo {

    @Field
    private String token;

    @Field
    private String USERTYPE;



    @Field
    private long FORM_ID;

    @Field
    private String FILE_OWNER;


    @Field
    private long FILE_ID;

    @Field
    private long PRIMARY_KEY;

    @Field
    private String FILE_NAME;

    @Field
    private String FILE_TYPE;

    @Field
    private long FILE_SIZE;

    @Field
    private String FILE_INDEX;


    public PublicFileInfo(String token, String USERTYPE, long FORM_ID, String FILE_OWNER,
                          long FILE_ID, long PRIMARY_KEY, String FILE_NAME,
                          String FILE_TYPE, long FILE_SIZE, String FILE_INDEX) {
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.FORM_ID = FORM_ID;
        this.FILE_OWNER = FILE_OWNER;
        this.FILE_ID = FILE_ID;
        this.PRIMARY_KEY = PRIMARY_KEY;
        this.FILE_NAME = FILE_NAME;
        this.FILE_TYPE = FILE_TYPE;
        this.FILE_SIZE = FILE_SIZE;
        this.FILE_INDEX = FILE_INDEX;
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

    public long getFORM_ID() {
        return FORM_ID;
    }

    public void setFORM_ID(long FORM_ID) {
        this.FORM_ID = FORM_ID;
    }

    public String getFILE_OWNER() {
        return FILE_OWNER;
    }

    public void setFILE_OWNER(String FILE_OWNER) {
        this.FILE_OWNER = FILE_OWNER;
    }

    public long getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(long FILE_ID) {
        this.FILE_ID = FILE_ID;
    }



    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public long getFILE_SIZE() {
        return FILE_SIZE;
    }

    public void setFILE_SIZE(long FILE_SIZE) {
        this.FILE_SIZE = FILE_SIZE;
    }

    public String getFILE_INDEX() {
        return FILE_INDEX;
    }

    public void setFILE_INDEX(String FILE_INDEX) {
        this.FILE_INDEX = FILE_INDEX;
    }

    public long getPRIMARY_KEY() {
        return PRIMARY_KEY;
    }

    public void setPRIMARY_KEY(long PRIMARY_KEY) {
        this.PRIMARY_KEY = PRIMARY_KEY;
    }
}
