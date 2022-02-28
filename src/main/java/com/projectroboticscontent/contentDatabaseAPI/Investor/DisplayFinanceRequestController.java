package com.projectroboticscontent.contentDatabaseAPI.Investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DisplayFinanceRequestController {

    @Autowired
    DisplayFinanceRequestService service;

    @PostMapping(value = "/investor/requests/status/display")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux displayWithStatus(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.RetrieveAllRequestStatus(submission.getToken(), submission.getUSERTYPE(),
                submission.getSTATUS())).getBody();

    }


    @PostMapping(value = "/investor/requests/display")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux displayAll(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.RetrieveAllRequest(submission.getToken(), submission.getUSERTYPE()
                )).getBody();

    }
}
