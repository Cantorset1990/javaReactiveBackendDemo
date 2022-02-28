package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

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
public class RetrieveUserFormController {

    @Autowired
    RetrieveUserFormService service;

    @PostMapping(value = "/user/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080", "http://localhost:8080"})

    public Flux getAllForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getAllForms(submission.getToken(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/user/status/forms")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux getAllFormsStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getAllFormsStatus(submission.getToken(), submission.getUSERTYPE(),
                submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/user/form")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono getOneForm(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getOneForm(submission.getToken(),
                submission.getFORM_ID(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/user/forms/view")
    @CrossOrigin({"http://localhost:3000"})

    public Flux viewAllForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewAllUserFormsByProvider(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/user/forms/status/view")
    @CrossOrigin({"http://localhost:3000"})

    public Flux viewAllFormsStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewAllUserFormsStatus(submission.getToken(),
                submission.getUSERTYPE(), submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/user/form/view")
    @CrossOrigin({"http://localhost:3000"})

    public Mono viewSingleForms(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.viewSingleFormByProvider(submission.getToken(),
                submission.getUSERTYPE(), submission.getFORM_ID())).getBody();

    }


}
