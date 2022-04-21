package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormDataRetrieval;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.RetrieveUserFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class SolutionMarketViewController {

    @Autowired
    SolutionMarketViewService service;

    @PostMapping(value = "/api/market/solutions/view")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080", "http://localhost:8080"})

    public Flux<SolutionMarketSubmission> getAllForms(@RequestBody SolutionMarketSubmission submission)
    {

        return ResponseEntity.ok(service.getAllMarketSolutions(submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/market/solutions/status")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<SolutionMarketSubmission> getAllFormsStatus(@RequestBody SolutionMarketSubmission submission)
    {

        return ResponseEntity.ok(service.getAllMarketSolutionStatus(submission.getToken(), submission.getUSERTYPE(),
                submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/api/market/solution/view")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<SolutionMarketSubmission> getOneForm(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getOneMarketSolution(submission.getToken(),
                submission.getFORM_ID(), submission.getUSERTYPE())).getBody();

    }

    ///////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = "/api/market/all/view")
    @CrossOrigin({"http://localhost:3000"})

    public Flux<SolutionMarketSubmission> viewAllForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewAllMarketSolutionsByProvider(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/market/all/status/view")
    @CrossOrigin({"http://localhost:3000"})

    public Flux<SolutionMarketSubmission> viewAllFormsStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewAllMarketSolutionStatus(submission.getToken(),
                submission.getUSERTYPE(), submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/api/market/one/view")
    @CrossOrigin({"http://localhost:3000"})

    public Mono<SolutionMarketSubmission> viewSingleForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewSingleFormByProvider(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID())).getBody();

    }



}
