package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TransferController {

    @Autowired
    private TransferService Service;

    @PostMapping(value = "/transfer",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono solutionFileSubmission(@RequestBody TransferInfo submission) {


        return ResponseEntity.ok(Service.SendCoinWithEmail(submission.getToken(), submission.getUSERTYPE(),
                submission.getEMAIL(), submission.getAMOUNT())).getBody();


    }

    @PostMapping(value = "/wallet/transfer",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono wallet(@RequestBody TransferInfo submission) {


        return ResponseEntity.ok(Service.SendCoin2(submission.getToken(), submission.getUSERTYPE(), submission.getRECEIVER(),
                submission.getAMOUNT())).getBody();


    }



}
