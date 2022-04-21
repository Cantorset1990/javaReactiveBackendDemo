package com.projectroboticscontent.contentDatabaseAPI.PublicComments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class UserCommentsController {

    @Autowired
    private UserCommentsService Service;


    @PostMapping(value = "/api/public/user/comment/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> userCommentSubmission(@RequestBody UserCommentStorage submission) {



        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                submission.getCOMMENT_DATA(), submission.getFORM_ID())).getBody();



    }

    @PostMapping(value = "/api/public/user/comment/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux<UserCommentStorage> userCommentGet(@RequestBody UserCommentStorage submission) {



        return ResponseEntity.ok(Service.viewAllUserFormsByProvider(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_ID())
                ).getBody();



    }






}
