package com.projectroboticscontent.contentDatabaseAPI.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class LoginController {

    @Autowired
    private LoginService Service;


    @PostMapping(value = "/api/login",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<Session> userLogin(@RequestBody NewLogin login) {

        return ResponseEntity.ok(Service.Login(
                        login.getUSERNAME(), login.getPASSWORD()))
                .getBody();


    }
}
