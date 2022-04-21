package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class UpdateUserFormController {

    @Autowired
    UpdateUserFormService service;

    @PostMapping(value = "/api/user/form/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> updateForm(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.updateForm(submission.getToken(), submission.getFORM_ID(),
                submission.getJSON_FORM_DATA(),submission.getFORM_TITLE(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/user/form/status/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> updateFormStatus(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.updateFormStatus(submission.getToken(), submission.getFORM_ID(),
                 submission.getUSERTYPE(), submission.getSTATUS())).getBody();

    }


}
