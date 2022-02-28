package com.projectroboticscontent.contentDatabaseAPI.Investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UpdateFinanceRequestController {


    @Autowired
    UpdateFinanceService service;


    @PostMapping(value = "/update/finance/request/status")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono update(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.updateFinanceStatus(submission.getToken(),
                submission.getFINANCE_REQUEST_ID(), submission.getSTATUS(), submission.getUSERTYPE())).getBody();

    }

}
