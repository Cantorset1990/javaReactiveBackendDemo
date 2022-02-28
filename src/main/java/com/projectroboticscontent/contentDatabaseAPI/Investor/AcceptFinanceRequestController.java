package com.projectroboticscontent.contentDatabaseAPI.Investor;

import com.projectroboticscontent.contentDatabaseAPI.Hardware.HardwareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AcceptFinanceRequestController {

    @Autowired
    AcceptFinanceRequestService service;




    @PostMapping(value = "/investor/request/accept")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono accept(@RequestBody FinanceSubmission submission)
    {

        return ResponseEntity.ok(service.SelectRequest2(submission.getFINANCE_REQUEST_ID(),
                submission.getToken(), submission.getUSERTYPE())).getBody();

    }


}
