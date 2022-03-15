package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.google.gson.Gson;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.PrivateFiles.PrivateFileStorage;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;

import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionSubmissionService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient userClient;
    private ReactiveMongoTemplate userMongoOps;

    public SolutionSubmissionService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.userClient = MongoClients.create();
        this.userMongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");
    }

    private ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Submission Error\n");
    }

    private Mono submissionError(){

        return Mono.just(createStatusException());

    }

    private Mono Error(){

        return Mono.just(createStatusException());

    }

    private Mono updateMessage(){

        return Mono.just("Creation was successful");
    }


    private Mono submissionSuccess ()
    {
        return Mono.just("Submission was successful");
    }


    private Mono addSolution(long[] SOLUTION_LIST,  long SOLUTION)
    {
        long newarr[] = new long[SOLUTION_LIST.length + 1];

        for (int i = 0; i < SOLUTION_LIST.length; i++)
            newarr[i] = SOLUTION_LIST[i];

        newarr[SOLUTION_LIST.length] = SOLUTION;

        return Mono.just(newarr);
    }




    private Flux newFileSubmission(PrivateFileStorage[] FILE_LIST, long SOLUTION_ID,
                                   long FORM_ID, long USER_PRIMARY_KEY, String FILE_OWNER) {

        List<Document> documents = new ArrayList<Document>();
        Gson gson = new Gson();


        for (int i = 0; i < FILE_LIST.length; i++) {

            long PRIMARY_KEY = JwtTokenUtil.parseJWT(FILE_LIST[i].getToken(), FILE_LIST[i].getUSERTYPE());

            long FILE_ID = System.currentTimeMillis() + i;

            FILE_LIST[i].setFILE_ID(FILE_ID);

            FILE_LIST[i].setSOLUTION_PROVIDER_PRIMARY_KEY(PRIMARY_KEY);

            FILE_LIST[i].setSOLUTION_ID(SOLUTION_ID);

            FILE_LIST[i].setFORM_ID(FORM_ID);

            FILE_LIST[i].setUSER_PRIMARY_KEY(USER_PRIMARY_KEY);

            FILE_LIST[i].setFILE_OWNER(FILE_OWNER);


            //FILE_LIST[i].setFILE_SIZE(FILE_SIZE);

            documents.add(Document.parse(gson.toJson(FILE_LIST[i])));
        }

        Mono list = Mono.just(documents);

        return solutionMongoOps.insertAll(list, "SolutionFileCollection");
    }



    private Mono updateSolutionList(long FORM_ID, long[] SOLUTION_LIST)
    {
        //final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FORM_ID").is(FORM_ID)
                                // Criteria.where("FORM_ID").is(formID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("SOLUTION_LIST", SOLUTION_LIST);
        //update.set("FORM_TITLE", FORM_TITLE);

        return MongoOps.findAndModify(query,update, FormSubmission.class,"UserCollection")
                .flatMap(output->{return updateMessage();})
                .switchIfEmpty(Error());


    }



    private Mono getAndAddSolutionList( long FORM_ID, long SOLUTION_ID )
    {
       // final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);


        //final long SOLUTION_ID = System.currentTimeMillis();

       // final SolutionInfo info = new SolutionInfo( SOLUTION_OWNER, SOLUTION_TITLE,
               // SOLUTION_ID, FORM_ID,  PRIMARY_KEY);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                               // Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("FORM_ID").is(FORM_ID)
                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");




        return MongoOps.findOne(query, FormSubmission.class,"UserCollection")

                .flatMap(output ->{
                    return addSolution(output.getSOLUTION_LIST(), SOLUTION_ID);
                })
                .flatMap(
                        output->{
                            return updateSolutionList(FORM_ID, (long[]) output);
                        }
                )

                .switchIfEmpty(Error());
    }





    private Mono newSolution(String token, String USERTYPE, String SOLUTION_TITLE,
                            String JSON_SOLUTION_DATA,
                        long PRIMARY_KEY,long FORM_ID, long SOLUTION_ID,
                             String SOLUTION_OWNER, long STATUS,
                             String SOLUTION_PROVIDER_WALLET_ID, long USER_PRIMARY_KEY, String USER_WALLET_ID)
    {


        final String DATETIME = LocalDateTime.now().toString();

        final String url = Configuration.blockchainIPAddress + "/sol/set";

        final SolutionSubmission submission = new SolutionSubmission(SOLUTION_OWNER, SOLUTION_TITLE, JSON_SOLUTION_DATA,
        SOLUTION_ID,  FORM_ID, new long[]{}, token, USERTYPE,
                PRIMARY_KEY, DATETIME, STATUS,
                SOLUTION_PROVIDER_WALLET_ID, USER_PRIMARY_KEY, USER_WALLET_ID);

        WebClient addBlockClient = WebClient.create();

        return solutionMongoOps.insert(submission,"SolutionProviderCollection")
                .flatMap(out1 ->{
                    return addBlockClient
                            .get()
                            .uri(url+"?key=" + Long.toString(SOLUTION_ID) + "&value="
                                    +out1.getHASH())
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .flatMap(out2 ->{
                    return getAndAddSolutionList( FORM_ID, SOLUTION_ID);
                })

                .flatMap(output ->{
                    return submissionSuccess();
                });


    }

    private Flux newSolution(String token, String USERTYPE, String SOLUTION_TITLE,
                            String JSON_SOLUTION_DATA,
                            long PRIMARY_KEY,long FORM_ID, long SOLUTION_ID,
                            String SOLUTION_OWNER, PrivateFileStorage[] FILE_LIST, long STATUS,
                             String SOLUTION_PROVIDER_WALLET_ID, long USER_PRIMARY_KEY, String USER_WALLET_ID)
    {

        final String DATETIME = LocalDateTime.now().toString();

        final SolutionSubmission submission = new SolutionSubmission(SOLUTION_OWNER, SOLUTION_TITLE, JSON_SOLUTION_DATA,
                SOLUTION_ID,  FORM_ID, new long[]{}, token, USERTYPE,
                PRIMARY_KEY, DATETIME, STATUS,  SOLUTION_PROVIDER_WALLET_ID, USER_PRIMARY_KEY, USER_WALLET_ID);

        return solutionMongoOps.insert(submission,"SolutionProviderCollection")
                .flatMapMany(output ->{
                    // return FileStorageService.newFile( MongoOps, "newFile", "pdf", "A", token, USERTYPE,
                    //         PRIMARY_KEY, FORM_ID);
                    return  newFileSubmission(FILE_LIST, SOLUTION_ID, FORM_ID, USER_PRIMARY_KEY, SOLUTION_OWNER );
                })
                .flatMap(output ->{
                    return getAndAddSolutionList( FORM_ID,  SOLUTION_ID);
                })
                .flatMap(output ->{
                    return submissionSuccess();
                });


        /*
        return  FileStorageService.newFileSubmission(FILE_LIST, MongoOps)
                .flatMap(output->{
                    return MongoOps.insert(submission,"UserRequestCollection");
                });

         */

    }


    private Mono newSubmission1(String token, String USERTYPE, String SOLUTION_TITLE,
                              String JSON_SOLUTION_DATA,
                              long FORM_ID, long  STATUS,
                                long USER_PRIMARY_KEY, String USER_WALLET_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long SOLUTION_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")

                .flatMap(output ->{
                    return newSolution( token,  USERTYPE,  SOLUTION_TITLE,
                            JSON_SOLUTION_DATA,
                            PRIMARY_KEY, FORM_ID, SOLUTION_ID, output.getFIRSTNAME() + " " + output.getLASTNAME(),
                            STATUS, output.getWALLET_ID(), USER_PRIMARY_KEY, USER_WALLET_ID);
                })

                .switchIfEmpty(submissionError());


    }

    private Flux newSubmission2(String token, String USERTYPE, String SOLUTION_TITLE,
                               String JSON_SOLUTION_DATA,
                               long FORM_ID,  PrivateFileStorage[] FILE_LIST, long STATUS,
                               long USER_PRIMARY_KEY, String USER_WALLET_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long SOLUTION_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMapMany(output ->{
                    return newSolution(token, USERTYPE, SOLUTION_TITLE,
                            JSON_SOLUTION_DATA, PRIMARY_KEY, FORM_ID,  SOLUTION_ID,
                            output.getFIRSTNAME() + " " + output.getLASTNAME(),  FILE_LIST,
                            STATUS, output.getWALLET_ID(), USER_PRIMARY_KEY, USER_WALLET_ID);
                })


                .switchIfEmpty(submissionError());


    }

    public Mono newSubmission(String token, String USERTYPE, String SOLUTION_TITLE,
                                String JSON_SOLUTION_DATA,
                                long FORM_ID, long  STATUS)
    {
      //  final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

      //  final long SOLUTION_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FORM_ID").is(FORM_ID)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return userMongoOps.findOne(query, FormSubmission.class,"UserRequestCollection")

                .flatMap(output ->{
                    return  newSubmission1( token, USERTYPE, SOLUTION_TITLE,
                             JSON_SOLUTION_DATA,
                     FORM_ID,   STATUS,
                            output.getUSER_PRIMARY_KEY(), output.getUSER_WALLET_ID());
                })

                .switchIfEmpty(submissionError());


    }

    public Flux newSubmission(String token, String USERTYPE, String SOLUTION_TITLE,
                              String JSON_SOLUTION_DATA,
                              long FORM_ID,  PrivateFileStorage[] FILE_LIST, long STATUS
                             )
    {
       // final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        final long SOLUTION_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FORM_ID").is(FORM_ID)

                        ));

        //query.fields().include("USERNAME");
        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return userMongoOps.findOne(query, FormSubmission.class,"UserRequestCollection")
                .flatMapMany(output ->{
                    return newSubmission2( token, USERTYPE,  SOLUTION_TITLE,
                             JSON_SOLUTION_DATA,
                     FORM_ID,   FILE_LIST,  STATUS,
                            output.getUSER_PRIMARY_KEY(), output.getUSER_WALLET_ID());
                })


                .switchIfEmpty(submissionError());


    }



}
