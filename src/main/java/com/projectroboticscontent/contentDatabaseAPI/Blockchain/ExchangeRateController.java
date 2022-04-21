package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.projectroboticscontent.contentDatabaseAPI.Profile.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/backend")
public class ExchangeRateController {


    @Autowired
    private ExchangeRateService service;


    @PostMapping(value = "/api/rate")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> submit(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.getRate(submission.getToken(), submission.getUSERTYPE())).getBody();

    }

}
