package com.projectroboticscontent.contentDatabaseAPI.Hardware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class HardwareWorkController {

    @Autowired
    HardwareService service;

    @PostMapping(value = "/api/hardware/work")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<String> updateWork(@RequestBody HardwareInfo submission)
    {

        return ResponseEntity.ok(service.updateHardwareWorkIncrement(submission.getHARDWARE_ID(),
                submission.getWORK_DONE())).getBody();

    }
}
