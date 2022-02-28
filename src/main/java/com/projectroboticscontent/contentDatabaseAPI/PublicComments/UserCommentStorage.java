package com.projectroboticscontent.contentDatabaseAPI.PublicComments;

import org.springframework.data.mongodb.core.mapping.Field;

public class UserCommentStorage  extends UserCommentInfo {

    @Field
    private String COMMENT_DATA;

    public UserCommentStorage(String token, String USERTYPE, long PRIMARY_KEY, long COMMENT_ID,
                          String COMMENT_DATA, String COMMENT_OWNER, long FORM_ID) {
        super(token, USERTYPE, PRIMARY_KEY, COMMENT_ID, COMMENT_OWNER, FORM_ID);
        this.COMMENT_DATA = COMMENT_DATA;
    }

    public String getCOMMENT_DATA() {
        return COMMENT_DATA;
    }

    public void setCOMMENT_DATA(String COMMENT_DATA) {
        this.COMMENT_DATA = COMMENT_DATA;
    }
}
