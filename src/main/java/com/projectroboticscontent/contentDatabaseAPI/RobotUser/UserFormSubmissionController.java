package com.projectroboticscontent.contentDatabaseAPI.RobotUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
public class UserFormSubmissionController {

    @Autowired
    private UserFormSubmissionService Service;


    @PostMapping(value = "/user/form/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono userSubmission(@RequestBody FormInput submission) {

        /*
        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA()))
                .getBody();

        */

        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA()))
                .getBody();


        /*
        return ResponseEntity.ok(Service.newForm(submission.getJWT(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA(),
                        JwtTokenUtil.parseJWT(submission.getJWT(), submission.getUSERTYPE())))
                .getBody();

        */



    }

    @PostMapping(value = "/user/form/submit/file",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux userSubmissionWithFile(@RequestBody FormInput submission) {

        /*
        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA()))
                .getBody();

        */

        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA(),
                       submission.getFILE_LIST()))
                .getBody();


        /*
        return ResponseEntity.ok(Service.newForm(submission.getJWT(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA(),
                        JwtTokenUtil.parseJWT(submission.getJWT(), submission.getUSERTYPE())))
                .getBody();

        */



    }
}
