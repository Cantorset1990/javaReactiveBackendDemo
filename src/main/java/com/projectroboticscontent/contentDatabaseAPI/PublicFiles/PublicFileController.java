package com.projectroboticscontent.contentDatabaseAPI.PublicFiles;

import com.projectroboticscontent.contentDatabaseAPI.PublicComments.UserCommentStorage;
import com.projectroboticscontent.contentDatabaseAPI.PublicComments.UserCommentsService;
import com.projectroboticscontent.contentDatabaseAPI.Utility.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backend")
public class PublicFileController {

    @Autowired
    private PublicFileService Service;


    @PostMapping(value = "/api/public/user/file/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<File> userFileSubmission(@RequestBody PublicFileStorage submission) {



        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID(), submission.getFILE_NAME(), submission.getFILE_TYPE(), submission.getFILE_SIZE(),
                submission.getFILE_INDEX(), submission.getBINARY_FILE_DATA())).getBody();



    }

    @PostMapping(value = "/api/public/user/files/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux<PublicFileInfo> userFilesGet(@RequestBody PublicFileStorage submission) {



        return ResponseEntity.ok(Service.viewAllUserFiles(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID())
        ).getBody();



    }

    @PostMapping(value = "/api/public/user/file/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono<File> userFileGet(@RequestBody PublicFileStorage submission) {



        return ResponseEntity.ok(Service.viewOneUserFile(submission.getToken(), submission.getUSERTYPE(),
                submission.getFILE_ID())
        ).getBody();



    }





}
