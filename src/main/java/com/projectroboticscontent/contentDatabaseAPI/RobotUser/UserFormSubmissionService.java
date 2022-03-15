package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;

import com.projectroboticscontent.contentDatabaseAPI.PublicFiles.PublicFileStorage;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.File;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserFormSubmissionService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    public UserFormSubmissionService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }

    private Mono submissionError(){

        return Mono.just(createStatusException());

    }

    private Mono submissionSuccess ()
    {
        return Mono.just("Submission was successful");
    }

    private Flux newFileSubmission(PublicFileStorage[] FILE_LIST,  long FORM_ID, String FILE_OWNER) {

        List<Document> documents = new ArrayList<Document>();
        Gson gson = new Gson();


        List<Document> DATA_DOCUMENTS = new ArrayList<Document> ();


        for (int i = 0; i < FILE_LIST.length; i++) {

            long PRIMARY_KEY = JwtTokenUtil.parseJWT(FILE_LIST[i].getToken(), FILE_LIST[i].getUSERTYPE());

            long FILE_ID = System.currentTimeMillis() + i;

            FILE_LIST[i].setFILE_ID(FILE_ID);

            FILE_LIST[i].setPRIMARY_KEY(PRIMARY_KEY);

            FILE_LIST[i].setFORM_ID(FORM_ID);

            FILE_LIST[i].setFILE_OWNER(FILE_OWNER);


            //documents.add(Document.parse(gson.toJson(FILE_LIST[i])));

            DATA_DOCUMENTS.add(Document.parse(gson.toJson(new File(FILE_LIST[i].getBINARY_FILE_DATA(), FILE_LIST[i].getFILE_ID()))));

            FILE_LIST[i].setBINARY_FILE_DATA(Hashing.sha256()
                    .hashString(FILE_LIST[i].getBINARY_FILE_DATA(), StandardCharsets.UTF_8)
                    .toString());

            documents.add(Document.parse(gson.toJson(FILE_LIST[i])));


        }

        Mono list = Mono.just(documents);

        Mono list2 = Mono.just(DATA_DOCUMENTS);

        return MongoOps.insertAll(list, "UserFileCollection")
                .flatMap(output -> {
                            return MongoOps.insertAll(list2, "UserFileCollection");
                        }
                );
    }





    private Mono newForm(String token, String USERTYPE, String FORM_TITLE, String JSON_FORM_DATA,
                        long PRIMARY_KEY,long FORM_ID, String FORM_OWNER, String USER_WALLET_ID)
    {

            final String DATETIME = LocalDateTime.now().toString();

            final String url = Configuration.blockchainIPAddress + "/sol/set";

            WebClient addBlockClient = WebClient.create();

            final FormSubmission submission = new FormSubmission(token, USERTYPE,
                    FORM_TITLE, JSON_FORM_DATA, PRIMARY_KEY, FORM_ID, new long[]{}, new long[]{}, FORM_OWNER, new long[]{},
                    DATETIME, USER_WALLET_ID, 0);

            return MongoOps.insert(submission,"UserRequestCollection")
                    .flatMap(out1 ->{
                        return addBlockClient
                                .get()
                                .uri(url+"?key=" + Long.toString(FORM_ID) + "&value="
                                        +out1.getHASH())
                                .retrieve()
                                .bodyToMono(String.class);
                    })
                    .flatMap(out2 ->{
                        return submissionSuccess();
                    });


    }

    private Flux newForm(String token, String USERTYPE, String FORM_TITLE, String JSON_FORM_DATA,
                                long PRIMARY_KEY, long FORM_ID, PublicFileStorage[] FILE_LIST,
                         String FORM_OWNER, String USER_WALLET_ID)
    {

        final String DATETIME = LocalDateTime.now().toString();

        final FormSubmission submission = new FormSubmission(token, USERTYPE,
                FORM_TITLE, JSON_FORM_DATA, PRIMARY_KEY, FORM_ID,
                new long[]{}, new long[]{}, FORM_OWNER, new long[]{}, DATETIME,USER_WALLET_ID,0);

         return MongoOps.insert(submission,"UserRequestCollection")
                .flatMapMany(output ->{
                   // return FileStorageService.newFile( MongoOps, "newFile", "pdf", "A", token, USERTYPE,
                   //         PRIMARY_KEY, FORM_ID);
                      return  newFileSubmission(FILE_LIST, FORM_ID, FORM_OWNER );
                });


        /*
        return  FileStorageService.newFileSubmission(FILE_LIST, MongoOps)
                .flatMap(output->{
                    return MongoOps.insert(submission,"UserRequestCollection");
                });

         */

    }


    public Mono newSubmission(String token, String USERTYPE, String FORM_TITLE, String JSON_FORM_DATA)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long FORM_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

       // query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return newForm(token, USERTYPE, FORM_TITLE, JSON_FORM_DATA, PRIMARY_KEY, FORM_ID, output.getFIRSTNAME() + " " + output.getLASTNAME(),
                            output.getWALLET_ID());
                })

                .switchIfEmpty(submissionError());


    }

    public Flux newSubmission(String token, String USERTYPE, String FORM_TITLE, String JSON_FORM_DATA,
                              PublicFileStorage[] FILE_LIST)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long FORM_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

       // query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(output ->{
                    return newForm(token, USERTYPE, FORM_TITLE, JSON_FORM_DATA, PRIMARY_KEY, FORM_ID, FILE_LIST,
                            output.getFIRSTNAME()+ " " + output.getLASTNAME(), output.getWALLET_ID());
                })

                .switchIfEmpty(submissionError());


    }





}
