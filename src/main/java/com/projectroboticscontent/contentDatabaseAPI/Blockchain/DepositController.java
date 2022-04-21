package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.projectroboticscontent.contentDatabaseAPI.Profile.Submission;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class DepositController {

    private final String url = Configuration.blockchainIPAddress + "/sol/deposit";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DepositService service;

    @PostMapping(path = "/api/wallet/deposit")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})
    public Mono<String> create(@RequestBody DepositInfo info )
    {


        return webClientBuilder.build()
                .get()
                .uri(url+"?address="+info.getAddress()+"&amount=" + info.getDepositAmount())
                .retrieve()
                .bodyToMono(String.class);


    }

    @PostMapping(value = "/api/deposit")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> submit(@RequestBody TransferInfo submission)
    {

        return ResponseEntity.ok(service.deposit(submission.getToken(),
                submission.getUSERTYPE(), submission.getAMOUNT())).getBody();

    }


}
