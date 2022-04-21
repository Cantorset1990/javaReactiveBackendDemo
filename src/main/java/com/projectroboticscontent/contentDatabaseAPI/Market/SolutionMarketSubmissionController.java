package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormInput;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.UserFormSubmissionService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class SolutionMarketSubmissionController {

    @Autowired
    private SolutionMarketSubmissionService Service;


    @PostMapping(value = "/api/market/solution/submit", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> marketSubmission(@RequestBody SolutionMarketSubmission submission) {



        return ResponseEntity.ok(Service.newMarketSolutionSubmission(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_TITLE(), submission.getJSON_SOLUTION_DATA(),
        submission.getPRICE(),  submission.getSTATUS()))
                .getBody();



    }

    @PostMapping(value = "/api/market/solution/submit/file", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux<Document> marketSubmissionWithFile(@RequestBody SolutionMarketInput submission) {



        return ResponseEntity.ok(Service.newMarketSolutionSubmission(submission.getToken(), submission.getUSERTYPE(),
                        submission.getSOLUTION_TITLE(), submission.getJSON_SOLUTION_DATA(),
                        submission.getPRICE(), submission.getSTATUS(), submission.getFILE_LIST()))
                .getBody();




    }
}
