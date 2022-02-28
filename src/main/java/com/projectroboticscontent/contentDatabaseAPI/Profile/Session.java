package com.projectroboticscontent.contentDatabaseAPI.Profile;

public class Session {


    String[] USERTYPE;
    String TOKEN;
    String WALLET_ID;

    public Session(String[] USERTYPE, String TOKEN, String WALLET_ID) {
        this.USERTYPE = USERTYPE.clone();
        this.TOKEN = TOKEN;
        this.WALLET_ID = WALLET_ID;
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
}
