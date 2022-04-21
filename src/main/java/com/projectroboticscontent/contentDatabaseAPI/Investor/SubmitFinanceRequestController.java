package com.projectroboticscontent.contentDatabaseAPI.Investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class SubmitFinanceRequestController {

    @Autowired
    SubmitFinanceRequestService service;


    @PostMapping(value = "/api/solution/finance/request/submit")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> submit(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.newSubmission2(submission.getToken(), submission.getUSERTYPE(),
                submission.getTOTAL_COST(), submission.getPAYMENT(),
                submission.getUSAGE_TIME(), submission.getHARDWARE_ID(),
                submission.getSOLUTION_ID())).getBody();

    }
}
