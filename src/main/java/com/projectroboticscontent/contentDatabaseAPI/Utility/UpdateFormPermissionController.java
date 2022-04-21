package com.projectroboticscontent.contentDatabaseAPI.Utility;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class UpdateFormPermissionController {

    @Autowired
    UpdateFormPermissionService service;

    @PostMapping(value = "/api/permission/add")
    @CrossOrigin({"http://localhost:3000"})

    public Mono<String> addPermission(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.getAndUpdatePermission(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID())).getBody();

    }

}
