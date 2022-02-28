package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.RetrieveUserFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class  RetrieveSolutionController {

    @Autowired
    RetrieveSolutionService service;


    @PostMapping(value = "/solution/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux getAllSolutions(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAllSolutionsForOnePerson(submission.getToken(), submission.getUSERTYPE())).getBody();

    }


    @PostMapping(value = "/solution/status/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux getAllSolutionsStatus(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAllSolutionsForOnePersonStatus(submission.getToken(), submission.getUSERTYPE(),
         submission.getSTATUS())).getBody();

    }


    @PostMapping(value = "/user/solution/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux getSolutionsForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAndCheckSolutionForOneForm(submission.getFORM_ID(),
                submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/user/solution/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono getOneSolutionsForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAndCheckSolutionForOneForm(submission.getFORM_ID(),
                submission.getSOLUTION_ID(), submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/solution/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono getSpecificSolution(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getAndCheckSolution(submission.getSOLUTION_ID(),
                submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/solution/user/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux getSubmittedSolutionsForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getSubmittedSolutionsForOneForm(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID())).getBody();

    }

    @PostMapping(value = "/solution/user/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono getSubmittedSolutionForOneForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.getSubmittedSolutionForOneForm(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID(), submission.getSOLUTION_ID())).getBody();

    }


}
