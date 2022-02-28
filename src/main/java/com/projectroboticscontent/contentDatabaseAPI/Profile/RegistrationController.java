package com.projectroboticscontent.contentDatabaseAPI.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService Service;


    @PostMapping(value = "/register",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono userRegistration(@RequestBody UserProfile registration) {

        return ResponseEntity.ok(Service.newRegistration(registration.getFIRSTNAME(), registration.getLASTNAME(),
                        registration.getUSERNAME(), registration.getPASSWORD(),
                        registration.getEMAIL(), registration.getPHONE(), registration.getUSERTYPE()))
                .getBody();


    }


}
