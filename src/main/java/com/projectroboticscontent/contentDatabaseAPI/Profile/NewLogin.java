package com.projectroboticscontent.contentDatabaseAPI.Profile;

import org.springframework.data.mongodb.core.mapping.Field;

public class NewLogin {

    @Field
    protected String USERNAME;
    @Field
    protected String PASSWORD;


    public NewLogin(String USERNAME, String PASSWORD) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }



    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    @Override
    public String toString() {
        return "NewLogin{" +
                "USERNAME='" + USERNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                '}';
    }
}
