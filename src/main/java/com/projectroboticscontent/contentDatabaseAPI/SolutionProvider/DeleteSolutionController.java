package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.DeleteUserFormService;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Controller
public class DeleteSolutionController {


    @Autowired
    DeleteSolutionService service;

    @PostMapping(value = "/solution/delete")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateForm(@RequestBody SolutionInput submission)
    {

        return ResponseEntity.ok(service.deleteSolution(submission.getToken(), submission.getSOLUTION_ID(),
                submission.getFORM_ID(), submission.getUSERTYPE())).getBody();

    }

}
