package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionSubmission;
import com.projectroboticscontent.contentDatabaseAPI.Utility.UpdateFormPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class PurchaseController {

    @Autowired
    PurchaseService service;

    @PostMapping(value = "/api/purchase/add")
    @CrossOrigin({"http://localhost:3000"})

    public Mono<String> addPermission(@RequestBody SolutionMarketInput submission)
    {

        return ResponseEntity.ok(service.getAndAddPurchase(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID())).getBody();

    }

}
