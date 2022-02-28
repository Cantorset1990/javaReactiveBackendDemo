package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.projectroboticscontent.contentDatabaseAPI.Hardware.HardwareService;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.UpdateUserFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class UpdateSolutionController {

    @Autowired
    UpdateSolutionService service;

    @PostMapping(value = "/solution/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateSolution(@RequestBody SolutionSubmission submission)
    {

        return ResponseEntity.ok(service.updateSolution(submission.getToken(), submission.getSOLUTION_ID(),
                submission.getJSON_SOLUTION_DATA(), submission.getSOLUTION_TITLE(), submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/solution/status/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateSolutionStatus(@RequestBody SolutionSubmission submission)
    {

        return ResponseEntity.ok(service.updateSolutionStatus(submission.getToken(), submission.getSOLUTION_ID(), submission.getUSERTYPE(),
                submission.getSTATUS())).getBody();

    }

    @PostMapping(value = "/user/solution/status/update")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateStatus(@RequestBody SolutionSubmission submission)
    {

        return ResponseEntity.ok(service.updateSolutionStatusByUser(submission.getToken(), submission.getSOLUTION_ID(),
                submission.getUSERTYPE(), submission.getSTATUS())).getBody();

    }
}
