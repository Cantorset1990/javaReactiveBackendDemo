package com.projectroboticscontent.contentDatabaseAPI.Hardware;

import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class UpdateHardwareController {

    @Autowired
    HardwareService service;


    @PostMapping(value = "/api/hardware/power")
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})

    public Mono<SolutionInput> updatePower(@RequestBody HardwareInfo submission)
    {

        return ResponseEntity.ok(service.updateHardwarePowerIncrement(submission.getHARDWARE_ID(), submission.getPOWER_CONSUMPTION())).getBody();

    }


}
