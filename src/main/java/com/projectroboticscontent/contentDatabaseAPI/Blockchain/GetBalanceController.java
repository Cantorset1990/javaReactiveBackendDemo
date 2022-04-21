package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.projectroboticscontent.contentDatabaseAPI.Investor.FinanceSubmission;
import com.projectroboticscontent.contentDatabaseAPI.Profile.Session;
import com.projectroboticscontent.contentDatabaseAPI.Profile.Submission;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class GetBalanceController {


   // private final String url = Configuration.blockchainIPAddress+"/sol/get-balance";



    @Autowired
    private GetBalanceService service;


    @PostMapping(value = "/api/wallet/balance")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> submit(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.getBalance(submission.getToken(), submission.getUSERTYPE())).getBody();

    }

}
