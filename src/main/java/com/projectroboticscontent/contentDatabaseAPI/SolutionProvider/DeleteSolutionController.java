package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class DeleteSolutionController {


    @Autowired
    DeleteSolutionService service;

    @PostMapping(value = "/api/solution/delete")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> updateForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.deleteSolution(submission.getToken(), submission.getSOLUTION_ID(),
                submission.getFORM_ID(), submission.getUSERTYPE())).getBody();

    }

}
