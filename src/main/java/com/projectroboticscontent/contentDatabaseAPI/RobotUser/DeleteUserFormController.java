package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class DeleteUserFormController {


    @Autowired
    DeleteUserFormService service;

    @PostMapping(value = "/api/user/form/delete")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> updateForm(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.deleteForm(submission.getToken(),submission.getFORM_ID(),
                submission.getUSERTYPE())).getBody();

    }


}
