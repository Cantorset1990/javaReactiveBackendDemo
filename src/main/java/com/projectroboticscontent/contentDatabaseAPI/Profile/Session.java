package com.projectroboticscontent.contentDatabaseAPI.Profile;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class Session {


    String[] USERTYPE;
    String TOKEN;
    String WALLET_ID;
    String SERVER_STATUS_MESSAGE;
    HttpStatus SERVER_STATUS;

    public Session(String[] USERTYPE, String TOKEN, String WALLET_ID,
                   HttpStatus SERVER_STATUS, String SERVER_STATUS_MESSAGE) {
        this.USERTYPE = USERTYPE.clone();
        this.TOKEN = TOKEN;
        this.WALLET_ID = WALLET_ID;
        this.SERVER_STATUS = SERVER_STATUS;
        this.SERVER_STATUS_MESSAGE = SERVER_STATUS_MESSAGE;
    }

    public String[] getUSERTYPE() {
        return USERTYPE;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setUSERTYPE(String[] USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getWALLET_ID() {
        return WALLET_ID;
    }

    public void setWALLET_ID(String WALLET_ID) {
        this.WALLET_ID = WALLET_ID;
    }

    public HttpStatus getSERVER_STATUS() {
        return SERVER_STATUS;
    }

    public void setSERVER_STATUS(HttpStatus SERVER_STATUS) {
        this.SERVER_STATUS = SERVER_STATUS;
    }

    public String getSERVER_STATUS_MESSAGE() {
        return SERVER_STATUS_MESSAGE;
    }

    public void setSERVER_STATUS_MESSAGE(String SERVER_STATUS_MESSAGE) {
        this.SERVER_STATUS_MESSAGE = SERVER_STATUS_MESSAGE;
    }

    @Override
    public String toString() {
        return "Session{" +
                "USERTYPE=" + Arrays.toString(USERTYPE) +
                ", TOKEN='" + TOKEN + '\'' +
                ", WALLET_ID='" + WALLET_ID + '\'' +
                '}';
    }
}
