package com.projectroboticscontent.contentDatabaseAPI.Blockchain;


import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GetWalletAddressController {

    @Autowired
    private GetWalletAddressService Service;

    @PostMapping(value = "/getWalletID",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono solutionFileSubmission(@RequestBody FormSubmission submission) {



        return ResponseEntity.ok(Service.checkWalletID(submission.getToken(), submission.getUSERTYPE())).getBody();




    }


}
