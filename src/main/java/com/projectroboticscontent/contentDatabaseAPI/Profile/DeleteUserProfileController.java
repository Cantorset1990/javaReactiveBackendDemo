package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DeleteUserProfileController {

    @Autowired
    DeleteUserProfileService service;

    @PostMapping(value = "/user/terminate")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux terminateUser(@RequestBody FormSubmission submission)
    {

        return ResponseEntity.ok(service.deleteUserProfile(submission.getToken())).getBody();

    }
}
