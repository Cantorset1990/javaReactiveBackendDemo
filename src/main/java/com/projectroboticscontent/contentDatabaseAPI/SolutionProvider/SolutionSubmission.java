package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import org.springframework.data.mongodb.core.mapping.Field;

public class SolutionSubmission extends SolutionInfo {

    @Field
    private String token;

    @Field
    private String USERTYPE;


    @Field
    private long[] COMMENTS;




    public SolutionSubmission(String SOLUTION_OWNER, String SOLUTION_TITLE, String JSON_SOLUTION_DATA, long SOLUTION_ID, long FORM_ID, long[] COMMENTS,
                              String token, String USERTYPE, long SOLUTION_PROVIDER_PRIMARY_KEY, String DATETIME,
                              long STATUS, String SOLUTION_PROVIDER_WALLET_ID, long USER_PRIMARY_KEY, String USER_WALLET_ID) {

        super(SOLUTION_OWNER, SOLUTION_TITLE,  SOLUTION_ID, FORM_ID, SOLUTION_PROVIDER_PRIMARY_KEY,
                DATETIME, STATUS, SOLUTION_PROVIDER_WALLET_ID, USER_PRIMARY_KEY, USER_WALLET_ID, JSON_SOLUTION_DATA);

        this.token = token;
        this.USERTYPE = USERTYPE;
        this.COMMENTS = COMMENTS;



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


    public long[] getCOMMENTS() {
        return COMMENTS;
    }

    public void setCOMMENTS(long[] COMMENTS) {
        this.COMMENTS = COMMENTS;
    }


}
