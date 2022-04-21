package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.RetrieveUserFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class RetrieveUserFormController {

    @Autowired
    RetrieveUserFormService service;

    @PostMapping(value = "/api/user/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080", "http://localhost:8080"})

    public Flux<FormDataRetrieval> getAllForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getAllForms(submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/user/status/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<FormDataRetrieval> getAllFormsStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getAllFormsStatus(submission.getToken(), submission.getUSERTYPE(),
                submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/api/user/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<FormDataRetrieval> getOneForm(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getOneForm(submission.getToken(),
                submission.getFORM_ID(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/user/forms/view")
    @CrossOrigin({"http://localhost:3000"})

    public Flux<FormDataRetrieval> viewAllForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewAllUserFormsByProvider(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/user/forms/status/view")
    @CrossOrigin({"http://localhost:3000"})

    public Flux<FormDataRetrieval> viewAllFormsStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewAllUserFormsStatus(submission.getToken(),
                submission.getUSERTYPE(), submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/api/user/form/view")
    @CrossOrigin({"http://localhost:3000"})

    public Mono<FormDataRetrieval> viewSingleForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewSingleFormByProvider(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID())).getBody();

    }


}
