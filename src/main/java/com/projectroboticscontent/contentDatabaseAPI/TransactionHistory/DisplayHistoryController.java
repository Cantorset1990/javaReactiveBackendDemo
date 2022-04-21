package com.projectroboticscontent.contentDatabaseAPI.TransactionHistory;


import com.projectroboticscontent.contentDatabaseAPI.Profile.Submission;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/backend")
public class DisplayHistoryController {

    @Autowired
    DisplayHistoryService service;

    @PostMapping(value = "/api/history/inflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<HistoryInfo> viewInflow(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewInflow(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/history/outflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<HistoryInfo> gviewOutflow(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewOutflow(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/history/robot/inflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<HistoryInfo> viewInflowRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewInflowRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/history/robot/outflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<HistoryInfo> gviewOutflowRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewOutflowRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }


    @PostMapping(value = "/api/history/payment/inflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<HistoryInfo> viewInflowNonRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewInflowNonRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

    @PostMapping(value = "/api/history/payment/outflow")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Flux<HistoryInfo> gviewOutflowNonRobot(@RequestBody Submission submission)
    {

        return ResponseEntity.ok(service.viewOutflowNonRobot(submission.getToken(),
                submission.getUSERTYPE())).getBody();

    }

}
