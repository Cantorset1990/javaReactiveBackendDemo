package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;


import com.projectroboticscontent.contentDatabaseAPI.PrivateFiles.PrivateFileStorage;
import org.springframework.data.mongodb.core.mapping.Field;

public class SolutionInput extends SolutionSubmission {

    @Field
    private PrivateFileStorage[] FILE_LIST;

    public SolutionInput(String SOLUTION_OWNER, String SOLUTION_TITLE, String JSON_SOLUTION_DATA,
                         long SOLUTION_ID, long FORM_ID, long[] COMMENTS, String token,
                         String USERTYPE, long SOLUTION_PROVIDER_PRIMARY_KEY, PrivateFileStorage[] FILE_LIST,
                         String DATETIME, long STATUS, String SOLUTION_PROVIDER_WALLET_ID,
                         long USER_PRIMARY_KEY, String USER_WALLET_ID) {
        super(SOLUTION_OWNER, SOLUTION_TITLE, JSON_SOLUTION_DATA, SOLUTION_ID, FORM_ID,
                COMMENTS, token, USERTYPE, SOLUTION_PROVIDER_PRIMARY_KEY, DATETIME, STATUS,
                SOLUTION_PROVIDER_WALLET_ID,  USER_PRIMARY_KEY,  USER_WALLET_ID);

        this.FILE_LIST = FILE_LIST;


    }

    public PrivateFileStorage[] getFILE_LIST() {
        return FILE_LIST;
    }

    public void setFILE_LIST(PrivateFileStorage[] FILE_LIST) {
        this.FILE_LIST = FILE_LIST;
    }


}
