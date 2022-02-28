package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileService service;

    @PostMapping(value = "/user/profile")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono userProfile(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.checkProfile(submission.getToken())).getBody();

    }
}
