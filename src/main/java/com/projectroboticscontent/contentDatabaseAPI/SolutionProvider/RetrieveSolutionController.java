package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.RetrieveUserFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class  RetrieveSolutionController {

    @Autowired
    RetrieveSolutionService service;


    @PostMapping(value = "/api/solution/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<SolutionDataRetrieval> getAllSolutions(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAllSolutionsForOnePerson(submission.getToken(), submission.getUSERTYPE())).getBody();

    }


    @PostMapping(value = "/api/solution/status/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<SolutionDataRetrieval> getAllSolutionsStatus(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAllSolutionsForOnePersonStatus(submission.getToken(), submission.getUSERTYPE(),
         submission.getSTATUS())).getBody();

    }


    @PostMapping(value = "/api/user/solution/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<SolutionDataRetrieval> getSolutionsForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAndCheckSolutionForOneForm(submission.getFORM_ID(),
                submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/user/solution/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<SolutionDataRetrieval> getOneSolutionsForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAndCheckSolutionForOneForm(submission.getFORM_ID(),
                submission.getSOLUTION_ID(), submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/solution/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<SolutionDataRetrieval> getSpecificSolution(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAndCheckSolution(submission.getSOLUTION_ID(),
                submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/solution/user/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<SolutionDataRetrieval> getSubmittedSolutionsForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getSubmittedSolutionsForOneForm(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID())).getBody();

    }

    @PostMapping(value = "/api/solution/user/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<SolutionDataRetrieval> getSubmittedSolutionForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getSubmittedSolutionForOneForm(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID(), submission.getSOLUTION_ID())).getBody();

    }


}
