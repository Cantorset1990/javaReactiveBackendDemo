package com.projectroboticscontent.contentDatabaseAPI.Profile;

import org.springframework.data.mongodb.core.mapping.Field;

public class Submission {

    @Field
    String token;

    @Field
    String USERTYPE;

    @Field
    String WALLET_ID;

    public Submission(String token, String USERTYPE, String WALLET_ID) {
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.WALLET_ID = WALLET_ID;
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

    public String getWALLET_ID() {
        return WALLET_ID;
    }

    public void setWALLET_ID(String WALLET_ID) {
        this.WALLET_ID = WALLET_ID;
    }
}
