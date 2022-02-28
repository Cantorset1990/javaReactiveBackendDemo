package com.projectroboticscontent.contentDatabaseAPI.PrivateFiles;

import com.projectroboticscontent.contentDatabaseAPI.PrivateComments.CommentStorage;
import com.projectroboticscontent.contentDatabaseAPI.PrivateComments.SolutionCommentsService;
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
public class PrivateFileController {

    @Autowired
    private PrivateFileService Service;


    @PostMapping(value = "/private/solution/file/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono solutionFileSubmission(@RequestBody PrivateFileStorage submission) {



        return ResponseEntity.ok(Service.newSolutionProviderFileSubmission(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID(), submission.getFILE_NAME(), submission.getFILE_TYPE(), submission.getFILE_SIZE(),
                submission.getFILE_INDEX(), submission.getBINARY_FILE_DATA())).getBody();



    }

    @PostMapping(value = "/private/user/file/submit",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono userFileSubmission(@RequestBody PrivateFileStorage submission) {



        return ResponseEntity.ok(Service.newUserFileSubmission(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID(), submission.getFILE_NAME(), submission.getFILE_TYPE(),
                submission.getFILE_SIZE(), submission.getFILE_INDEX(), submission.getBINARY_FILE_DATA())).getBody();



    }


    @PostMapping(value = "/private/files/user/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux getAllCommentsByUser(@RequestBody PrivateFileStorage submission) {



        return ResponseEntity.ok(Service.getAllFilesUser(submission.getToken(), submission.getUSERTYPE(),
                submission.getSOLUTION_ID())).getBody();





    }

    @PostMapping(value = "/private/files/solution/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Flux getAllCommentsByProvider(@RequestBody PrivateFileStorage submission) {



        return ResponseEntity.ok(Service.getAllFilesBySolutionProvider(submission.getToken(),
                submission.getUSERTYPE(), submission.getSOLUTION_ID())).getBody();







    }

    @PostMapping(value = "/private/file/user/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono getOneFileByUser(@RequestBody PrivateFileStorage submission) {



        return ResponseEntity.ok(Service.getOneFilesUser(submission.getToken(), submission.getUSERTYPE(), submission.getFILE_ID())).getBody();





    }

    @PostMapping(value = "/private/file/solution/get",  consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin({"http://localhost:3000", "http://localhost:8080"})


    public Mono getOneFileBySolutionProvider(@RequestBody PrivateFileStorage submission) {



        return ResponseEntity.ok(Service.getOneFileBySolutionProvider(submission.getToken(),
                submission.getUSERTYPE(), submission.getFILE_ID())).getBody();







    }
}
