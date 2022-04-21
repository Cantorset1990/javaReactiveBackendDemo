package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class SolutionSubmissionController {

    @Autowired
    private SolutionSubmissionService Service;


    @PostMapping(value = "/api/solution/form/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> solutionSubmission(@RequestBody SolutionInput submission) {

        /*
        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA()))
                .getBody();

        */

        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(), submission.getSOLUTION_TITLE(),
                        submission.getJSON_SOLUTION_DATA(),
                        submission.getFORM_ID(), submission.getSTATUS()))
                .getBody();






    }

    @PostMapping(value = "/api/solution/form/submit/file",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux<String> solutionSubmissionWithFile(@RequestBody SolutionInput submission) {



        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(), submission.getSOLUTION_TITLE(),
                submission.getJSON_SOLUTION_DATA(),
                submission.getFORM_ID(), submission.getFILE_LIST(), submission.getSTATUS()))
                .getBody();







    }
}
