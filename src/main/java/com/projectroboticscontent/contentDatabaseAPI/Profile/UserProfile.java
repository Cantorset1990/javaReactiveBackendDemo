package com.projectroboticscontent.contentDatabaseAPI.Profile;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

public class UserProfile extends NewLogin {

    @Field
    protected String FIRSTNAME;
    @Field
    protected String LASTNAME;
    @Field
    protected String EMAIL;
    @Field
    protected String PHONE;
    @Field
    protected String[] USERTYPE;
    @Field
    protected long[] FORM_PERMISSION;


    private long PRIMARY_KEY;

    @Field
    private String REGISTRATION_DATE;

    @Field
    private String WALLET_ID;

    public UserProfile(String FIRSTNAME, String LASTNAME, String USERNAME, String PASSWORD,
                           String EMAIL, String PHONE, String[] USERTYPE,
                           long[] FORM_PERMISSION, String WALLET_ID) {

        super(USERNAME, PASSWORD);

        this.FIRSTNAME = FIRSTNAME;
        this.LASTNAME = LASTNAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.PRIMARY_KEY =  System.currentTimeMillis();
        this.REGISTRATION_DATE = LocalDateTime.now().toString();
        this.USERTYPE = USERTYPE;
        this.FORM_PERMISSION = FORM_PERMISSION;
        this.WALLET_ID = WALLET_ID;
    }



    public String getEMAIL() {
        return EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public long getPRIMARY_KEY() {
        return PRIMARY_KEY;
    }



    public String getREGISTRATION_DATE() {
        return REGISTRATION_DATE;
    }

    public String[] getUSERTYPE() {
        return USERTYPE;
    }

    public long[] getFORM_PERMISSION() {
        return FORM_PERMISSION;
    }

    public void setEMAIL(String email) {
        this.EMAIL = email;
    }

    public void setPHONE(String phone) {
        this.PHONE = phone;
    }

    public void setFIRSTNAME(String firstname) {
        this.FIRSTNAME = firstname;
    }

    public void setLASTNAME(String lastname) {
        this.LASTNAME = lastname;
    }

    public void setUSERTYPE(String[] USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public void setFORM_PERMISSION(long[] FORM_PERMISSION) {
        this.FORM_PERMISSION = FORM_PERMISSION;
    }

    public void setREGISTRATION_DATE(String REGISTRATION_DATE) {
        this.REGISTRATION_DATE = REGISTRATION_DATE;
    }

    public String getWALLET_ID() {
        return WALLET_ID;
    }

    public void setWALLET_ID(String WALLET_ID) {
        this.WALLET_ID = WALLET_ID;
    }




    /*
    @Field
    protected String USERNAME;
    @Field
    private String FIRSTNAME;
    @Field
    private String LASTNAME;
    @Field
    private String EMAIL;
    @Field
    private String PHONE;
    @Field
    private String[] USERTYPE;
    @Field
    private long[] FORM_PERMISSION;

    @Field
    private String REGISTRATION_DATE;

    @Field
    private String WALLET_ID;

    public UserProfile(String USERNAME, String FIRSTNAME,
                       String LASTNAME, String EMAIL,
                       String PHONE, String[] USERTYPE,
                       String REGISTRATION_DATE, String WALLET_ID) {
        this.USERNAME = USERNAME;
        this.FIRSTNAME = FIRSTNAME;
        this.LASTNAME = LASTNAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.USERTYPE = USERTYPE;
        this.WALLET_ID = WALLET_ID;
        this.REGISTRATION_DATE = REGISTRATION_DATE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String[] getUSERTYPE() {
        return USERTYPE;
    }


    public String getREGISTRATION_DATE() {
        return REGISTRATION_DATE;
    }

    public long[] getFORM_PERMISSION() {
        return FORM_PERMISSION;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public void setUSERTYPE(String[] USERTYPE) {
        this.USERTYPE = USERTYPE;
    }


    public void setREGISTRATION_DATE(String REGISTRATION_DATE) {
        this.REGISTRATION_DATE = REGISTRATION_DATE;
    }

    public void setFORM_PERMISSION(long[] FORM_PERMISSION) {
        this.FORM_PERMISSION = FORM_PERMISSION;
    }

    public String getWALLET_ID() {
        return WALLET_ID;
    }

    public void setWALLET_ID(String WALLET_ID) {
        this.WALLET_ID = WALLET_ID;
    }

    */
}
