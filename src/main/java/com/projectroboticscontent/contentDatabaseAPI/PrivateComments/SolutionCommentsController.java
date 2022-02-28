package com.projectroboticscontent.contentDatabaseAPI.PrivateComments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SolutionCommentsController {


    @Autowired
    private SolutionCommentsService Service;


    @PostMapping(value = "/private/solution/comment/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono solutionCommentSubmission(@RequestBody CommentStorage submission) {



        return ResponseEntity.ok(Service.newFinalSubmissionSolutionProvider(submission.getToken(), submission.getUSERTYPE(),
                submission.getCOMMENT_DATA(), submission.getSOLUTION_ID())).getBody();



    }

    @PostMapping(value = "/private/user/comment/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono userCommentSubmission(@RequestBody CommentStorage submission) {



        return ResponseEntity.ok(Service.newFinalSubmissionUser(submission.getToken(), submission.getUSERTYPE(),
                submission.getCOMMENT_DATA(), submission.getSOLUTION_ID())).getBody();



    }


    @PostMapping(value = "/private/comments/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000"})


    public Flux getAllComments(@RequestBody CommentStorage submission) {

        /*
        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA()))
                .getBody();

        */

        return ResponseEntity.ok(Service.getAllSolutionComments(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID())).getBody();



        /*
        return ResponseEntity.ok(Service.newForm(submission.getJWT(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA(),
                        JwtTokenUtil.parseJWT(submission.getJWT(), submission.getUSERTYPE())))
                .getBody();

        */



    }
}
