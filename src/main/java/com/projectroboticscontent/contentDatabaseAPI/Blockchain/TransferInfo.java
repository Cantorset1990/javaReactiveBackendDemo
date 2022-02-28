package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import org.springframework.data.mongodb.core.mapping.Field;

public class TransferInfo {

    @Field
    private String token;

    @Field
    private String USERTYPE;

    @Field
    private String EMAIL;

    @Field
    private String AMOUNT;

    @Field
    private String RECEIVER;

    public TransferInfo(String token, String USERTYPE, String EMAIL, String AMOUNT, String RECEIVER) {
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.EMAIL = EMAIL;
        this.AMOUNT = AMOUNT;
        this.RECEIVER = RECEIVER;
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

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getRECEIVER() {
        return RECEIVER;
    }

    public void setRECEIVER(String RECEIVER) {
        this.RECEIVER = RECEIVER;
    }
}
