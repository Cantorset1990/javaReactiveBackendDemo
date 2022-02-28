package com.projectroboticscontent.contentDatabaseAPI.PublicFiles;

import com.projectroboticscontent.contentDatabaseAPI.PublicComments.UserCommentStorage;
import com.projectroboticscontent.contentDatabaseAPI.PublicComments.UserCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PublicFileController {

    @Autowired
    private PublicFileService Service;


    @PostMapping(value = "/public/user/file/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono userFileSubmission(@RequestBody PublicFileStorage submission) {



        return ResponseEntity.ok(Service.newSubmission(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID(), submission.getFILE_NAME(), submission.getFILE_TYPE(), submission.getFILE_SIZE(),
                submission.getFILE_INDEX(), submission.getBINARY_FILE_DATA())).getBody();



    }

    @PostMapping(value = "/public/user/files/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux userFilesGet(@RequestBody PublicFileStorage submission) {



        return ResponseEntity.ok(Service.viewAllUserFiles(submission.getToken(), submission.getUSERTYPE(),
                submission.getFORM_ID())
        ).getBody();



    }

    @PostMapping(value = "/public/user/file/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono userFileGet(@RequestBody PublicFileStorage submission) {



        return ResponseEntity.ok(Service.viewOneUserFile(submission.getToken(), submission.getUSERTYPE(),
                submission.getFILE_ID())
        ).getBody();



    }





}
