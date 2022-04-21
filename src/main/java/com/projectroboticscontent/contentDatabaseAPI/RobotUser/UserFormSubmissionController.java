package com.projectroboticscontent.contentDatabaseAPI.RobotUser;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/backend")
public class UserFormSubmissionController {

    @Autowired
    private UserFormSubmissionService Service;


    @PostMapping(value = "/api/user/form/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> userSubmission(@RequestBody FormInput submission) {



        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA()))
                .getBody();



    }

    @PostMapping(value = "/api/user/form/submit/file",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux<Document> userSubmissionWithFile(@RequestBody FormInput submission) {



        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getFORM_TITLE(), submission.getJSON_FORM_DATA(),
                       submission.getFILE_LIST()))
                .getBody();




    }
}
