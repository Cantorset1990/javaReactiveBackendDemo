package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import org.springframework.data.mongodb.core.mapping.Field;

public class BlockVerification {

    @Field
    String token;

    @Field
    String USERTYPE;

    @Field
    String HASH;

    @Field
    long TRANSACTION_ID;

    @Field
    long FINANCE_REQUEST_ID;

    @Field
    long SOLUTION_ID;

    @Field
    long FORM_ID;




    public BlockVerification(String token, String USERTYPE, String HASH, long TRANSACTION_ID,
                             long FINANCE_REQUEST_ID, long SOLUTION_ID, long FORM_ID) {
        this.token = token;
        this.USERTYPE = USERTYPE;
        this.HASH = HASH;
        this.TRANSACTION_ID = TRANSACTION_ID;
        this.FINANCE_REQUEST_ID = FINANCE_REQUEST_ID;
        this.SOLUTION_ID = SOLUTION_ID;
        this.FORM_ID = FORM_ID;
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

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }

    public long getTRANSACTION_ID() {
        return TRANSACTION_ID;
    }

    public void setTRANSACTION_ID(long TRANSACTION_ID) {
        this.TRANSACTION_ID = TRANSACTION_ID;
    }

    public long getFINANCE_REQUEST_ID() {
        return FINANCE_REQUEST_ID;
    }

    public void setFINANCE_REQUEST_ID(long FINANCE_REQUEST_ID) {
        this.FINANCE_REQUEST_ID = FINANCE_REQUEST_ID;
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
}
