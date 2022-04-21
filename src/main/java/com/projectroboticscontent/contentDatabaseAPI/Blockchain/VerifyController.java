package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class VerifyController {

    @Autowired
    private VerifyService Service;

    @PostMapping(value = "/api/verifyTransaction",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> verifyTransaction(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifyTransaction(submission.getToken(), submission.getUSERTYPE(),
                submission.getTRANSACTION_ID())).getBody();


    }



    @PostMapping(value = "/api/verifySolution",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> verifySolution(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifySolution(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID())).getBody();


    }

    @PostMapping(value = "/api/verifyFinance",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> verifyFinance(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifyFinance(submission.getToken(), submission.getUSERTYPE(),
                submission.getFINANCE_REQUEST_ID())).getBody();


    }

    @PostMapping(value = "/api/verifyForm",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> verifyForm(@RequestBody BlockVerification submission) {


        return ResponseEntity.ok(Service.verifyForm(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID())).getBody();


    }

}
