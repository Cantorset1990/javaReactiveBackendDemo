package com.projectroboticscontent.contentDatabaseAPI.Utility;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UpdateFormPermissionController {

    @Autowired
    UpdateFormPermissionService service;

    @PostMapping(value = "/permission/add")
    @CrossOrigin({"http://localhost:3000"})

    public Mono addPermission(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getAndUpdatePermission(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID())).getBody();

    }

}
