package com.projectroboticscontent.contentDatabaseAPI.Investor;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

public class FinanceSubmission extends FinanceRequest{

    @Field
    private String token;

    @Field
    private String USERTYPE;

    public FinanceSubmission(String SERVER_STATUS_MESSAGE, HttpStatus SERVER_STATUS, long STATUS, long FINANCE_REQUEST_ID, long TOTAL_COST,
                             String SOLUTION_TITLE, long PAYMENT, long USAGE_TIME, long SOLUTION_PROVIDER_PRIMARY_KEY,
                             long INVESTOR_PRIMARY_KEY, String DATETIME, String SOLUTION_PROVIDER_WALLET_ID, String INVESTOR_WALLET_ID,
                             String HARDWARE_ID, long USER_PRIMARY_KEY, String USER_WALLET_ID,
                             long SOLUTION_ID, String token, String USERTYPE,
                             String SOLUTION_OWNER, String FINANCE_REQUEST_OWNER) {
        super( SERVER_STATUS_MESSAGE,  SERVER_STATUS,STATUS, FINANCE_REQUEST_ID, TOTAL_COST, SOLUTION_TITLE, PAYMENT, USAGE_TIME, SOLUTION_PROVIDER_PRIMARY_KEY, INVESTOR_PRIMARY_KEY,
                DATETIME, SOLUTION_PROVIDER_WALLET_ID, INVESTOR_WALLET_ID, HARDWARE_ID, USER_PRIMARY_KEY,
                USER_WALLET_ID, SOLUTION_ID, SOLUTION_OWNER, FINANCE_REQUEST_OWNER);
        this.token = token;
        this.USERTYPE = USERTYPE;
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
}
