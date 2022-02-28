package com.projectroboticscontent.contentDatabaseAPI.PrivateFiles;

import org.bson.internal.Base64;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Field;

public class PrivateFileStorage extends PrivateFileInfo{

    @Field
    private String BINARY_FILE_DATA;

    public PrivateFileStorage(String token, String USERTYPE, long SOLUTION_ID, long FORM_ID,
                              String FILE_OWNER, long SOLUTION_PROVIDER_PRIMARY_KEY, long FILE_ID,
                              long USER_PRIMARY_KEY, String FILE_NAME, String FILE_TYPE,
                              long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA) {

        super(token, USERTYPE, SOLUTION_ID, FORM_ID, FILE_OWNER, SOLUTION_PROVIDER_PRIMARY_KEY,
                FILE_ID, USER_PRIMARY_KEY, FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_INDEX);
        this.BINARY_FILE_DATA = BINARY_FILE_DATA;
    }

    public String getBINARY_FILE_DATA() {
        return BINARY_FILE_DATA;
    }

    public void setBINARY_FILE_DATA(String BINARY_FILE_DATA) {
        this.BINARY_FILE_DATA = BINARY_FILE_DATA;
    }
}
