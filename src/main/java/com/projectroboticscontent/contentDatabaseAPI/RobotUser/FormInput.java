package com.projectroboticscontent.contentDatabaseAPI.RobotUser;


import com.projectroboticscontent.contentDatabaseAPI.PublicFiles.PublicFileInfo;
import com.projectroboticscontent.contentDatabaseAPI.PublicFiles.PublicFileStorage;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

public class FormInput extends FormSubmission {

    @Field
    private PublicFileStorage[] FILE_LIST;



    public FormInput(String SERVER_STATUS_MESSAGE, HttpStatus SERVER_STATUS, String token, String USERTYPE, String FORM_TITLE, String JSON_FORM_DATA,
                     long USER_PRIMARY_KEY, long FORM_ID, PublicFileStorage[] FILE_LIST, long[] COMMENTS,
                     long[] SOLUTION_LIST, String FORM_OWNER, long[] FILES,
                     String DATETIME, String USER_WALLET_ID, long STATUS) {

        super(SERVER_STATUS_MESSAGE, SERVER_STATUS, token, USERTYPE, FORM_TITLE, JSON_FORM_DATA,
        USER_PRIMARY_KEY, FORM_ID, COMMENTS, SOLUTION_LIST, FORM_OWNER, FILES, DATETIME,
                USER_WALLET_ID, STATUS);

        this.FILE_LIST = FILE_LIST;

    }

    public PublicFileStorage[] getFILE_LIST() {
        return FILE_LIST;
    }

    public void setFILE_LIST(PublicFileStorage[] FILE_LIST) {
        this.FILE_LIST = FILE_LIST;
    }
}
