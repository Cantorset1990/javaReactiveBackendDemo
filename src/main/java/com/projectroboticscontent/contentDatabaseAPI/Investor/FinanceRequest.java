package com.projectroboticscontent.contentDatabaseAPI.Investor;

import com.google.common.hash.Hashing;
import org.springframework.data.mongodb.core.mapping.Field;

import java.nio.charset.StandardCharsets;

public class FinanceRequest {


    @Field
    private long STATUS;

    @Field
    private long FINANCE_REQUEST_ID;

    @Field
    private String FINANCE_REQUEST_OWNER;

    @Field
    private long SOLUTION_ID;

    @Field
    private long TOTAL_COST;

    @Field
    private String SOLUTION_TITLE;

    @Field
    private long PAYMENT;

    @Field
    private long USAGE_TIME;

    @Field
    private long SOLUTION_PROVIDER_PRIMARY_KEY;

    @Field
    private String SOLUTION_PROVIDER_WALLET_ID;

    @Field
    private String SOLUTION_OWNER;

    @Field
    private long INVESTOR_PRIMARY_KEY;

    @Field
    private String INVESTOR_WALLET_ID;

    @Field
    private long USER_PRIMARY_KEY;

    @Field
    private String USER_WALLET_ID;

    @Field
    private String DATETIME;

    @Field
    private String HARDWARE_ID;

    @Field
    private String HASH;

    public FinanceRequest(long STATUS, long FINANCE_REQUEST_ID, long TOTAL_COST,
                          String SOLUTION_TITLE, long PAYMENT,
                          long USAGE_TIME, long SOLUTION_PROVIDER_PRIMARY_KEY,
                          long INVESTOR_PRIMARY_KEY, String DATETIME,
                          String SOLUTION_PROVIDER_WALLET_ID, String INVESTOR_WALLET_ID,
                          String HARDWARE_ID, long USER_PRIMARY_KEY,
                          String USER_WALLET_ID, long SOLUTION_ID,
                          String SOLUTION_OWNER, String FINANCE_REQUEST_OWNER) {

        this.STATUS = STATUS;
        this.FINANCE_REQUEST_ID = FINANCE_REQUEST_ID;
        this.TOTAL_COST = TOTAL_COST;
        this.SOLUTION_TITLE = SOLUTION_TITLE;
        this.PAYMENT = PAYMENT;
        this.USAGE_TIME = USAGE_TIME;
        this.SOLUTION_PROVIDER_PRIMARY_KEY = SOLUTION_PROVIDER_PRIMARY_KEY;
        this.INVESTOR_PRIMARY_KEY = INVESTOR_PRIMARY_KEY;
        this.DATETIME = DATETIME;
        this.SOLUTION_PROVIDER_WALLET_ID = SOLUTION_PROVIDER_WALLET_ID;
        this.INVESTOR_WALLET_ID = INVESTOR_WALLET_ID;
        this.HARDWARE_ID = HARDWARE_ID;
        this.USER_PRIMARY_KEY = USER_PRIMARY_KEY;
        this.USER_WALLET_ID = USER_WALLET_ID;
        this.SOLUTION_ID = SOLUTION_ID;
        this.FINANCE_REQUEST_OWNER = FINANCE_REQUEST_OWNER;
        this.SOLUTION_OWNER = SOLUTION_OWNER;

        String originalString =  FINANCE_REQUEST_ID + TOTAL_COST + SOLUTION_TITLE
                + PAYMENT + USAGE_TIME +
                SOLUTION_PROVIDER_PRIMARY_KEY + INVESTOR_PRIMARY_KEY + DATETIME
                +SOLUTION_PROVIDER_WALLET_ID + INVESTOR_WALLET_ID
                +HARDWARE_ID + USER_PRIMARY_KEY + USER_WALLET_ID + SOLUTION_ID
                +FINANCE_REQUEST_OWNER + SOLUTION_OWNER;

        this.HASH = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

    }

    public long getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(long STATUS) {
        this.STATUS = STATUS;
    }

    public long getFINANCE_REQUEST_ID() {
        return FINANCE_REQUEST_ID;
    }

    public void setFINANCE_REQUEST_ID(long FINANCE_REQUEST_ID) {
        this.FINANCE_REQUEST_ID = FINANCE_REQUEST_ID;
    }

    public long getTOTAL_COST() {
        return TOTAL_COST;
    }

    public void setTOTAL_COST(long TOTAL_COST) {
        this.TOTAL_COST = TOTAL_COST;
    }

    public String getSOLUTION_TITLE() {
        return SOLUTION_TITLE;
    }

    public void setSOLUTION_TITLE(String SOLUTION_TITLE) {
        this.SOLUTION_TITLE = SOLUTION_TITLE;
    }

    public long getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(long PAYMENT) {
        this.PAYMENT = PAYMENT;
    }

    public long getUSAGE_TIME() {
        return USAGE_TIME;
    }

    public void setUSAGE_TIME(long USAGE_TIME) {
        this.USAGE_TIME = USAGE_TIME;
    }

    public long getSOLUTION_PROVIDER_PRIMARY_KEY() {
        return SOLUTION_PROVIDER_PRIMARY_KEY;
    }

    public void setSOLUTION_PROVIDER_PRIMARY_KEY(long SOLUTION_PROVIDER_PRIMARY_KEY) {
        this.SOLUTION_PROVIDER_PRIMARY_KEY = SOLUTION_PROVIDER_PRIMARY_KEY;
    }

    public long getINVESTOR_PRIMARY_KEY() {
        return INVESTOR_PRIMARY_KEY;
    }

    public void setINVESTOR_PRIMARY_KEY(long INVESTOR_PRIMARY_KEY) {
        this.INVESTOR_PRIMARY_KEY = INVESTOR_PRIMARY_KEY;
    }

    public String getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(String DATETIME) {
        this.DATETIME = DATETIME;
    }

    public String getSOLUTION_PROVIDER_WALLET_ID() {
        return SOLUTION_PROVIDER_WALLET_ID;
    }

    public void setSOLUTION_PROVIDER_WALLET_ID(String SOLUTION_PROVIDER_WALLET_ID) {
        this.SOLUTION_PROVIDER_WALLET_ID = SOLUTION_PROVIDER_WALLET_ID;
    }

    public String getINVESTOR_WALLET_ID() {
        return INVESTOR_WALLET_ID;
    }

    public void setINVESTOR_WALLET_ID(String INVESTOR_WALLET_ID) {
        this.INVESTOR_WALLET_ID = INVESTOR_WALLET_ID;
    }

    public String getHARDWARE_ID() {
        return HARDWARE_ID;
    }

    public void setHARDWARE_ID(String HARDWARE_ID) {
        this.HARDWARE_ID = HARDWARE_ID;
    }

    public long getUSER_PRIMARY_KEY() {
        return USER_PRIMARY_KEY;
    }

    public void setUSER_PRIMARY_KEY(long USER_PRIMARY_KEY) {
        this.USER_PRIMARY_KEY = USER_PRIMARY_KEY;
    }

    public String getUSER_WALLET_ID() {
        return USER_WALLET_ID;
    }

    public void setUSER_WALLET_ID(String USER_WALLET_ID) {
        this.USER_WALLET_ID = USER_WALLET_ID;
    }

    public long getSOLUTION_ID() {
        return SOLUTION_ID;
    }

    public void setSOLUTION_ID(long SOLUTION_ID) {
        this.SOLUTION_ID = SOLUTION_ID;
    }

    public String getFINANCE_REQUEST_OWNER() {
        return FINANCE_REQUEST_OWNER;
    }

    public void setFINANCE_REQUEST_OWNER(String FINANCE_REQUEST_OWNER) {
        this.FINANCE_REQUEST_OWNER = FINANCE_REQUEST_OWNER;
    }

    public String getSOLUTION_OWNER() {
        return SOLUTION_OWNER;
    }

    public void setSOLUTION_OWNER(String SOLUTION_OWNER) {
        this.SOLUTION_OWNER = SOLUTION_OWNER;
    }

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }
}
