package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.projectroboticscontent.contentDatabaseAPI.PublicFiles.PublicFileStorage;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

public class SolutionMarketInput extends SolutionMarketSubmission{



    @Field
    private PublicFileStorage[] FILE_LIST;

    public SolutionMarketInput(String SERVER_STATUS_MESSAGE, HttpStatus SERVER_STATUS, String SOLUTION_OWNER, String SOLUTION_TITLE, long STATUS, long SOLUTION_ID, long SOLUTION_PROVIDER_PRIMARY_KEY, String SOLUTION_PROVIDER_WALLET_ID, String DATETIME, String JSON_SOLUTION_DATA, String token, String USERTYPE, long PRICE, PublicFileStorage[] FILE_LIST) {
        super(SERVER_STATUS_MESSAGE, SERVER_STATUS, SOLUTION_OWNER, SOLUTION_TITLE, STATUS, SOLUTION_ID,  SOLUTION_PROVIDER_PRIMARY_KEY, SOLUTION_PROVIDER_WALLET_ID, DATETIME, JSON_SOLUTION_DATA, token, USERTYPE, PRICE);
        this.FILE_LIST = FILE_LIST;
    }

    public PublicFileStorage[] getFILE_LIST() {
        return FILE_LIST;
    }

    public void setFILE_LIST(PublicFileStorage[] FILE_LIST) {
        this.FILE_LIST = FILE_LIST;
    }


}
