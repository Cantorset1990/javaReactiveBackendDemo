package com.projectroboticscontent.contentDatabaseAPI.Investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class DisplayFinanceRequestController {

    @Autowired
    DisplayFinanceRequestService service;

    @PostMapping(value = "/api/investor/requests/status/display")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<FinanceRequest> displayWithStatus(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.RetrieveAllRequestStatus(submission.getToken(), submission.getUSERTYPE(),
                submission.getSTATUS())).getBody();

    }


    @PostMapping(value = "/api/investor/requests/display")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<FinanceRequest> displayAll(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.RetrieveAllRequest(submission.getToken(), submission.getUSERTYPE()
                )).getBody();

    }
}
