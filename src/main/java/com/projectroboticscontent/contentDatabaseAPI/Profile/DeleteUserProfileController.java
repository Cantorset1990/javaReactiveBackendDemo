package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/backend")
public class DeleteUserProfileController {

    @Autowired
    DeleteUserProfileService service;

    @PostMapping(value = "/api/user/terminate")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<String> terminateUser(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.deleteUserProfile(submission.getToken())).getBody();

    }
}
