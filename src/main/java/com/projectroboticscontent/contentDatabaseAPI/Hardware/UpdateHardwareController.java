package com.projectroboticscontent.contentDatabaseAPI.Hardware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UpdateHardwareController {

    @Autowired
    HardwareService service;



    @PostMapping(value = "/hardware/power")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono updatePower(@RequestBody HardwareInfo submission)
    {

        return ResponseEntity.ok(service.updateHardwarePowerIncrement(submission.getHARDWARE_ID(), submission.getPOWER_CONSUMPTION())).getBody();

    }


}
