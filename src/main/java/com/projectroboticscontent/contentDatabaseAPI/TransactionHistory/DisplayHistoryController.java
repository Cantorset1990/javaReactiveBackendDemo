package com.projectroboticscontent.contentDatabaseAPI.TransactionHistory;


import com.projectroboticscontent.contentDatabaseAPI.Profile.Submission;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DisplayHistoryController {

    @Autowired
    DisplayHistoryService service;

    @PostMapping(value = "/history/inflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux viewInflow(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewInflow(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/history/outflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux gviewOutflow(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewOutflow(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/history/robot/inflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux viewInflowRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewInflowRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/history/robot/outflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux gviewOutflowRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewOutflowRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }


    @PostMapping(value = "/history/payment/inflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux viewInflowNonRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewInflowNonRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/history/payment/outflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux gviewOutflowNonRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewOutflowNonRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

}
