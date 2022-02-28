package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class VerifyController {

    @Autowired
    private VerifyService Service;

    @PostMapping(value = "/verifyTransaction",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono verifyTransaction(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifyTransaction(submission.getToken(), submission.getUSERTYPE(),
                submission.getTRANSACTION_ID())).getBody();


    }

    @PostMapping(value = "/verifySolution",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono verifySolution(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifySolution(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID())).getBody();


    }

    @PostMapping(value = "/verifyFinance",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono verifyFinance(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifyFinance(submission.getToken(), submission.getUSERTYPE(),
                submission.getFINANCE_REQUEST_ID())).getBody();


    }

    @PostMapping(value = "/verifyForm",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono verifyForm(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifyForm(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID())).getBody();


    }

}
