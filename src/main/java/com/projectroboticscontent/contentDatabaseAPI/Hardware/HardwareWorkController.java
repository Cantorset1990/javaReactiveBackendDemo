package com.projectroboticscontent.contentDatabaseAPI.Hardware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HardwareWorkController {

    @Autowired
    HardwareService service;

    @PostMapping(value = "/hardware/work")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updateWork(@RequestBody HardwareInfo submission)
    {

        return ResponseEntity.ok(service.updateHardwareWorkIncrement(submission.getHARDWARE_ID(),
                submission.getWORK_DONE())).getBody();
       //   return Mono.just("Work Completed");
    }
}
