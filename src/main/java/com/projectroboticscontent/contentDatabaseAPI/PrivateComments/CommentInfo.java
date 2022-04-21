package com.projectroboticscontent.contentDatabaseAPI.PrivateComments;

import org.springframework.data.mongodb.core.mapping.Field;

public class CommentInfo {

    @Field
    private String token;

    @Field
    private String USERTYPE;

    @Field
    private long SOLUTION_ID;


    @Field
    private long FORM_ID;

    @Field
    private String COMMENT_OWNER;

    @Field
    private long PRIMARY_KEY_SOLUTION;

    @Field
    private long COMMENT_ID;

    @Field
    private long PRIMARY_KEY_USER;


    public CommentInfo(String token, String USERTYPE, long SOLUTION_ID, long PRIMARY_KEY_SOLUTION, long COMMENT_ID, String COMMENT_OWNER
    ,long PRIMARY_KEY_USER, long FORM_ID)
    {
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.SOLUTION_ID = SOLUTION_ID;
        this.FORM_ID = FORM_ID;
        this.PRIMARY_KEY_SOLUTION = PRIMARY_KEY_SOLUTION;
        this.COMMENT_ID = COMMENT_ID;
        this.COMMENT_OWNER = COMMENT_OWNER;
        this.PRIMARY_KEY_USER = PRIMARY_KEY_USER;
    }

    public String getToken() {
        return token;
    }

    public String getUSERTYPE() {
        return USERTYPE;
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



    public void setCOMMENT_ID(long COMMENT_ID) {
        this.COMMENT_ID = COMMENT_ID;
    }





    public String getCOMMENT_OWNER() {
        return COMMENT_OWNER;
    }

    public void setCOMMENT_OWNER(String COMMENT_OWNER) {
        this.COMMENT_OWNER = COMMENT_OWNER;
    }

    public long getPRIMARY_KEY_USER() {
        return PRIMARY_KEY_USER;
    }

    public void setPRIMARY_KEY_USER(long PRIMARY_KEY_USER) {
        this.PRIMARY_KEY_USER = PRIMARY_KEY_USER;
    }

    public long getSOLUTION_ID() {
        return SOLUTION_ID;
    }

    public void setSOLUTION_ID(long SOLUTION_ID) {
        this.SOLUTION_ID = SOLUTION_ID;
    }

    public long getPRIMARY_KEY_SOLUTION() {
        return PRIMARY_KEY_SOLUTION;
    }

    public void setPRIMARY_KEY_SOLUTION(long PRIMARY_KEY_SOLUTION) {
        this.PRIMARY_KEY_SOLUTION = PRIMARY_KEY_SOLUTION;
    }


}
