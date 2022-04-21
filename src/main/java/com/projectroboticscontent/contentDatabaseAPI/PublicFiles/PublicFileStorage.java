package com.projectroboticscontent.contentDatabaseAPI.PublicFiles;

import org.bson.internal.Base64;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Field;

public class PublicFileStorage extends PublicFileInfo {

    @Field
    private String BINARY_FILE_DATA;


    public PublicFileStorage(String token, String USERTYPE, long FORM_ID, String FILE_OWNER, long FILE_ID, long PRIMARY_KEY,
                             String FILE_NAME, String FILE_TYPE, long FILE_SIZE, String FILE_INDEX, String BINARY_FILE_DATA) {
        super(token, USERTYPE, FORM_ID, FILE_OWNER, FILE_ID, PRIMARY_KEY, FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_INDEX);
        this.BINARY_FILE_DATA = BINARY_FILE_DATA;
    }

    public String getBINARY_FILE_DATA() {
        return BINARY_FILE_DATA;
    }

    public void setBINARY_FILE_DATA(String BINARY_FILE_DATA) {
        this.BINARY_FILE_DATA = BINARY_FILE_DATA;
    }
}
