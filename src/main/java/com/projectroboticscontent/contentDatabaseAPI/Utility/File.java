package com.projectroboticscontent.contentDatabaseAPI.Utility;

public class File {

    String BINARY_FILE_DATA;
    long DATA_ID;

    public File(String BINARY_FILE_DATA, long DATA_ID) {
        this.BINARY_FILE_DATA = BINARY_FILE_DATA;
        this.DATA_ID = DATA_ID;
    }

    public String getBINARY_FILE_DATA() {
        return BINARY_FILE_DATA;
    }

    public void setBINARY_FILE_DATA(String BINARY_FILE_DATA) {
        this.BINARY_FILE_DATA = BINARY_FILE_DATA;
    }

    public long getDATA_ID() {
        return DATA_ID;
    }

    public void setDATA_ID(long DATA_ID) {
        this.DATA_ID = DATA_ID;
    }
}
