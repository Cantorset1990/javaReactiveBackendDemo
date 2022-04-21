package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class TransferController {

    @Autowired
    private TransferService Service;

    @PostMapping(value = "/api/transfer",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> solutionFileSubmission(@RequestBody TransferInfo submission) {


        return ResponseEntity.ok(Service.SendCoinWithEmail(submission.getToken(), submission.getUSERTYPE(),
                submission.getEMAIL(), submission.getAMOUNT())).getBody();


    }

    @PostMapping(value = "/api/wallet/transfer",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> wallet(@RequestBody TransferInfo submission) {


        return ResponseEntity.ok(Service.SendCoin2(submission.getToken(), submission.getUSERTYPE(), submission.getRECEIVER(),
                submission.getAMOUNT())).getBody();


    }

    /*

    @PostMapping(value = "/api/wallet/transfer2",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<String> wallet2(@RequestBody TransferInfo submission) {


        return ResponseEntity.ok(Service.SendCoin(submission.getRECEIVER(), submission.getRECEIVER(),
                "1")).getBody();


    }

    */



}
