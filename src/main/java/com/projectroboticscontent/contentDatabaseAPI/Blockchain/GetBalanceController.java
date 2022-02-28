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
public class GetBalanceController {

    //private final String API_BY_ID = "https://sandbox.api.myinfo.gov.sg/com/v3/person-sample/{id}";

    private final String url = Configuration.blockchainIPAddress+"/sol/get-balance";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private GetBalanceService service;

    @GetMapping("/wallet/balance")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono getBalance(@RequestParam String address)
    {

        return webClientBuilder.build()
                .get()
                .uri(url+"?address="+address)
                .retrieve()
                .bodyToMono(String.class);





    }

    @PostMapping(value = "/balance")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono submit(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.getBalance(submission.getToken(), submission.getUSERTYPE())).getBody();

    }

}
