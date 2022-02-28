package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UpdateUserFormController {

    @Autowired
    UpdateUserFormService service;

    @PostMapping(value = "/user/form/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateForm(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.updateForm(submission.getToken(), submission.getFORM_ID(),
                submission.getJSON_FORM_DATA(),submission.getFORM_TITLE(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/user/form/status/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateFormStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.updateFormStatus(submission.getToken(), submission.getFORM_ID(),
                 submission.getUSERTYPE(), submission.getSTATUS())).getBody();

    }


}
