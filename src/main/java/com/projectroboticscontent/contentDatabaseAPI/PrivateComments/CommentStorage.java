package com.projectroboticscontent.contentDatabaseAPI.PrivateComments;

import com.projectroboticscontent.contentDatabaseAPI.PrivateComments.CommentInfo;
import org.springframework.data.mongodb.core.mapping.Field;

public class CommentStorage extends CommentInfo {

    @Field
    private String COMMENT_DATA;

    public CommentStorage(String token, String USERTYPE, long SOLUTION_ID, long PRIMARY_KEY_SOLUTION, long COMMENT_ID,
                          String COMMENT_DATA, String COMMENT_OWNER, long PRIMARY_KEY_USER, long FORM_ID) {
        super(token, USERTYPE, SOLUTION_ID, PRIMARY_KEY_SOLUTION, COMMENT_ID, COMMENT_OWNER, PRIMARY_KEY_USER, FORM_ID);
        this.COMMENT_DATA = COMMENT_DATA;
    }

    public String getCOMMENT_DATA() {
        return COMMENT_DATA;
    }

    public void setCOMMENT_DATA(String COMMENT_DATA) {
        this.COMMENT_DATA = COMMENT_DATA;
    }
}
