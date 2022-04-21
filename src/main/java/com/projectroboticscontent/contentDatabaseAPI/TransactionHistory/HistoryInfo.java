package com.projectroboticscontent.contentDatabaseAPI.TransactionHistory;

import com.google.common.hash.Hashing;
import org.springframework.data.mongodb.core.mapping.Field;

import java.nio.charset.StandardCharsets;

public class HistoryInfo {

    @Field
    private String RECEIVER_WALLET_ID;

    @Field
    private String SENDER_WALLET_ID;

    @Field
    private long RECEIVER_TRANSACTION_AMOUNT;

    @Field
    private long SENDER_TRANSACTION_AMOUNT;

    @Field
    private String NEW_RECEIVER_BALANCE;

    @Field
    private String NEW_SENDER_BALANCE;

    //UNIQUE JOB ID
    @Field
    private long TRANSACTION_ID;

    @Field
    private String DATETIME;

    //Property 1 : machine initiated transaction
    //Property 2:  people initiated transaction
    @Field
    private long PROPERTY;

    @Field
    private String HASH;



    public HistoryInfo(String RECEIVER_WALLET_ID, String SENDER_WALLET_ID, long RECEIVER_TRANSACTION_AMOUNT,
                       long SENDER_TRANSACTION_AMOUNT, String NEW_RECEIVER_BALANCE,
                       String NEW_SENDER_BALANCE, long TRANSACTION_ID, String DATETIME, long PROPERTY) {

        this.RECEIVER_WALLET_ID = RECEIVER_WALLET_ID;
        this.SENDER_WALLET_ID = SENDER_WALLET_ID;
        this.RECEIVER_TRANSACTION_AMOUNT = RECEIVER_TRANSACTION_AMOUNT;
        this.SENDER_TRANSACTION_AMOUNT = SENDER_TRANSACTION_AMOUNT;
        this.NEW_RECEIVER_BALANCE = NEW_RECEIVER_BALANCE;
        this.NEW_SENDER_BALANCE = NEW_SENDER_BALANCE;
        this.TRANSACTION_ID = TRANSACTION_ID;
        this.DATETIME = DATETIME;
        this.PROPERTY = PROPERTY;

        String originalString =  RECEIVER_WALLET_ID + SENDER_WALLET_ID + RECEIVER_TRANSACTION_AMOUNT
        + SENDER_TRANSACTION_AMOUNT + NEW_RECEIVER_BALANCE +
                 NEW_SENDER_BALANCE + TRANSACTION_ID + DATETIME + PROPERTY;
        this.HASH = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

    }

    public String getRECEIVER_WALLET_ID() {
        return RECEIVER_WALLET_ID;
    }

    public void setRECEIVER_WALLET_ID(String RECEIVER_WALLET_ID) {
        this.RECEIVER_WALLET_ID = RECEIVER_WALLET_ID;
    }

    public String getSENDER_WALLET_ID() {
        return SENDER_WALLET_ID;
    }

    public void setSENDER_WALLET_ID(String SENDER_WALLET_ID) {
        this.SENDER_WALLET_ID = SENDER_WALLET_ID;
    }

    public long getRECEIVER_TRANSACTION_AMOUNT() {
        return RECEIVER_TRANSACTION_AMOUNT;
    }

    public void setRECEIVER_TRANSACTION_AMOUNT(long RECEIVER_TRANSACTION_AMOUNT) {
        this.RECEIVER_TRANSACTION_AMOUNT = RECEIVER_TRANSACTION_AMOUNT;
    }

    public long getSENDER_TRANSACTION_AMOUNT() {
        return SENDER_TRANSACTION_AMOUNT;
    }

    public void setSENDER_TRANSACTION_AMOUNT(long SENDER_TRANSACTION_AMOUNT) {
        this.SENDER_TRANSACTION_AMOUNT = SENDER_TRANSACTION_AMOUNT;
    }

    public String getNEW_RECEIVER_BALANCE() {
        return NEW_RECEIVER_BALANCE;
    }

    public void setNEW_RECEIVER_BALANCE(String NEW_RECEIVER_BALANCE) {
        this.NEW_RECEIVER_BALANCE = NEW_RECEIVER_BALANCE;
    }

    public String getNEW_SENDER_BALANCE() {
        return NEW_SENDER_BALANCE;
    }

    public void setNEW_SENDER_BALANCE(String NEW_SENDER_BALANCE) {
        this.NEW_SENDER_BALANCE = NEW_SENDER_BALANCE;
    }

    public long getTRANSACTION_ID() {
        return TRANSACTION_ID;
    }

    public void setTRANSACTION_ID(long TRANSACTION_ID) {
        this.TRANSACTION_ID = TRANSACTION_ID;
    }

    public String getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(String DATETIME) {
        this.DATETIME = DATETIME;
    }

    public long getPROPERTY() {
        return PROPERTY;
    }

    public void setPROPERTY(long PROPERTY) {
        this.PROPERTY = PROPERTY;
    }

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }
}
