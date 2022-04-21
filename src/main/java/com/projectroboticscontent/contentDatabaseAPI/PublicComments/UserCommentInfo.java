package com.projectroboticscontent.contentDatabaseAPI.PublicComments;

import org.springframework.data.mongodb.core.mapping.Field;

public class UserCommentInfo {

    @Field
    private String token;

    @Field
    private String USERTYPE;


    @Field
    private long FORM_ID;

    @Field
    private String COMMENT_OWNER;

    @Field
    private long PRIMARY_KEY;

    @Field
    private long COMMENT_ID;




    public UserCommentInfo(String token, String USERTYPE,  long PRIMARY_KEY, long COMMENT_ID, String COMMENT_OWNER,
             long FORM_ID)
    {
        this.token = token;
        this.USERTYPE = USERTYPE;

        this.FORM_ID = FORM_ID;
        this.PRIMARY_KEY = PRIMARY_KEY;
        this.COMMENT_ID = COMMENT_ID;
        this.COMMENT_OWNER = COMMENT_OWNER;

    }

    public String getToken() {
        return token;
    }

    public String getUSERTYPE() {
        return USERTYPE;
    }

    public long getPRIMARY_KEY() {
        return PRIMARY_KEY;
    }

    public long getCOMMENT_ID() {
        return COMMENT_ID;
    }

    public long getFORM_ID() {
        return FORM_ID;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUSERTYPE(String USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public void setFORM_ID(long FORM_ID) {
        this.FORM_ID = FORM_ID;
    }

    public void setPRIMARY_KEY(long PRIMARY_KEY) {
        this.PRIMARY_KEY = PRIMARY_KEY;
    }

    public void setCOMMENT_ID(long COMMENT_ID) {
        this.COMMENT_ID = COMMENT_ID;
    }



    public String getCOMMENT_OWNER() {
        return COMMENT_OWNER;
    }

    public void setCOMMENT_OWNER(String COMMENT_OWNER) {
        this.COMMENT_OWNER = COMMENT_OWNER;
    }




}
